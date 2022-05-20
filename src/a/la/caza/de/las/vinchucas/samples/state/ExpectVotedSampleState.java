package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVote;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class ExpectVotedSampleState implements SampleState {

	@Override
	public void addOpinion(Sample sample, Opinion opinion) throws UserAlreadyVote {
		if(sample.userAlreadyVote(sample.getOpinionHistory(), opinion.getUser()) || !opinion.getUser().isExpert()) {
			throw new UserAlreadyVote("User " + opinion.getUser().getName() + " already voted or has not permission to vote");
		}
		updatedState(sample, opinion);
		sample.addUserOpinion(opinion);
	}

	@Override
	public void updatedState(Sample sample, Opinion opinion) {
		if(opinion.getUser().isExpert()) {
			sample.setState(new VerifySampleState());
		}
	}

	@Override
	public Vote getLevelVerification(Sample sample) {
		return Vote.VOTED;
	}

}
