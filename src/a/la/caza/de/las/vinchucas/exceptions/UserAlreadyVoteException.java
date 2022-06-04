package a.la.caza.de.las.vinchucas.exceptions;

/**
 * Es una excepcion para cada usuario que ya haya emitido su voto.
 */
@SuppressWarnings("serial")
public class UserAlreadyVoteException extends UserIsNotExpertException {
	public UserAlreadyVoteException(String msg) {
		super(msg);
	}
}
