package net.youyoung.excel.spring;

import net.youyoung.excel.core.ExcelReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Utility for reading Excel data from MultipartFile.
 * This class requires Spring Web.
 */
public class ExcelMultipartFile {

    private ExcelMultipartFile() {
    }

    /**
     * Read Excel data from MultipartFile.
     *
     * @param file  the multipart file
     * @param clazz the target class
     * @param <T>   the type of data model
     * @return list of objects
     * @throws IOException if an I/O error occurs
     */
    public static <T> List<T> read(MultipartFile file, Class<T> clazz) throws IOException {
        return ExcelReader.read(file.getInputStream(), clazz);
    }

    /**
     * Read Excel data from MultipartFile with sheet index.
     *
     * @param file    the multipart file
     * @param clazz   the target class
     * @param sheetNo the sheet index (0-based)
     * @param <T>     the type of data model
     * @return list of objects
     * @throws IOException if an I/O error occurs
     */
    public static <T> List<T> read(MultipartFile file, Class<T> clazz, int sheetNo) throws IOException {
        return ExcelReader.read(file.getInputStream(), clazz, sheetNo);
    }

    /**
     * Read Excel data from MultipartFile with sheet index and header row number.
     *
     * @param file          the multipart file
     * @param clazz         the target class
     * @param sheetNo       the sheet index (0-based)
     * @param headRowNumber the header row number (1-based)
     * @param <T>           the type of data model
     * @return list of objects
     * @throws IOException if an I/O error occurs
     */
    public static <T> List<T> read(MultipartFile file, Class<T> clazz, int sheetNo, int headRowNumber) throws IOException {
        return ExcelReader.read(file.getInputStream(), clazz, sheetNo, headRowNumber);
    }
}
