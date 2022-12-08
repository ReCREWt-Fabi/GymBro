package de.othr.im.gymbro.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull
public @interface NotZeroEnum {

    String message() default "must be valid enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}