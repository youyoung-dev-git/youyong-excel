package net.youyoung.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure date format for date/time fields.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDateFormat {

    /**
     * Date format pattern (e.g., "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss").
     */
    String value() default "yyyy-MM-dd";
}
