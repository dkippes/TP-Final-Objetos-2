package a.la.caza.de.las.vinchucas.samples.state;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/**
 * Describe el estado de la muestra como verificada
 */
public class VerifySample extends SampleState {

	/**
	 * Una muestra verificada no puede agregar opiniones
	 * 
	 * @param Sample, Opinion
	 */
	@Override
	public void addOpinion(Sample sample, Opinion opinion) {
	}

	/**
	 * Una muestra verificada no puede actualizarse
	 * 
	 * @param Sample, Opinion
	 */
	@Override
	public void updatedState(Sample sample, Opinion opinion) {
	}

	@Override
	public Vote getLevelVerification() {
		return Vote.VERIFIED;
	}

	@Override
	public GenericOpinionType getResult(Sample sample) {
		List<OpinionType> opinions = sample.getOpinionsByExperts();
		Map<OpinionType, Integer> mapOpinions = mapOpinionsByOpinionType(sample, opinions);
		Entry<OpinionType, Integer> maxEntry = mapOpinions.entrySet().iterator().next();
		for (Entry<OpinionType, Integer> entry : mapOpinions.entrySet()) {
			if (entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return maxEntry.getKey();
	}
}
