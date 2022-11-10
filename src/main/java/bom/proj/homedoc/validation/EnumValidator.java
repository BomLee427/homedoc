package bom.proj.homedoc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue annotation;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (value == null) {
                return true;
            }
            Enum findConstant = Arrays.stream(annotation.enumClass().getEnumConstants()).filter(
                    e -> value.equals(e.toString())
                            || (annotation.ignoreCase() && value.equalsIgnoreCase(e.toString()))
            ).findFirst().orElse(null);
            return findConstant != null;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
