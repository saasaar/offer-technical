package fr.example.technicaloffer.exception;

/**
 * Exception thrown if the user is not found
 * 
 * @author saad arkoubi
 *
 */
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super(UserNotFoundMessage.ERROR_MESSAGE);
	}

}
