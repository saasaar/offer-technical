package fr.example.technicaloffer.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User entity
 * 
 * @author saad arkoubi
 *
 */
@Builder
@Getter
@Setter
@ToString
@Table("\"user\"")
public class User {

	@Id
	private UUID id;

	@NotBlank
	@Length(max = 255)
	private String username;

	@Column("birth_date")
	@NotNull
	private LocalDate birthDate;

	@NotBlank
	@Length(max = 30)
	private String country;

	@Length(max = 13)
	private String phone;

	@Length(max = 5)
	private String gender;

}
