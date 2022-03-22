package fr.example.technicaloffer.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import fr.example.technicaloffer.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

	public Mono<User> findByUsername(String username);

}
