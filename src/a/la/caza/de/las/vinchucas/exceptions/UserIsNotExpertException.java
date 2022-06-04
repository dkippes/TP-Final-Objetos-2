package a.la.caza.de.las.vinchucas.exceptions;

/**
 * Es una excepcion para usuario no experto. 
 */
@SuppressWarnings("serial")
public class UserIsNotExpertException extends UserValidationException {
	public UserIsNotExpertException(String msg) {
		super(msg);
	}
}
