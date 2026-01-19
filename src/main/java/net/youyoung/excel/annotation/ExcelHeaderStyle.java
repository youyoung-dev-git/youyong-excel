package net.youyoung.excel.annotation;

import net.youyoung.excel.style.ExcelAlign;
import net.youyoung.excel.style.ExcelColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure header row style.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeaderStyle {

    /**
     * Header cell background color.
     */
    ExcelColor backgroundColor() default ExcelColor.GREY_25_PERCENT;

    /**
     * Header font color.
     */
    ExcelColor fontColor() default ExcelColor.BLACK;

    /**
     * Horizontal alignment.
     */
    ExcelAlign align() default ExcelAlign.CENTER;

    /**
     * Whether to use bold font.
     */
    boolean bold() default true;

    /**
     * Font size in points.
     */
    int fontSize() default 11;

    /**
     * Header row height. -1 means auto-size.
     */
    int height() default -1;
}
