package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/*
 * Describe el estado basico de una muestra
 * Esta no estaria verificada, es votada por al menos 1 usuario quien fue que la creo
 */
public class BasicVotedSampleState extends SampleStateImpl {

	/**
	 * Agrega una opinion en la muestra si el usuario no voto
	 * Una vez agregada la opinion actualiza su estado si es posible
	 * @param Sample, Opinion
	 * @throws UserValidationException
	 * @exception UserValidationException
	 */
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
	public Vote getLevelVerification() {
		return Vote.VOTED;
	}
}
