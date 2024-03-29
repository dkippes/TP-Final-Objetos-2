package a.la.caza.de.las.vinchucas.samples.state;

import java.util.List;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.opinions.UndefinedOpinion;
import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Describe el estado de la muestra como votada por un experto
 */
public class ExpectVotedSample extends SampleState {

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
		if(sample.expertUserHasTheSameOpinion(opinion)) {
			sample.setState(new VerifySample());	
		}
	}
	
	@Override
	public GenericOpinionType getResult(Sample sample) {
		List<OpinionType> opinionsByExperts = sample.getOpinionsByExperts();

		if(opinionsByExperts.size() >= 2) {
			return UndefinedOpinion.UNDEFINED;
		}
		return opinionsByExperts.get(0);
	}
}
