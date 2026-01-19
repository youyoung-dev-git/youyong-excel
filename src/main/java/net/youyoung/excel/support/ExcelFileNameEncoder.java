package net.youyoung.excel.support;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility for encoding Excel file names for HTTP responses.
 * Supports Korean and other non-ASCII characters.
 */
public class ExcelFileNameEncoder {

    private ExcelFileNameEncoder() {
    }

    /**
     * Encode filename for Content-Disposition header.
     * Supports Korean and other non-ASCII characters across browsers.
     *
     * @param fileName the file name to encode
     * @return encoded filename for Content-Disposition header
     */
    public static String encode(String fileName) {
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replace("+", "%20");
        return "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName;
    }

    /**
     * Encode filename and ensure .xlsx extension.
     *
     * @param fileName the file name to encode
     * @return encoded filename for Content-Disposition header
     */
    public static String encodeWithExtension(String fileName) {
        String fullFileName = fileName.endsWith(".xlsx") ? fileName : fileName + ".xlsx";
        return encode(fullFileName);
    }
}
