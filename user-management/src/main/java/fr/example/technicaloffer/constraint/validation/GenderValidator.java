package fr.example.technicaloffer.constraint.validation;

import java.util.Arrays;

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
		return Arrays.asList(GenderEnum.values()).stream().map(GenderEnum::name)
				.anyMatch(enumGender -> gender.strip().equals(enumGender));
	}

}
