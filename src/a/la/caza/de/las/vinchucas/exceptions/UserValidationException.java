package a.la.caza.de.las.vinchucas.exceptions;

/**
 * Clase UserValidationException
 * 
 * Es una clase génerica que describe las excepciones de las validaciones de cada User.
 *
 */

@SuppressWarnings("serial")
public class UserValidationException extends Exception {
	public UserValidationException(String msg) {
		super(msg);
	}
}
