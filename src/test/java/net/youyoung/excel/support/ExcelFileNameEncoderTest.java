package net.youyoung.excel.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelFileNameEncoderTest {

    @Test
    void encode_shouldEncodeSimpleFilename() {
        String result = ExcelFileNameEncoder.encode("test.xlsx");

        assertTrue(result.contains("attachment;"));
        assertTrue(result.contains("filename="));
        assertTrue(result.contains("test.xlsx"));
    }

    @Test
    void encode_shouldEncodeKoreanFilename() {
        String result = ExcelFileNameEncoder.encode("사용자목록.xlsx");

        assertTrue(result.contains("attachment;"));
        assertTrue(result.contains("filename*=UTF-8''"));
        assertFalse(result.contains("사용자목록")); // Should be URL-encoded
    }

    @Test
    void encode_shouldHandleSpaces() {
        String result = ExcelFileNameEncoder.encode("file name.xlsx");

        assertTrue(result.contains("%20")); // Space should be encoded as %20
        assertFalse(result.contains("+")); // Not encoded as +
    }

    @Test
    void encodeWithExtension_shouldAddXlsxExtension() {
        String result = ExcelFileNameEncoder.encodeWithExtension("test");

        assertTrue(result.contains("test.xlsx"));
    }

    @Test
    void encodeWithExtension_shouldNotDuplicateExtension() {
        String result = ExcelFileNameEncoder.encodeWithExtension("test.xlsx");

        assertFalse(result.contains("test.xlsx.xlsx"));
        assertTrue(result.contains("test.xlsx"));
    }

    @Test
    void encode_shouldHandleSpecialCharacters() {
        String result = ExcelFileNameEncoder.encode("file[1]_test.xlsx");

        assertNotNull(result);
        assertTrue(result.contains("attachment;"));
    }
}
