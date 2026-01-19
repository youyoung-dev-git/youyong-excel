package net.youyoung.excel.core;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import net.youyoung.excel.handler.AnnotationWriteHandler;
import net.youyoung.excel.handler.EmptyColumnHideHandler;
import net.youyoung.excel.style.DefaultStyleStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

/**
 * Excel writer that converts Java objects to Excel files.
 */
public class ExcelWriter {

    private ExcelWriter() {
    }

    /**
     * Write data to byte array.
     *
     * @param data  the data to write
     * @param clazz the data class
     * @param <T>   the type of data
     * @return byte array containing Excel data
     */
    public static <T> byte[] write(List<T> data, Class<T> clazz) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeTo(outputStream, data, clazz);
        return outputStream.toByteArray();
    }

    /**
     * Write data to byte array with filename.
     *
     * @param data     the data to write
     * @param clazz    the data class
     * @param fileName the file name (without extension)
     * @param <T>      the type of data
     * @return ExcelFile containing byte array and filename
     */
    public static <T> ExcelFile writeWithFileName(List<T> data, Class<T> clazz, String fileName) {
        byte[] bytes = write(data, clazz);
        String fullFileName = fileName.endsWith(".xlsx") ? fileName : fileName + ".xlsx";
        return new ExcelFile(bytes, fullFileName);
    }

    /**
     * Write data to output stream.
     *
     * @param outputStream the output stream
     * @param data         the data to write
     * @param clazz        the data class
     * @param <T>          the type of data
     */
    public static <T> void writeTo(OutputStream outputStream, List<T> data, Class<T> clazz) {
        SheetMetadata metadata = ExcelMetadataParser.parse(clazz);
        doWrite(outputStream, data, clazz, metadata);
    }

    /**
     * Write data to file.
     *
     * @param file  the output file
     * @param data  the data to write
     * @param clazz the data class
     * @param <T>   the type of data
     */
    public static <T> void writeTo(File file, List<T> data, Class<T> clazz) {
        SheetMetadata metadata = ExcelMetadataParser.parse(clazz);

        ExcelWriterSheetBuilder sheetBuilder = EasyExcel.write(file, clazz)
                .registerWriteHandler(DefaultStyleStrategy.create(metadata))
                .registerWriteHandler(new AnnotationWriteHandler(metadata))
                .registerWriteHandler(new EmptyColumnHideHandler(metadata, data))
                .sheet(metadata.getSheetName());

        configureHeaders(sheetBuilder, metadata);
        sheetBuilder.doWrite(data);
    }

    /**
     * Write data to file path.
     *
     * @param filePath the output file path
     * @param data     the data to write
     * @param clazz    the data class
     * @param <T>      the type of data
     */
    public static <T> void writeTo(String filePath, List<T> data, Class<T> clazz) {
        writeTo(new File(filePath), data, clazz);
    }

    private static <T> void doWrite(OutputStream outputStream, List<T> data, Class<T> clazz, SheetMetadata metadata) {
        ExcelWriterSheetBuilder sheetBuilder = EasyExcel.write(outputStream, clazz)
                .registerWriteHandler(DefaultStyleStrategy.create(metadata))
                .registerWriteHandler(new AnnotationWriteHandler(metadata))
                .registerWriteHandler(new EmptyColumnHideHandler(metadata, data))
                .sheet(metadata.getSheetName());

        configureHeaders(sheetBuilder, metadata);
        sheetBuilder.doWrite(data);
    }

    private static void configureHeaders(ExcelWriterSheetBuilder sheetBuilder, SheetMetadata metadata) {
        List<List<String>> headers = metadata.getColumns().stream()
                .map(col -> List.of(col.getHeader()))
                .toList();
        sheetBuilder.head(headers);
    }
}
