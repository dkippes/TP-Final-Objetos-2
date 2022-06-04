package a.la.caza.de.las.vinchucas.exceptions;

/**
 * Clase UserIsNotExpertExcepction 
 * 
 * Esta clase describe una excepción para cada usuario no experto. 
 * 
 */

@SuppressWarnings("serial")
public class UserIsNotExpertException extends UserValidationException {
	public UserIsNotExpertException(String msg) {
		super(msg);
	}
}
