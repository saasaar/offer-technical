package fr.example.technicaloffer.service;

import fr.example.technicaloffer.api.UserRequest;
import fr.example.technicaloffer.api.UserResponse;
import reactor.core.publisher.Mono;

public interface UserService {

	public Mono<UserResponse> registerUser(UserRequest userRequest);

	public Mono<UserResponse> findByUsername(String userName);

}
