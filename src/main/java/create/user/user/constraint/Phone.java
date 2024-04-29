package create.user.user.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import create.user.user.validator.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {PhoneValidator.class})
public @interface Phone {

  String message() default "invalid phone";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
