package fr.example.technicaloffer.constraint.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import fr.example.technicaloffer.constraint.annotation.Age;

/**
 * Age validator. <br>
 * A {@link LocalDate} must have age between a {@link Age.min()} and
 * {@link Age.max()}
 * 
 * @author saad arkoubi
 *
 */
@Component
public class AgeValidator implements ConstraintValidator<Age, LocalDate> {

	private int minAge;
	private int maxAge;

	@Override
	public void initialize(Age age) {
		minAge = age.min();
		maxAge = age.max();
	}

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
		var diffYears = Period.between(date, LocalDate.now()).getYears();
		return minAge <= diffYears && diffYears <= maxAge;
	}

}
