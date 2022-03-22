package fr.example.technicaloffer.constraint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import javax.validation.Constraint;
import javax.validation.Payload;

import fr.example.technicaloffer.constraint.validation.AgeValidator;

/**
 * Validate that the age is between min and max included from a
 * {@link LocalDate}.
 * 
 * @author saad arkoubi
 *
 */
@Constraint(validatedBy = AgeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Age {

	int min() default 18;

	int max() default 150;

	String message() default "{user.age.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}
