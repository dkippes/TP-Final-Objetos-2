package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/**
 * Describe el estado de la muestra como votada por un experto
 */
public class ExpectVotedSampleState extends SampleStateImpl {

	/**
	 * Agrega una opinion en la muestra si el usuario no voto, y es experto
	 * Una vez agregada la opinion actualiza su estado si es posible
	 * @param Sample, Opinion
	 * @throws UserValidationException
	 * @exception UserValidationException
	 */
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
