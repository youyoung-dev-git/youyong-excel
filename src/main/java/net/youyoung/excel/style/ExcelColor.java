package net.youyoung.excel.style;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Excel cell color options.
 */
public enum ExcelColor {

    NONE(IndexedColors.AUTOMATIC.getIndex()),
    BLACK(IndexedColors.BLACK.getIndex()),
    WHITE(IndexedColors.WHITE.getIndex()),
    RED(IndexedColors.RED.getIndex()),
    BRIGHT_GREEN(IndexedColors.BRIGHT_GREEN.getIndex()),
    BLUE(IndexedColors.BLUE.getIndex()),
    YELLOW(IndexedColors.YELLOW.getIndex()),
    PINK(IndexedColors.PINK.getIndex()),
    TURQUOISE(IndexedColors.TURQUOISE.getIndex()),
    DARK_RED(IndexedColors.DARK_RED.getIndex()),
    GREEN(IndexedColors.GREEN.getIndex()),
    DARK_BLUE(IndexedColors.DARK_BLUE.getIndex()),
    DARK_YELLOW(IndexedColors.DARK_YELLOW.getIndex()),
    VIOLET(IndexedColors.VIOLET.getIndex()),
    TEAL(IndexedColors.TEAL.getIndex()),
    GREY_25_PERCENT(IndexedColors.GREY_25_PERCENT.getIndex()),
    GREY_50_PERCENT(IndexedColors.GREY_50_PERCENT.getIndex()),
    GREY_40_PERCENT(IndexedColors.GREY_40_PERCENT.getIndex()),
    GREY_80_PERCENT(IndexedColors.GREY_80_PERCENT.getIndex()),
    LIGHT_BLUE(IndexedColors.LIGHT_BLUE.getIndex()),
    LIGHT_GREEN(IndexedColors.LIGHT_GREEN.getIndex()),
    LIGHT_YELLOW(IndexedColors.LIGHT_YELLOW.getIndex()),
    LIGHT_ORANGE(IndexedColors.LIGHT_ORANGE.getIndex()),
    LIGHT_TURQUOISE(IndexedColors.LIGHT_TURQUOISE.getIndex()),
    SKY_BLUE(IndexedColors.SKY_BLUE.getIndex()),
    GOLD(IndexedColors.GOLD.getIndex()),
    ORANGE(IndexedColors.ORANGE.getIndex()),
    CORAL(IndexedColors.CORAL.getIndex()),
    LAVENDER(IndexedColors.LAVENDER.getIndex()),
    TAN(IndexedColors.TAN.getIndex()),
    LIME(IndexedColors.LIME.getIndex()),
    AQUA(IndexedColors.AQUA.getIndex()),
    ROSE(IndexedColors.ROSE.getIndex());

    private final short index;

    ExcelColor(short index) {
        this.index = index;
    }

    public short getIndex() {
        return index;
    }
}
