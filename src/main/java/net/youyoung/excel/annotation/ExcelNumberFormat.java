package net.youyoung.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure number format for numeric fields.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelNumberFormat {

    /**
     * Number format pattern (e.g., "#,###", "#,##0.00", "0.00%").
     */
    String value() default "#,###";
}
