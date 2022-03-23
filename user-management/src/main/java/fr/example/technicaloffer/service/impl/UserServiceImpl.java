package fr.example.technicaloffer.service.impl;

import org.springframework.stereotype.Service;

import fr.example.technicaloffer.api.UserRequest;
import fr.example.technicaloffer.api.UserResponse;
import fr.example.technicaloffer.exception.UserAlreadyExistException;
import fr.example.technicaloffer.exception.UserNotFoundException;
import fr.example.technicaloffer.model.User;
import fr.example.technicaloffer.repository.UserRepository;
import fr.example.technicaloffer.service.UserService;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<UserResponse> registerUser(UserRequest userRequest) {
		var user = UserMapper.toUser(userRequest);

		return userRepository.findByUsername(userRequest.getUsername())
				.flatMap(u -> Mono.error(UserAlreadyExistException::new))
				.switchIfEmpty(Mono.defer(() -> userRepository.save(user))).cast(User.class)
				.map(UserMapper::toUserResponse);
	}

	@Override
	public Mono<UserResponse> findByUsername(String userName) {
		return userRepository.findByUsername(userName).switchIfEmpty(Mono.error(UserNotFoundException::new)).cache()
				.map(UserMapper::toUserResponse);
	}

}
