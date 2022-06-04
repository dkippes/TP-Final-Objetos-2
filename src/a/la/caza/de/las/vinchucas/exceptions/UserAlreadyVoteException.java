package a.la.caza.de.las.vinchucas.exceptions;

/**
 * Clase UserAlreadyVoteException 
 * 
 * Esta clase una excepción para cada usuario que ya emitió su voto.
 */


@SuppressWarnings("serial")
public class UserAlreadyVoteException extends UserIsNotExpertException {
	public UserAlreadyVoteException(String msg) {
		super(msg);
	}
}
