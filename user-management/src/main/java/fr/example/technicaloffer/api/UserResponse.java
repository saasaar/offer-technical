package fr.example.technicaloffer.api;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * Structure of a user response.
 * 
 * @author saad arkoubi
 *
 */
@Data
@Builder
public class UserResponse {

	private String username;

	@JsonProperty("birth_date")
	private LocalDate birthDate;

	private String country;

	private String phone;

	private String gender;
}
