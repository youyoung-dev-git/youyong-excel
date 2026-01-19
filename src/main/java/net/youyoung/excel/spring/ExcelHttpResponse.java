package net.youyoung.excel.spring;

import jakarta.servlet.http.HttpServletResponse;
import net.youyoung.excel.core.ExcelWriter;
import net.youyoung.excel.support.ExcelFileNameEncoder;
import net.youyoung.excel.support.ExcelResponseHelper;

import java.io.IOException;
import java.util.List;

/**
 * Utility for writing Excel files directly to HttpServletResponse.
 * This class requires Spring Web and Servlet API.
 */
public class ExcelHttpResponse {

    private ExcelHttpResponse() {
    }

    /**
     * Write Excel data to HttpServletResponse.
     *
     * @param response the HTTP response
     * @param data     the data to write
     * @param clazz    the data class
     * @param fileName the file name (with or without .xlsx extension)
     * @param <T>      the type of data
     * @throws IOException if an I/O error occurs
     */
    public static <T> void write(HttpServletResponse response, List<T> data, Class<T> clazz, String fileName) throws IOException {
        setResponseHeaders(response, fileName);

        byte[] bytes = ExcelWriter.write(data, clazz);
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    /**
     * Write Excel data to HttpServletResponse using streaming.
     *
     * @param response the HTTP response
     * @param data     the data to write
     * @param clazz    the data class
     * @param fileName the file name (with or without .xlsx extension)
     * @param <T>      the type of data
     * @throws IOException if an I/O error occurs
     */
    public static <T> void writeStream(HttpServletResponse response, List<T> data, Class<T> clazz, String fileName) throws IOException {
        setResponseHeaders(response, fileName);
        ExcelWriter.writeTo(response.getOutputStream(), data, clazz);
        response.getOutputStream().flush();
    }

    private static void setResponseHeaders(HttpServletResponse response, String fileName) {
        response.setContentType(ExcelResponseHelper.CONTENT_TYPE);
        response.setHeader("Content-Disposition", ExcelFileNameEncoder.encodeWithExtension(fileName));
        response.setCharacterEncoding("UTF-8");
    }
}
