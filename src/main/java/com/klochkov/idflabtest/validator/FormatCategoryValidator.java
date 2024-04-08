package com.klochkov.idflabtest.validator;

import com.klochkov.idflabtest.enumeration.Category;
import com.klochkov.idflabtest.validator.annotation.CheckFormatCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormatCategoryValidator implements ConstraintValidator<CheckFormatCategory, String> {
    private final List<String> listCategory = Arrays.stream(Category.values())
            .map(category -> category.name()).collect(Collectors.toList());
    /**
     * Method for check FormatCategory is valid or not valid.
     *
     * @param value - the field for check
     * @param context - context for validation
     * @return - boolean
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && listCategory.contains(value);
    }
}
