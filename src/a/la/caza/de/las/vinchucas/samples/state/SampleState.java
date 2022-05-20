package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

public abstract class SampleState {
	public abstract void addOpinion(Sample sample, Opinion opinion)  throws Exception;
	public abstract void updatedState(Sample sample, Opinion opinion);
	public abstract Vote getLevelVerification(Sample sample);
	
	public void checkIfUserAlreadyVote(Sample sample, Opinion opinion) throws UserAlreadyVoteException {
		if(userAlreadyVote(sample, opinion)) {
			throw new UserAlreadyVoteException("User " + opinion.getUser().getName() + " already voted");
		}
	}
	
	public void checkIfUserIsExpert(Opinion opinion) throws UserIsNotExpertException {
		if(!userIsExpert(opinion)) {
			throw new UserIsNotExpertException("User " + opinion.getUser().getName() + " can not vote because is not expert/specialist");
		}
	}
	
	public boolean userAlreadyVote(Sample sample, Opinion opinion) {
		return sample.userAlreadyVote(sample.getOpinionHistory(), opinion.getUser());
	}
	
	public boolean userIsExpert(Opinion opinion) {
		return opinion.getUser().isExpert();
	}
}
