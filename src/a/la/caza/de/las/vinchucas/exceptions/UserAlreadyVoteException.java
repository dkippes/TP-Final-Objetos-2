package a.la.caza.de.las.vinchucas.exceptions;

@SuppressWarnings("serial")
public class UserAlreadyVoteException extends UserIsNotExpertException {
	public UserAlreadyVoteException(String msg) {
		super(msg);
	}
}
