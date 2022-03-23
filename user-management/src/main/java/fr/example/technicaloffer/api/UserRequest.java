package fr.example.technicaloffer.api;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.example.technicaloffer.constraint.annotation.Country;
import fr.example.technicaloffer.constraint.annotation.DateAge;
import fr.example.technicaloffer.constraint.annotation.Gender;
import lombok.Builder;
import lombok.Data;

/**
 * Structure of a user request
 * 
 * @author saad arkoubi
 *
 */
@Data
@Builder
public class UserRequest {

	private static final String PHONE_PATTERN = "^(0033|0|\\+33)\\d{9}";

	@NotBlank(message = "{user.username.message}")
	@Size(max = 255)
	private String username;

	// TODO Handle Deserialization error
	@JsonProperty("birth_date")
	@NotNull(message = "{user.birthdate.notnull.message}")
	@DateAge(minAge = 18)
	private LocalDate birthDate;

	@Country(message = "{user.country.message}")
	@NotBlank
	private String country;

	@Pattern(regexp = PHONE_PATTERN, message = "{user.phone.message}")
	@Size(max = 13)
	private String phone;

	@Gender
	private String gender;
}
