package net.youyoung.excel.style;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import net.youyoung.excel.core.ColumnMetadata;
import net.youyoung.excel.core.SheetMetadata;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * Default style strategy based on annotation metadata.
 */
public class DefaultStyleStrategy {

    private DefaultStyleStrategy() {
    }

    /**
     * Create a style strategy from sheet metadata.
     *
     * @param metadata the sheet metadata
     * @return horizontal cell style strategy
     */
    public static HorizontalCellStyleStrategy create(SheetMetadata metadata) {
        WriteCellStyle headerStyle = createHeaderStyle(metadata);
        WriteCellStyle contentStyle = createContentStyle();

        return new HorizontalCellStyleStrategy(headerStyle, contentStyle);
    }

    private static WriteCellStyle createHeaderStyle(SheetMetadata metadata) {
        WriteCellStyle style = new WriteCellStyle();

        if (metadata.getHeaderBackgroundColor() != ExcelColor.NONE) {
            style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(metadata.getHeaderBackgroundColor().getIndex());
        }

        style.setHorizontalAlignment(metadata.getHeaderAlign().getAlignment());

        WriteFont font = new WriteFont();
        font.setBold(metadata.isHeaderBold());
        font.setFontHeightInPoints((short) metadata.getHeaderFontSize());
        if (metadata.getHeaderFontColor() != ExcelColor.NONE) {
            font.setColor(metadata.getHeaderFontColor().getIndex());
        }
        style.setWriteFont(font);

        return style;
    }

    private static WriteCellStyle createContentStyle() {
        WriteCellStyle style = new WriteCellStyle();
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 11);
        style.setWriteFont(font);
        return style;
    }

    /**
     * Create cell style from column metadata.
     *
     * @param column the column metadata
     * @return write cell style
     */
    public static WriteCellStyle createCellStyle(ColumnMetadata column) {
        WriteCellStyle style = new WriteCellStyle();

        if (column.getBackgroundColor() != ExcelColor.NONE) {
            style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(column.getBackgroundColor().getIndex());
        }

        style.setHorizontalAlignment(column.getAlign().getAlignment());

        WriteFont font = new WriteFont();
        font.setBold(column.isBold());
        font.setFontHeightInPoints((short) column.getFontSize());
        if (column.getFontColor() != ExcelColor.NONE) {
            font.setColor(column.getFontColor().getIndex());
        }
        style.setWriteFont(font);

        return style;
    }
}
