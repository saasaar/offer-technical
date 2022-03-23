package fr.example.technicaloffer.constraint.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import fr.example.technicaloffer.constraint.annotation.DateAge;

/**
 * DateAge validator. <br>
 * A {@link LocalDate} must have age between a {@link DateAge.minAge()} and
 * {@link DateAge.maxAge()}
 * 
 * @author saad arkoubi
 *
 */
@Component
public class DateAgeValidator implements ConstraintValidator<DateAge, LocalDate> {

	private int minAge;
	private int maxAge;

	@Override
	public void initialize(DateAge age) {
		minAge = age.minAge();
		maxAge = age.maxAge();
	}

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
		var diffYears = Period.between(date, LocalDate.now()).getYears();
		return minAge <= diffYears && diffYears <= maxAge;
	}

}
