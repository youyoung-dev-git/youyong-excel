package net.youyoung.excel.style;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelAlignTest {

    @Test
    void getAlignment_shouldReturnCorrectAlignmentForLeft() {
        assertEquals(HorizontalAlignment.LEFT, ExcelAlign.LEFT.getAlignment());
    }

    @Test
    void getAlignment_shouldReturnCorrectAlignmentForCenter() {
        assertEquals(HorizontalAlignment.CENTER, ExcelAlign.CENTER.getAlignment());
    }

    @Test
    void getAlignment_shouldReturnCorrectAlignmentForRight() {
        assertEquals(HorizontalAlignment.RIGHT, ExcelAlign.RIGHT.getAlignment());
    }

    @Test
    void getAlignment_shouldReturnCorrectAlignmentForJustify() {
        assertEquals(HorizontalAlignment.JUSTIFY, ExcelAlign.JUSTIFY.getAlignment());
    }

    @Test
    void getAlignment_shouldReturnCorrectAlignmentForGeneral() {
        assertEquals(HorizontalAlignment.GENERAL, ExcelAlign.GENERAL.getAlignment());
    }
}
