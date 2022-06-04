package a.la.caza.de.las.vinchucas.exceptions;

/**
 * Es una excepcion generica para las validaciones de usuario.
 */
@SuppressWarnings("serial")
public class UserValidationException extends Exception {
	public UserValidationException(String msg) {
		super(msg);
	}
}
