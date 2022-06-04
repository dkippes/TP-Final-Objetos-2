package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

public class ExpectVotedSampleState extends SampleStateImpl {

	@Override
	public void addOpinion(Sample sample, Opinion opinion) throws UserValidationException {
		checkIfUserAlreadyVote(sample, opinion);
		checkIfUserIsExpert(opinion);
		updatedState(sample, opinion);
		sample.addUserOpinion(opinion);
	}

	@Override
	public void updatedState(Sample sample, Opinion opinion) {
		sample.setState(new VerifySampleState());
	}

	@Override
	public Vote getLevelVerification() {
		return Vote.VOTED;
	}
}
