package net.youyoung.excel.core;

import net.youyoung.excel.annotation.ExcelColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Parser for extracting Excel metadata from annotated classes.
 */
public class ExcelMetadataParser {

    private static final Map<Class<?>, SheetMetadata> CACHE = new ConcurrentHashMap<>();

    private ExcelMetadataParser() {
    }

    /**
     * Parse metadata from the given class.
     *
     * @param clazz the class to parse
     * @return sheet metadata
     */
    public static SheetMetadata parse(Class<?> clazz) {
        return CACHE.computeIfAbsent(clazz, ExcelMetadataParser::doParse);
    }

    private static SheetMetadata doParse(Class<?> clazz) {
        List<ColumnMetadata> columns = new ArrayList<>();

        for (Field field : getAllFields(clazz)) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ColumnMetadata column = ColumnMetadata.from(field);
                if (column != null) {
                    columns.add(column);
                }
            }
        }

        columns.sort(Comparator.comparingInt(ColumnMetadata::getOrder));

        return SheetMetadata.from(clazz, columns);
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = clazz;

        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                fields.add(field);
            }
            current = current.getSuperclass();
        }

        return fields;
    }

    /**
     * Clear the metadata cache.
     */
    public static void clearCache() {
        CACHE.clear();
    }
}
