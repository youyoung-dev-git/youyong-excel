package net.youyoung.excel.style;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelColorTest {

    @Test
    void getIndex_shouldReturnCorrectIndexForBlack() {
        assertEquals(IndexedColors.BLACK.getIndex(), ExcelColor.BLACK.getIndex());
    }

    @Test
    void getIndex_shouldReturnCorrectIndexForWhite() {
        assertEquals(IndexedColors.WHITE.getIndex(), ExcelColor.WHITE.getIndex());
    }

    @Test
    void getIndex_shouldReturnCorrectIndexForGrey25Percent() {
        assertEquals(IndexedColors.GREY_25_PERCENT.getIndex(), ExcelColor.GREY_25_PERCENT.getIndex());
    }

    @Test
    void getIndex_shouldReturnCorrectIndexForRed() {
        assertEquals(IndexedColors.RED.getIndex(), ExcelColor.RED.getIndex());
    }

    @Test
    void getIndex_shouldReturnCorrectIndexForBlue() {
        assertEquals(IndexedColors.BLUE.getIndex(), ExcelColor.BLUE.getIndex());
    }

    @Test
    void none_shouldReturnAutomaticIndex() {
        assertEquals(IndexedColors.AUTOMATIC.getIndex(), ExcelColor.NONE.getIndex());
    }
}
