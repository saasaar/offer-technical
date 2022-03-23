package fr.example.technicaloffer.exception;

/**
 * Exception thrown if the user is not found
 * 
 * @author saad arkoubi
 *
 */
public class UserAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException() {
		super(UserNotFoundMessage.USER_ALREADY_EXIST);
	}

}
