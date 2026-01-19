package net.youyoung.excel.handler;

import net.youyoung.excel.core.ExcelMetadataParser;
import net.youyoung.excel.core.SheetMetadata;
import net.youyoung.excel.model.TestUserExcel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmptyColumnHideHandlerTest {

    @AfterEach
    void tearDown() {
        ExcelMetadataParser.clearCache();
    }

    @Test
    void shouldDetectEmptyColumn() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<TestUserExcel> dataWithNullPoints = List.of(
                new TestUserExcel("Alice", "alice@example.com", LocalDate.now(), null),
                new TestUserExcel("Bob", "bob@example.com", LocalDate.now(), null)
        );

        EmptyColumnHideHandler handler = new EmptyColumnHideHandler(metadata, dataWithNullPoints);
        Set<Integer> emptyColumns = handler.getEmptyColumnIndices();

        // Points column (index 3) should be detected as empty
        assertTrue(emptyColumns.contains(3));
    }

    @Test
    void shouldNotHideNonEmptyColumn() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<TestUserExcel> dataWithPoints = List.of(
                new TestUserExcel("Alice", "alice@example.com", LocalDate.now(), 100),
                new TestUserExcel("Bob", "bob@example.com", LocalDate.now(), null)
        );

        EmptyColumnHideHandler handler = new EmptyColumnHideHandler(metadata, dataWithPoints);
        Set<Integer> emptyColumns = handler.getEmptyColumnIndices();

        // Points column should not be hidden because at least one row has a value
        assertFalse(emptyColumns.contains(3));
    }

    @Test
    void shouldHandleEmptyDataList() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<TestUserExcel> emptyData = List.of();

        EmptyColumnHideHandler handler = new EmptyColumnHideHandler(metadata, emptyData);
        Set<Integer> emptyColumns = handler.getEmptyColumnIndices();

        // Only columns with hideIfEmpty=true should be in the set
        assertTrue(emptyColumns.contains(3)); // Points has hideIfEmpty=true
        assertEquals(1, emptyColumns.size());
    }

    @Test
    void shouldHandleNullDataList() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);

        EmptyColumnHideHandler handler = new EmptyColumnHideHandler(metadata, null);
        Set<Integer> emptyColumns = handler.getEmptyColumnIndices();

        assertNotNull(emptyColumns);
    }
}
