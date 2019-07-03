package ca.csl.gifthub.core.model.account;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    public enum HashStatus {
        HASHED, UNHASHED
    }

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
    // min 8 characters, alphanum and a few special characters
    private static final Pattern PLAINTEXT_PASSWORD_PATTERN = Pattern
            .compile("^([a-z0-9����������������@#$%^&+=]*).{8}$", Pattern.CASE_INSENSITIVE);

    private HashStatus hashStatus;
    private boolean optional;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.hashStatus = constraintAnnotation.status();
        this.optional = constraintAnnotation.optional();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(password)) {
            return this.optional;
        }

        switch (this.hashStatus) {
            case HASHED:
                return this.validHashedPassword(password);
            case UNHASHED:
                return this.validUnhashedPassword(password);
            default:
                return false;
        }

    }

    private boolean validHashedPassword(String password) {
        String passwordWithoutEncoderId = password.replaceFirst("\\{[a-z0-9-]*\\}", "");
        return BCRYPT_PATTERN.matcher(passwordWithoutEncoderId).matches();
    }

    private boolean validUnhashedPassword(String password) {
        return PLAINTEXT_PASSWORD_PATTERN.matcher(password).matches();
    }

}
