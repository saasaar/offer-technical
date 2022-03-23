package fr.example.technicaloffer.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.example.technicaloffer.api.ResponseError;
import fr.example.technicaloffer.api.UserRequest;
import fr.example.technicaloffer.api.UserResponse;
import fr.example.technicaloffer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Mono;

/**
 * API endpoint for user management.
 * 
 * @author saad arkoubi
 *
 */
@RestController
@RequestMapping("${app.api.latest-prefix-endpoint}")
@Validated
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get a user by its username")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the user", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid username query supplied", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class)) }) })
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<UserResponse> findUserByUsername(@RequestParam @NotBlank String username) {
		return userService.findByUsername(username);
	}

	@Operation(summary = "Register a user")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User registred", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid user request", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class)) }),
			@ApiResponse(responseCode = "409", description = "User already exist", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseError.class)) })})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
		return userService.registerUser(userRequest);
	}
}
