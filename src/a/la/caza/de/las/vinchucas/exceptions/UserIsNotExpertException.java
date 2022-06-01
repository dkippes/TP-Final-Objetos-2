package a.la.caza.de.las.vinchucas.exceptions;

@SuppressWarnings("serial")
public class UserIsNotExpertException extends UserValidationException {
	public UserIsNotExpertException(String msg) {
		super(msg);
	}
}
