package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;

/*
 * Clase Abstracta SampleStateImpl 
 * 
 * Esta clase abstacta describe el estado de cada muestra.
 */

public abstract class SampleStateImpl implements ISampleState, UserValidations {

	@Override
	public void checkIfUserAlreadyVote(Sample sample, Opinion opinion) throws UserAlreadyVoteException {
		if (userAlreadyVote(sample, opinion)) {
			throw new UserAlreadyVoteException("User " + opinion.getUser().getName() + " already voted");
		}
	}

	@Override
	public void checkIfUserIsExpert(Opinion opinion) throws UserIsNotExpertException {
		if (!userIsExpert(opinion)) {
			throw new UserIsNotExpertException(
					"User " + opinion.getUser().getName() + " can not vote because is not expert/specialist");
		}
	}

	@Override
	public boolean userAlreadyVote(Sample sample, Opinion opinion) {
		// TODO
		return sample.userAlreadyVote(opinion.getUser());
	}

	@Override
	public boolean userIsExpert(Opinion opinion) {
		return opinion.getUser().hasExpertKnowledge();
	}
}
