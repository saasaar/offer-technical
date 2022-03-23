package fr.example.technicaloffer.api;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Structure of an error response.
 * 
 * @author saad arkoubi
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseError {

	private List<ErrorResource> errors = new ArrayList<>();

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
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
