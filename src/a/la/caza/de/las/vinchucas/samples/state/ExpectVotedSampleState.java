package a.la.caza.de.las.vinchucas.samples.state;

import java.util.List;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.opinions.UndefinedOpinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/**
 * Describe el estado de la muestra como votada por un experto
 */
public class ExpectVotedSampleState extends SampleState {

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
			sample.setState(new VerifySampleState());	
		}
	}

	@Override
	public Vote getLevelVerification() {
		return Vote.VOTED;
	}
	
	@Override
	public GenericOpinionType getResult(Sample sample) {
		List<OpinionType> opinionsByExperts = getOpinionsByExperts(sample);

		if(opinionsByExperts.size() >= 2) {
			return UndefinedOpinion.UNDEFINED;
		}
		return opinionsByExperts.get(0);
	}

	private List<OpinionType> getOpinionsByExperts(Sample sample) {
		return sample.getOpinionHistory().stream()
				.filter(o -> o.getUser().hasExpertKnowledge())
				.map(o -> o.getOpinionType())
				.collect(Collectors.toList());
	}
}
