package de.othr.im.gymbro.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidatorConstraint implements ConstraintValidator<NotZeroEnum, Enum<?>> {

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.ordinal() != 0;
    }
}