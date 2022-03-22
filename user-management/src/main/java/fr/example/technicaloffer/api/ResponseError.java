package fr.example.technicaloffer.api;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * Structure of an error response.
 * @author saad arkoubi
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ResponseError {

	private List<ErrorResource> errors = new ArrayList<>();

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	@EqualsAndHashCode
	public static class ErrorResource {
		/**
		 * HTTP code
		 */
		private Integer status;
		/**
		 * Error type 
		 */
		private ErrorType type;
		/**
		 * Message error
		 */
		private String message;
	}
}
