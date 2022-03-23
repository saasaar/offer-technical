package fr.example.technicaloffer.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.example.technicaloffer.api.CountryEnum;
import fr.example.technicaloffer.api.GenderEnum;
import fr.example.technicaloffer.api.UserRequest;
import fr.example.technicaloffer.exception.UserNotFoundException;
import fr.example.technicaloffer.model.User;
import fr.example.technicaloffer.repository.UserRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void registerUser() {
		when(userRepository.save(ArgumentMatchers.any())).thenReturn(mockUser());

		StepVerifier.create(userService.registerUser(mock(UserRequest.class))).assertNext(userResponse -> {
			assertNull(userResponse.getUsername());
			assertNotNull(userResponse.getBirthDate());
			assertEquals(GenderEnum.MAN.name(), userResponse.getGender());
		}).expectComplete();
	}

	@Test
	void findByUsernameSuccess() {

		when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(mockUser());

		StepVerifier.create(userService.findByUsername("")).assertNext(userResponse -> {
			assertNull(userResponse.getUsername());
			assertNotNull(userResponse.getBirthDate());
			assertEquals(GenderEnum.MAN.name(), userResponse.getGender());
		}).expectComplete();
	}

	@Test
	void findByUsernameException() {
		when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Mono.empty());
		StepVerifier.create(userService.findByUsername("")).expectError(UserNotFoundException.class);
	}

	private Mono<User> mockUser() {
		return Mono.just(User.builder().birthDate(LocalDate.now()).country(CountryEnum.FRANCE.name())
				.gender(GenderEnum.MAN.name()).build());
	}

}
