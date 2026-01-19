package net.youyoung.excel.support;

import net.youyoung.excel.core.ExcelFile;
import net.youyoung.excel.core.ExcelWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Helper class for writing Excel data to HTTP responses.
 * This class is framework-agnostic and works with any OutputStream.
 */
public class ExcelResponseHelper {

    private ExcelResponseHelper() {
    }

    /**
     * Excel content type for XLSX files.
     */
    public static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * Write Excel data to output stream with response headers.
     *
     * @param outputStream the output stream to write to
     * @param data         the data to write
     * @param clazz        the data class
     * @param fileName     the file name
     * @param <T>          the type of data
     * @throws IOException if an I/O error occurs
     */
    public static <T> void write(OutputStream outputStream, List<T> data, Class<T> clazz, String fileName) throws IOException {
        byte[] bytes = ExcelWriter.write(data, clazz);
        outputStream.write(bytes);
        outputStream.flush();
    }

    /**
     * Create ExcelFile from data.
     *
     * @param data     the data to write
     * @param clazz    the data class
     * @param fileName the file name
     * @param <T>      the type of data
     * @return ExcelFile containing byte array and filename
     */
    public static <T> ExcelFile createFile(List<T> data, Class<T> clazz, String fileName) {
        return ExcelWriter.writeWithFileName(data, clazz, fileName);
    }
}
