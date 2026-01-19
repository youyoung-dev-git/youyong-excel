package net.youyoung.excel.core;

import net.youyoung.excel.model.TestUserExcel;
import net.youyoung.excel.style.ExcelAlign;
import net.youyoung.excel.style.ExcelColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelMetadataParserTest {

    @AfterEach
    void tearDown() {
        ExcelMetadataParser.clearCache();
    }

    @Test
    void parse_shouldExtractSheetMetadata() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);

        assertNotNull(metadata);
        assertEquals("Users", metadata.getSheetName());
    }

    @Test
    void parse_shouldExtractHeaderStyle() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);

        assertEquals(ExcelColor.GREY_25_PERCENT, metadata.getHeaderBackgroundColor());
        assertTrue(metadata.isHeaderBold());
        assertEquals(ExcelAlign.CENTER, metadata.getHeaderAlign());
    }

    @Test
    void parse_shouldExtractColumns() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<ColumnMetadata> columns = metadata.getColumns();

        assertEquals(4, columns.size());
    }

    @Test
    void parse_shouldOrderColumnsByOrderAttribute() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<ColumnMetadata> columns = metadata.getColumns();

        assertEquals("Name", columns.get(0).getHeader());
        assertEquals("Email", columns.get(1).getHeader());
        assertEquals("Join Date", columns.get(2).getHeader());
        assertEquals("Points", columns.get(3).getHeader());
    }

    @Test
    void parse_shouldExtractColumnWidth() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<ColumnMetadata> columns = metadata.getColumns();

        ColumnMetadata emailColumn = columns.get(1);
        assertEquals(30, emailColumn.getWidth());
    }

    @Test
    void parse_shouldExtractHideIfEmpty() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<ColumnMetadata> columns = metadata.getColumns();

        ColumnMetadata pointsColumn = columns.get(3);
        assertTrue(pointsColumn.isHideIfEmpty());
    }

    @Test
    void parse_shouldExtractCellStyle() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<ColumnMetadata> columns = metadata.getColumns();

        ColumnMetadata nameColumn = columns.get(0);
        assertTrue(nameColumn.isBold());
        assertEquals(ExcelColor.BLUE, nameColumn.getFontColor());
    }

    @Test
    void parse_shouldExtractDateFormat() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<ColumnMetadata> columns = metadata.getColumns();

        ColumnMetadata joinDateColumn = columns.get(2);
        assertTrue(joinDateColumn.hasDateFormat());
        assertEquals("yyyy-MM-dd", joinDateColumn.getDateFormat());
    }

    @Test
    void parse_shouldExtractNumberFormat() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<ColumnMetadata> columns = metadata.getColumns();

        ColumnMetadata pointsColumn = columns.get(3);
        assertTrue(pointsColumn.hasNumberFormat());
        assertEquals("#,###", pointsColumn.getNumberFormat());
    }

    @Test
    void parse_shouldCacheResults() {
        SheetMetadata first = ExcelMetadataParser.parse(TestUserExcel.class);
        SheetMetadata second = ExcelMetadataParser.parse(TestUserExcel.class);

        assertSame(first, second);
    }

    @Test
    void getHeaders_shouldReturnColumnHeaders() {
        SheetMetadata metadata = ExcelMetadataParser.parse(TestUserExcel.class);
        List<String> headers = metadata.getHeaders();

        assertEquals(List.of("Name", "Email", "Join Date", "Points"), headers);
    }
}
