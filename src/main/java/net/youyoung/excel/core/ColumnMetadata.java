package net.youyoung.excel.core;

import net.youyoung.excel.annotation.*;
import net.youyoung.excel.style.ExcelAlign;
import net.youyoung.excel.style.ExcelColor;

import java.lang.reflect.Field;

/**
 * Metadata holder for Excel column configuration.
 */
public class ColumnMetadata {

    private final Field field;
    private final String header;
    private final String headerEn;
    private final int order;
    private final int width;
    private final boolean hideIfEmpty;

    // Style
    private final ExcelColor backgroundColor;
    private final ExcelColor fontColor;
    private final ExcelAlign align;
    private final boolean bold;
    private final int fontSize;

    // Format
    private final String dateFormat;
    private final String numberFormat;

    private ColumnMetadata(Builder builder) {
        this.field = builder.field;
        this.header = builder.header;
        this.headerEn = builder.headerEn;
        this.order = builder.order;
        this.width = builder.width;
        this.hideIfEmpty = builder.hideIfEmpty;
        this.backgroundColor = builder.backgroundColor;
        this.fontColor = builder.fontColor;
        this.align = builder.align;
        this.bold = builder.bold;
        this.fontSize = builder.fontSize;
        this.dateFormat = builder.dateFormat;
        this.numberFormat = builder.numberFormat;
    }

    public static ColumnMetadata from(Field field) {
        ExcelColumn column = field.getAnnotation(ExcelColumn.class);
        if (column == null) {
            return null;
        }

        Builder builder = new Builder()
                .field(field)
                .header(column.header())
                .headerEn(column.headerEn())
                .order(column.order())
                .width(column.width())
                .hideIfEmpty(column.hideIfEmpty());

        ExcelStyle style = field.getAnnotation(ExcelStyle.class);
        if (style != null) {
            builder.backgroundColor(style.backgroundColor())
                    .fontColor(style.fontColor())
                    .align(style.align())
                    .bold(style.bold())
                    .fontSize(style.fontSize());
        }

        ExcelDateFormat dateFormat = field.getAnnotation(ExcelDateFormat.class);
        if (dateFormat != null) {
            builder.dateFormat(dateFormat.value());
        }

        ExcelNumberFormat numberFormat = field.getAnnotation(ExcelNumberFormat.class);
        if (numberFormat != null) {
            builder.numberFormat(numberFormat.value());
        }

        return builder.build();
    }

    public Field getField() {
        return field;
    }

    public String getFieldName() {
        return field.getName();
    }

    public Class<?> getFieldType() {
        return field.getType();
    }

    public String getHeader() {
        return header;
    }

    /**
     * Get header by language. Falls back to Korean header if English header is not specified.
     *
     * @param language the language to get header for
     * @return the header in the specified language
     */
    public String getHeader(ExcelLanguage language) {
        if (language == ExcelLanguage.EN && headerEn != null && !headerEn.isEmpty()) {
            return headerEn;
        }
        return header;
    }

    public String getHeaderEn() {
        return headerEn;
    }

    public int getOrder() {
        return order;
    }

    public int getWidth() {
        return width;
    }

    public boolean isHideIfEmpty() {
        return hideIfEmpty;
    }

    public ExcelColor getBackgroundColor() {
        return backgroundColor;
    }

    public ExcelColor getFontColor() {
        return fontColor;
    }

    public ExcelAlign getAlign() {
        return align;
    }

    public boolean isBold() {
        return bold;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public boolean hasStyle() {
        return backgroundColor != null || fontColor != null || align != null;
    }

    public boolean hasDateFormat() {
        return dateFormat != null;
    }

    public boolean hasNumberFormat() {
        return numberFormat != null;
    }

    private static class Builder {
        private Field field;
        private String header;
        private String headerEn;
        private int order = 0;
        private int width = -1;
        private boolean hideIfEmpty = false;
        private ExcelColor backgroundColor = ExcelColor.NONE;
        private ExcelColor fontColor = ExcelColor.BLACK;
        private ExcelAlign align = ExcelAlign.LEFT;
        private boolean bold = false;
        private int fontSize = 11;
        private String dateFormat;
        private String numberFormat;

        Builder field(Field field) {
            this.field = field;
            return this;
        }

        Builder header(String header) {
            this.header = header;
            return this;
        }

        Builder headerEn(String headerEn) {
            this.headerEn = headerEn;
            return this;
        }

        Builder order(int order) {
            this.order = order;
            return this;
        }

        Builder width(int width) {
            this.width = width;
            return this;
        }

        Builder hideIfEmpty(boolean hideIfEmpty) {
            this.hideIfEmpty = hideIfEmpty;
            return this;
        }

        Builder backgroundColor(ExcelColor backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        Builder fontColor(ExcelColor fontColor) {
            this.fontColor = fontColor;
            return this;
        }

        Builder align(ExcelAlign align) {
            this.align = align;
            return this;
        }

        Builder bold(boolean bold) {
            this.bold = bold;
            return this;
        }

        Builder fontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        Builder dateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        Builder numberFormat(String numberFormat) {
            this.numberFormat = numberFormat;
            return this;
        }

        ColumnMetadata build() {
            return new ColumnMetadata(this);
        }
    }
}
