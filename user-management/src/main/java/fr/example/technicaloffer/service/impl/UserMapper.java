package fr.example.technicaloffer.service.impl;

import fr.example.technicaloffer.api.UserRequest;
import fr.example.technicaloffer.api.UserResponse;
import fr.example.technicaloffer.model.User;
import lombok.experimental.UtilityClass;

/**
 * Mapper utility class for the user domain
 * 
 * @author saad arkoubi
 *
 */
@UtilityClass
public class UserMapper {

	/**
	 * Convert a {@link UserRequest} to a {@link User}
	 * 
	 * @param userRequest
	 * @return {@link User}
	 */
	public static User toUser(final UserRequest userRequest) {
		return User.builder().username(userRequest.getUsername()).birthDate(userRequest.getBirthDate())
				.country(userRequest.getCountry()).gender(userRequest.getGender()).phone(userRequest.getPhone()).build();
	}

	/**
	 * Convert a {@link User} to a {@link UserResponse}
	 * 
	 * @param user
	 * @return {@link UserResponse}
	 */
	public static UserResponse toUserResponse(final User user) {
		return UserResponse.builder().username(user.getUsername()).birthDate(user.getBirthDate())
				.country(user.getCountry()).gender(user.getGender()).phone(user.getPhone()).build();
	}
}
