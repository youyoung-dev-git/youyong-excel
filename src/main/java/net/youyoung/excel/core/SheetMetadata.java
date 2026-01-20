package net.youyoung.excel.core;

import net.youyoung.excel.annotation.ExcelHeaderStyle;
import net.youyoung.excel.annotation.ExcelSheet;
import net.youyoung.excel.style.ExcelAlign;
import net.youyoung.excel.style.ExcelColor;

import java.util.List;

/**
 * Metadata holder for Excel sheet configuration.
 */
public class SheetMetadata {

    private final String sheetName;
    private final List<ColumnMetadata> columns;

    // Header style
    private final ExcelColor headerBackgroundColor;
    private final ExcelColor headerFontColor;
    private final ExcelAlign headerAlign;
    private final boolean headerBold;
    private final int headerFontSize;
    private final int headerHeight;

    private SheetMetadata(Builder builder) {
        this.sheetName = builder.sheetName;
        this.columns = builder.columns;
        this.headerBackgroundColor = builder.headerBackgroundColor;
        this.headerFontColor = builder.headerFontColor;
        this.headerAlign = builder.headerAlign;
        this.headerBold = builder.headerBold;
        this.headerFontSize = builder.headerFontSize;
        this.headerHeight = builder.headerHeight;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SheetMetadata from(Class<?> clazz, List<ColumnMetadata> columns) {
        Builder builder = new Builder().columns(columns);

        ExcelSheet sheet = clazz.getAnnotation(ExcelSheet.class);
        if (sheet != null) {
            builder.sheetName(sheet.name());
        }

        ExcelHeaderStyle headerStyle = clazz.getAnnotation(ExcelHeaderStyle.class);
        if (headerStyle != null) {
            builder.headerBackgroundColor(headerStyle.backgroundColor())
                    .headerFontColor(headerStyle.fontColor())
                    .headerAlign(headerStyle.align())
                    .headerBold(headerStyle.bold())
                    .headerFontSize(headerStyle.fontSize())
                    .headerHeight(headerStyle.height());
        }

        return builder.build();
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<ColumnMetadata> getColumns() {
        return columns;
    }

    public List<String> getHeaders() {
        return columns.stream()
                .map(ColumnMetadata::getHeader)
                .toList();
    }

    /**
     * Get headers for the specified language.
     *
     * @param language the language to get headers for
     * @return list of header strings in the specified language
     */
    public List<String> getHeaders(ExcelLanguage language) {
        return columns.stream()
                .map(col -> col.getHeader(language))
                .toList();
    }

    public ExcelColor getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public ExcelColor getHeaderFontColor() {
        return headerFontColor;
    }

    public ExcelAlign getHeaderAlign() {
        return headerAlign;
    }

    public boolean isHeaderBold() {
        return headerBold;
    }

    public int getHeaderFontSize() {
        return headerFontSize;
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public static class Builder {
        private String sheetName = "Sheet1";
        private List<ColumnMetadata> columns = List.of();
        private ExcelColor headerBackgroundColor = ExcelColor.GREY_25_PERCENT;
        private ExcelColor headerFontColor = ExcelColor.BLACK;
        private ExcelAlign headerAlign = ExcelAlign.CENTER;
        private boolean headerBold = true;
        private int headerFontSize = 11;
        private int headerHeight = -1;

        public Builder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public Builder columns(List<ColumnMetadata> columns) {
            this.columns = columns;
            return this;
        }

        public Builder headerBackgroundColor(ExcelColor headerBackgroundColor) {
            this.headerBackgroundColor = headerBackgroundColor;
            return this;
        }

        public Builder headerFontColor(ExcelColor headerFontColor) {
            this.headerFontColor = headerFontColor;
            return this;
        }

        public Builder headerAlign(ExcelAlign headerAlign) {
            this.headerAlign = headerAlign;
            return this;
        }

        public Builder headerBold(boolean headerBold) {
            this.headerBold = headerBold;
            return this;
        }

        public Builder headerFontSize(int headerFontSize) {
            this.headerFontSize = headerFontSize;
            return this;
        }

        public Builder headerHeight(int headerHeight) {
            this.headerHeight = headerHeight;
            return this;
        }

        public SheetMetadata build() {
            return new SheetMetadata(this);
        }
    }
}
