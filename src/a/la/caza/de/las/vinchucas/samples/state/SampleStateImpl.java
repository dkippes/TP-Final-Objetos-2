package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;

/*
 * Esta clase abstacta describe el estado de cada muestra.
 */
public abstract class SampleStateImpl implements ISampleState, UserValidations {

	/**
	 * Lanza una excepcion si un usuario ya voto en la muestra anteriormente
	 * @throws UserAlreadyVoteException
	 * @exception UserAlreadyVoteException
	 * @param Sample, Opinion
	 */
	@Override
	public void checkIfUserAlreadyVote(Sample sample, Opinion opinion) throws UserAlreadyVoteException {
		if (userAlreadyVote(sample, opinion)) {
			throw new UserAlreadyVoteException("User " + opinion.getUser().getName() + " already voted");
		}
	}

	/**
	 * Lanza una excepcion si el usuario no es experto
	 * @throws UserIsNotExpertException
	 * @exception UserIsNotExpertException
	 * @param Opinion
	 */
	@Override
	public void checkIfUserIsExpert(Opinion opinion) throws UserIsNotExpertException {
		if (!userIsExpert(opinion)) {
			throw new UserIsNotExpertException(
					"User " + opinion.getUser().getName() + " can not vote because is not expert/specialist");
		}
	}

	/**
	 * Indica si un usuario ya voto en la muestra anteriormente
	 * @param Sample, Opinion
	 */
	@Override
	public boolean userAlreadyVote(Sample sample, Opinion opinion) {
		return sample.userAlreadyVote(opinion.getUser());
	}

	/**
	 * Indica si un usuario es experto
	 * @param Opinion
	 */
	@Override
	public boolean userIsExpert(Opinion opinion) {
		return opinion.getUser().hasExpertKnowledge();
	}
}
