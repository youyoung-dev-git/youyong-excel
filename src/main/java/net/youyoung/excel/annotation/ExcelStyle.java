package net.youyoung.excel.annotation;

import net.youyoung.excel.style.ExcelAlign;
import net.youyoung.excel.style.ExcelColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure data cell style.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelStyle {

    /**
     * Cell background color.
     */
    ExcelColor backgroundColor() default ExcelColor.NONE;

    /**
     * Font color.
     */
    ExcelColor fontColor() default ExcelColor.BLACK;

    /**
     * Horizontal alignment.
     */
    ExcelAlign align() default ExcelAlign.LEFT;

    /**
     * Whether to use bold font.
     */
    boolean bold() default false;

    /**
     * Font size in points.
     */
    int fontSize() default 11;
}
