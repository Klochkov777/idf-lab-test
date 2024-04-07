package com.klochkov.idflabtest.validator.annotation;

import com.klochkov.idflabtest.validator.FormatCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FormatCategoryValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER,  ElementType.ANNOTATION_TYPE})
public @interface CheckFormatCategory {
    /**
     * default message about not validated data.
     */
    String message() default "not valid category";
    /**
     * groups.
     */
    Class<?>[] groups() default {};
    /**
     * meta information about annotation.
     */
    Class<? extends Payload>[] payload() default {};
}
