package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class VerifySampleState implements SampleState {

	@Override
	public void addOpinion(Sample sample, Opinion opinion) {
	}

	@Override
	public void updatedState(Sample sample, Opinion opinion) {
	}

	@Override
	public Vote getLevelVerification(Sample sample) {
		return Vote.VERIFIED;
	}
}
