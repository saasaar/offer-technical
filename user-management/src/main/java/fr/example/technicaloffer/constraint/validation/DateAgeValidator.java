package fr.example.technicaloffer.constraint.validation;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import fr.example.technicaloffer.constraint.annotation.DateAge;

/**
 * DateAge validator. <br>
 * A {@link LocalDate} must have age greater than {@link DateAge.minAge()} included
 * 
 * @author saad arkoubi
 *
 */
@Component
public class DateAgeValidator implements ConstraintValidator<DateAge, LocalDate> {

	private int minAge;

	@Override
	public void initialize(DateAge age) {
		minAge = age.minAge();
	}

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
		if (Objects.isNull(date)) {
			return false;
		}
		return minAge <= Period.between(date, LocalDate.now()).getYears();
	}

}
