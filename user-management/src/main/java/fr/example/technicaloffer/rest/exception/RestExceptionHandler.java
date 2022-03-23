package fr.example.technicaloffer.rest.exception;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import fr.example.technicaloffer.api.ErrorType;
import fr.example.technicaloffer.api.ResponseError;
import fr.example.technicaloffer.api.ResponseError.ErrorResource;
import fr.example.technicaloffer.exception.UserAlreadyExistException;
import fr.example.technicaloffer.exception.UserNotFoundException;
import fr.example.technicaloffer.rest.UserRestController;
import io.swagger.v3.oas.annotations.Hidden;
import reactor.core.publisher.Mono;

/**
 * Handler of thrown exception by {@link UserRestController}. <br>
 * Customize {@link HttpStatus} and the {@link ResponseBody}.
 * 
 * @author saad arkoubi
 *
 */
@RestControllerAdvice
public class RestExceptionHandler {

	private static final String WHITESPACE = " ";

	/**
	 * Handle {@link WebExchangeBindException}
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(WebExchangeBindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Mono<ResponseError> handleWebExchangeBindException(WebExchangeBindException exception) {
		ResponseError responseError = new ResponseError();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(sourceError(error)).append(WHITESPACE).append(error.getDefaultMessage());
			ErrorResource errorResource = new ErrorResource(HttpStatus.BAD_REQUEST.value(), ErrorType.VALIDATION_ERROR,
					strBuilder.toString());
			responseError.getErrors().add(errorResource);
		});
		return Mono.just(responseError);
	}

	/**
	 * Handle {@link ConstraintViolationException}
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Mono<ResponseError> handleConstraintViolationException(ConstraintViolationException exception) {
		return Mono.just(new ResponseError(List.of(new ErrorResource(HttpStatus.BAD_REQUEST.value(),
				ErrorType.VALIDATION_ERROR, exception.getMessage()))));
	}

	/**
	 * Handle {@link UserNotFoundException}
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@Hidden
	public Mono<ResponseError> handleUserNotFoundException(UserNotFoundException exception) {
		return Mono.just(new ResponseError(List.of(new ErrorResource(HttpStatus.NOT_FOUND.value(),
				ErrorType.RESOURCE_NOT_FOUND, exception.getMessage()))));

	}
	
	/**
	 * Handle {@link UserAlreadyExistException}
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(UserAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@Hidden
	public Mono<ResponseError> handleUserAlreadyExistException(UserAlreadyExistException exception) {
		return Mono.just(new ResponseError(List.of(new ErrorResource(HttpStatus.CONFLICT.value(),
				ErrorType.RESOURCE_ALREADY_EXIST, exception.getMessage()))));
	}

	/**
	 * 
	 * @return field if validation failed on a field, otherwise return object name
	 */
	private String sourceError(ObjectError error) {
		return (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
	}

}
