package a.la.caza.de.las.vinchucas.samples.state;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.opinions.UndefinedOpinion;
import a.la.caza.de.las.vinchucas.samples.Sample;

/*
 * Describe el estado basico de una muestra
 * Esta no estaria verificada, es votada por al menos 1 usuario quien fue que la creo
 */
public class BasicVotedSample extends SampleState {

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
			sample.setState(new ExpectVotedSample());
		}
	}
	
	@Override
	public GenericOpinionType getResult(Sample sample) {
		List<OpinionType> opinions = sample.getOpinionsAsList();
		Map<OpinionType, Integer> mapOpinions = mapOpinionsByOpinionType(sample, opinions);
		return getMostVotedOpinionOrUndefinedIfDraw(mapOpinions);
	}
	
	private GenericOpinionType getMostVotedOpinionOrUndefinedIfDraw(Map<OpinionType, Integer> mapOpinions) {
		Entry<OpinionType, Integer> maxEntry = mapOpinions.entrySet().iterator().next();
		for (Entry<OpinionType, Integer> entry : mapOpinions.entrySet()) {
			if (entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		mapOpinions.remove(maxEntry.getKey());
		return mapOpinions.containsValue(maxEntry.getValue()) 
				? UndefinedOpinion.UNDEFINED
				: maxEntry.getKey(); 
	}
}
