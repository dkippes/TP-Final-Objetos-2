package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

public class BasicVotedSampleState extends SampleStateImpl {

	@Override
	public void addOpinion(Sample sample, Opinion opinion) throws UserAlreadyVoteException {
		super.checkIfUserAlreadyVote(sample, opinion);
		updatedState(sample, opinion);
		sample.addUserOpinion(opinion);
	}

	@Override
	public void updatedState(Sample sample, Opinion opinion) {
		if (super.userIsExpert(opinion)) {
			sample.setState(new ExpectVotedSampleState());
		}
	}

	@Override
	public Vote getLevelVerification(Sample sample) {
		return Vote.VOTED;
	}
}
