package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVote;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class BasicVotedSampleState implements SampleState {

	@Override
	public SampleState addOpinion(Sample sample, Opinion opinion) throws UserAlreadyVote {
		return this; //Temporal.
	}

	@Override
	public SampleState updatedState(Sample sample) {
		return this;
	}

	@Override
	public Vote getLevelVerification(Sample sample) {
		return Vote.VOTED;
	}
}
