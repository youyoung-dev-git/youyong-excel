package net.youyoung.excel.style;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * Excel cell horizontal alignment options.
 */
public enum ExcelAlign {

    LEFT(HorizontalAlignment.LEFT),
    CENTER(HorizontalAlignment.CENTER),
    RIGHT(HorizontalAlignment.RIGHT),
    JUSTIFY(HorizontalAlignment.JUSTIFY),
    GENERAL(HorizontalAlignment.GENERAL);

    private final HorizontalAlignment alignment;

    ExcelAlign(HorizontalAlignment alignment) {
        this.alignment = alignment;
    }

    public HorizontalAlignment getAlignment() {
        return alignment;
    }
}
