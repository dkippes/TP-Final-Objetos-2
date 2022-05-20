package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVote;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public interface SampleState {
	public void addOpinion(Sample sample, Opinion opinion) throws UserAlreadyVote;
	public void updatedState(Sample sample, Opinion opinion);
	public Vote getLevelVerification(Sample sample);
}
