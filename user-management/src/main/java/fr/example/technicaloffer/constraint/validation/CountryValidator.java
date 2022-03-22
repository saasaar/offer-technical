package fr.example.technicaloffer.constraint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import fr.example.technicaloffer.api.CountryEnum;
import fr.example.technicaloffer.constraint.annotation.Country;

/**
 * Country validator. <br>
 * Only values of {@link CountryEnum} are allowed.
 * @author saad arkoubi
 *
 */
@Component
public class CountryValidator implements ConstraintValidator<Country, String> {

	@Override
	public boolean isValid(String country, ConstraintValidatorContext context) {
		return country.strip().equals(CountryEnum.FRANCE.name());
	}

}
