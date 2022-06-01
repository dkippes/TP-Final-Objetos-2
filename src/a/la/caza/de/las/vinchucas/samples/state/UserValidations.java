package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;

public interface UserValidations {
	public void checkIfUserAlreadyVote(Sample sample, Opinion opinion) throws UserAlreadyVoteException;

	public void checkIfUserIsExpert(Opinion opinion) throws UserIsNotExpertException;

	public boolean userAlreadyVote(Sample sample, Opinion opinion);

	public boolean userIsExpert(Opinion opinion);
}
