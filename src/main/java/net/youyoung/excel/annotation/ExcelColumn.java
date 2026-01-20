package net.youyoung.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure Excel column properties.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * Column header name in Korean (required).
     */
    String header();

    /**
     * Column header name in English (optional).
     * Falls back to header() if not specified.
     */
    String headerEn() default "";

    /**
     * Column order (0-based). Lower values appear first.
     */
    int order() default 0;

    /**
     * Column width in characters. -1 means auto-size.
     */
    int width() default -1;

    /**
     * Whether to hide this column if all values are empty.
     */
    boolean hideIfEmpty() default false;
}
