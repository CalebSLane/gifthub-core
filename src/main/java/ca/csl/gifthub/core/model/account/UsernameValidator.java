package ca.csl.gifthub.core.model.account;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9����������������]*$",
            Pattern.CASE_INSENSITIVE);

    private boolean optional;

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.optional = constraintAnnotation.optional();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(username)) {
            return this.optional;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

}
