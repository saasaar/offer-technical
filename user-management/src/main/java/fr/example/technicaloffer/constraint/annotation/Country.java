package fr.example.technicaloffer.constraint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import fr.example.technicaloffer.api.CountryEnum;
import fr.example.technicaloffer.constraint.validation.CountryValidator;

/**
 * Validate that the string is a recognized country from {@link CountryEnum}
 * 
 * @author saad arkoubi
 *
 */
@Constraint(validatedBy = CountryValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Country {

	String message() default "{user.country.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	/**
	 * @return values the element must equal to
	 */
	CountryEnum[] values() default { CountryEnum.FRANCE };
}
