package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class VerifySampleState implements SampleState {

	@Override
	public SampleState addOpinion(Sample sample, Opinion opinion, User user) {
		return this;
	}

	@Override
	public SampleState updatedState(Sample sample) {
		return this;
	}

	@Override
	public Vote getLevelVerification(Sample sample) {
		return Vote.VERIFIED;
	}

}
