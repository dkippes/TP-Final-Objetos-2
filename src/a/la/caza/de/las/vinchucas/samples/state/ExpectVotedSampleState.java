package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class ExpectVotedSampleState extends SampleState {

	@Override
	public void addOpinion(Sample sample, Opinion opinion) throws Exception {
		checkIfUserAlreadyVote(sample, opinion);
		checkIfUserIsExpert(opinion);
		updatedState(sample, opinion);
		sample.addUserOpinion(opinion);
	}

	@Override
	public void updatedState(Sample sample, Opinion opinion) {
		if(super.userIsExpert(opinion)) {
			sample.setState(new VerifySampleState());
		}
	}

	@Override
	public Vote getLevelVerification(Sample sample) {
		return Vote.VOTED;
	}

}
