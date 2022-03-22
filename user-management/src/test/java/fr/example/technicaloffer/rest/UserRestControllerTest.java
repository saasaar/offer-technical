package fr.example.technicaloffer.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import fr.example.technicaloffer.api.CountryEnum;
import fr.example.technicaloffer.api.ErrorType;
import fr.example.technicaloffer.api.GenderEnum;
import fr.example.technicaloffer.api.ResponseError;
import fr.example.technicaloffer.api.ResponseError.ErrorResource;
import fr.example.technicaloffer.api.UserRequest;
import fr.example.technicaloffer.api.UserResponse;
import fr.example.technicaloffer.exception.UserNotFoundException;
import fr.example.technicaloffer.exception.UserNotFoundMessage;
import fr.example.technicaloffer.service.UserService;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserRestController.class)
public class UserRestControllerTest {

	private static final String USERNAME = "username";
	private static final LocalDate TWENTY_YEARS_OLD = LocalDate.now().minusYears(20);

	@MockBean
	private UserService userService;

	@Autowired
	private WebTestClient webClient;

	@Value("${app.api.latest-prefix-endpoint}")
	private String prefixEndpoint;

	private String usersUri() {
		return prefixEndpoint.concat("/users");
	}

	@Test
	void findUserByUsernameIs200() {
		when(userService.findByUsername(ArgumentMatchers.anyString())).thenReturn(mockUserResponse());
		webClient.get().uri(uriBuilder -> uriBuilder.path(usersUri()).queryParam(USERNAME, "toto").build()).exchange()
				.expectStatus().isOk().expectBody(UserResponse.class).isEqualTo(userResponse());

		Mockito.verify(userService, times(1)).findByUsername(ArgumentMatchers.anyString());
	}

	@Test
	void findUserByUsernameIs404() {
		ResponseError responseError = new ResponseError(List.of(new ErrorResource(HttpStatus.NOT_FOUND.value(),
				ErrorType.RESOURCE_NOT_FOUND, UserNotFoundMessage.ERROR_MESSAGE)));
		when(userService.findByUsername(ArgumentMatchers.anyString()))
				.thenReturn(Mono.error(UserNotFoundException::new));
		webClient.get().uri(uriBuilder -> uriBuilder.path(usersUri()).queryParam(USERNAME, "toto").build()).exchange()
				.expectStatus().isNotFound().expectBody(ResponseError.class).isEqualTo(responseError);

		Mockito.verify(userService, times(1)).findByUsername(ArgumentMatchers.anyString());
	}

	@Test
	void findUserByUsernameIs400() {
		webClient.get().uri(uriBuilder -> uriBuilder.path(usersUri()).queryParam(USERNAME, "").build()).exchange()
				.expectStatus().isBadRequest().expectBody().jsonPath("errors").isArray().jsonPath("errors[0].status")
				.isEqualTo(HttpStatus.BAD_REQUEST.value()).jsonPath("errors[0].type")
				.isEqualTo(ErrorType.VALIDATION_ERROR.name());

		Mockito.verify(userService, times(0)).findByUsername(ArgumentMatchers.anyString());
	}

	@Test
	void registerUserIs201() {
		var userRequest = UserRequest.builder().gender(GenderEnum.MAN.name()).country(CountryEnum.FRANCE.name())
				.username(USERNAME).birthDate(TWENTY_YEARS_OLD).build();

		when(userService.registerUser(ArgumentMatchers.any())).thenReturn(mockUserResponse());
		webClient.post().uri(usersUri()).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(userRequest)).exchange().expectStatus().isCreated()
				.expectBody(UserResponse.class).isEqualTo(userResponse());

		Mockito.verify(userService, times(1)).registerUser(ArgumentMatchers.any());

	}

	@Test
	void registerUserIs400() {
		var userRequest = UserRequest.builder().gender("RANDOM").country(CountryEnum.FRANCE.name()).username(USERNAME)
				.birthDate(TWENTY_YEARS_OLD).build();

		webClient.post().uri(usersUri()).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(userRequest)).exchange().expectStatus().isBadRequest().expectBody()
				.jsonPath("errors").isArray().jsonPath("errors[0].status").isEqualTo(HttpStatus.BAD_REQUEST.value())
				.jsonPath("errors[0].type").isEqualTo(ErrorType.VALIDATION_ERROR.name()).jsonPath("errors[0].message");

		Mockito.verify(userService, times(0)).registerUser(ArgumentMatchers.any());

	}

	private Mono<UserResponse> mockUserResponse() {
		return Mono.just(userResponse());
	}

	private UserResponse userResponse() {
		return UserResponse.builder().username(USERNAME).build();
	}
}
