package net.youyoung.excel.core;

import net.youyoung.excel.model.TestUserExcel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelWriterReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void write_shouldCreateNonEmptyByteArray() {
        List<TestUserExcel> data = createTestData();

        byte[] bytes = ExcelWriter.write(data, TestUserExcel.class);

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    @Test
    void writeWithFileName_shouldReturnExcelFile() {
        List<TestUserExcel> data = createTestData();

        ExcelFile file = ExcelWriter.writeWithFileName(data, TestUserExcel.class, "test-users");

        assertNotNull(file);
        assertEquals("test-users.xlsx", file.getFileName());
        assertTrue(file.getSize() > 0);
    }

    @Test
    void writeWithFileName_shouldPreserveXlsxExtension() {
        List<TestUserExcel> data = createTestData();

        ExcelFile file = ExcelWriter.writeWithFileName(data, TestUserExcel.class, "test-users.xlsx");

        assertEquals("test-users.xlsx", file.getFileName());
    }

    @Test
    void writeTo_shouldWriteToFile() {
        List<TestUserExcel> data = createTestData();
        File outputFile = tempDir.resolve("output.xlsx").toFile();

        ExcelWriter.writeTo(outputFile, data, TestUserExcel.class);

        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    @Test
    void writeAndRead_shouldRoundTrip() {
        List<TestUserExcel> originalData = createTestData();
        byte[] bytes = ExcelWriter.write(originalData, TestUserExcel.class);

        List<TestUserExcel> readData = ExcelReader.read(
                new ByteArrayInputStream(bytes), TestUserExcel.class);

        assertEquals(originalData.size(), readData.size());

        TestUserExcel original = originalData.get(0);
        TestUserExcel read = readData.get(0);
        assertEquals(original.getName(), read.getName());
        assertEquals(original.getEmail(), read.getEmail());
    }

    @Test
    void read_shouldReadFromFile() {
        List<TestUserExcel> data = createTestData();
        File file = tempDir.resolve("test.xlsx").toFile();
        ExcelWriter.writeTo(file, data, TestUserExcel.class);

        List<TestUserExcel> readData = ExcelReader.read(file, TestUserExcel.class);

        assertNotNull(readData);
        assertEquals(data.size(), readData.size());
    }

    @Test
    void read_shouldReadFromFilePath() {
        List<TestUserExcel> data = createTestData();
        String filePath = tempDir.resolve("test-path.xlsx").toString();
        ExcelWriter.writeTo(filePath, data, TestUserExcel.class);

        List<TestUserExcel> readData = ExcelReader.read(filePath, TestUserExcel.class);

        assertNotNull(readData);
        assertEquals(data.size(), readData.size());
    }

    @Test
    void write_shouldHandleEmptyList() {
        List<TestUserExcel> emptyData = List.of();

        byte[] bytes = ExcelWriter.write(emptyData, TestUserExcel.class);

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    private List<TestUserExcel> createTestData() {
        return List.of(
                new TestUserExcel("Alice", "alice@example.com", LocalDate.of(2024, 1, 15), 1000),
                new TestUserExcel("Bob", "bob@example.com", LocalDate.of(2024, 2, 20), 2500),
                new TestUserExcel("Charlie", "charlie@example.com", LocalDate.of(2024, 3, 10), null)
        );
    }
}
