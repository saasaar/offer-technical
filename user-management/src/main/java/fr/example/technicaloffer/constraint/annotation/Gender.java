package fr.example.technicaloffer.constraint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import fr.example.technicaloffer.api.GenderEnum;
import fr.example.technicaloffer.constraint.validation.GenderValidator;

/**
 * Validate that the string is a recognized gender from {@link GenderEnum}
 * 
 * @author saad arkoubi
 *
 */
@Constraint(validatedBy = GenderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Gender {

	String message() default "{user.gender.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * @return value the element must equal to
	 */
	GenderEnum[] values() default { GenderEnum.MAN, GenderEnum.WOMEN };
}
