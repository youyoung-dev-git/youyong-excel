package net.youyoung.excel.core;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Excel file reader that maps Excel data to Java objects.
 */
public class ExcelReader {

    private ExcelReader() {
    }

    /**
     * Read Excel from InputStream.
     *
     * @param inputStream the input stream
     * @param clazz       the target class
     * @param <T>         the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(InputStream inputStream, Class<T> clazz) {
        return read(inputStream, clazz, 0);
    }

    /**
     * Read Excel from InputStream with sheet index.
     *
     * @param inputStream the input stream
     * @param clazz       the target class
     * @param sheetNo     the sheet index (0-based)
     * @param <T>         the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(InputStream inputStream, Class<T> clazz, int sheetNo) {
        return read(inputStream, clazz, sheetNo, 1);
    }

    /**
     * Read Excel from InputStream with sheet index and header row number.
     *
     * @param inputStream   the input stream
     * @param clazz         the target class
     * @param sheetNo       the sheet index (0-based)
     * @param headRowNumber the header row number (1-based)
     * @param <T>           the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(InputStream inputStream, Class<T> clazz, int sheetNo, int headRowNumber) {
        ExcelReadListener<T> listener = new ExcelReadListener<>();
        EasyExcel.read(inputStream, clazz, listener)
                .sheet(sheetNo)
                .headRowNumber(headRowNumber)
                .doRead();
        return listener.getDataList();
    }

    /**
     * Read Excel from file.
     *
     * @param file  the file
     * @param clazz the target class
     * @param <T>   the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(File file, Class<T> clazz) {
        return read(file, clazz, 0);
    }

    /**
     * Read Excel from file with sheet index.
     *
     * @param file    the file
     * @param clazz   the target class
     * @param sheetNo the sheet index (0-based)
     * @param <T>     the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(File file, Class<T> clazz, int sheetNo) {
        return read(file, clazz, sheetNo, 1);
    }

    /**
     * Read Excel from file with sheet index and header row number.
     *
     * @param file          the file
     * @param clazz         the target class
     * @param sheetNo       the sheet index (0-based)
     * @param headRowNumber the header row number (1-based)
     * @param <T>           the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(File file, Class<T> clazz, int sheetNo, int headRowNumber) {
        ExcelReadListener<T> listener = new ExcelReadListener<>();
        EasyExcel.read(file, clazz, listener)
                .sheet(sheetNo)
                .headRowNumber(headRowNumber)
                .doRead();
        return listener.getDataList();
    }

    /**
     * Read Excel from file path.
     *
     * @param filePath the file path
     * @param clazz    the target class
     * @param <T>      the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(String filePath, Class<T> clazz) {
        return read(new File(filePath), clazz);
    }

    /**
     * Read Excel from file path with sheet index.
     *
     * @param filePath the file path
     * @param clazz    the target class
     * @param sheetNo  the sheet index (0-based)
     * @param <T>      the type of data model
     * @return list of objects
     */
    public static <T> List<T> read(String filePath, Class<T> clazz, int sheetNo) {
        return read(new File(filePath), clazz, sheetNo);
    }
}
