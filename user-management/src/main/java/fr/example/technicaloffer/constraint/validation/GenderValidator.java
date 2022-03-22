package fr.example.technicaloffer.constraint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import fr.example.technicaloffer.api.GenderEnum;
import fr.example.technicaloffer.constraint.annotation.Gender;

/**
 * Gender validator. <br>
 * Only values of {@link GenderEnum} are allowed.
 * 
 * @author saad arkoubi
 *
 */
@Component
public class GenderValidator implements ConstraintValidator<Gender, String> {

	@Override
	public boolean isValid(String gender, ConstraintValidatorContext context) {
		final var stripedGender = gender.strip();
		return stripedGender.equals(GenderEnum.MAN.name()) || stripedGender.equals(GenderEnum.WOMEN.name());
	}

}
