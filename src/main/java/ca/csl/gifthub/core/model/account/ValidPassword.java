package ca.csl.gifthub.core.model.account;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ca.csl.gifthub.core.model.account.PasswordValidator.HashStatus;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {

    String message() default "user.password.invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    HashStatus status();

    boolean optional() default false;
}
