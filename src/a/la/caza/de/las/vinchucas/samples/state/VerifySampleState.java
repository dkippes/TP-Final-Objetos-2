package a.la.caza.de.las.vinchucas.samples.state;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/**
 * Describe el estado de la muestra como verificada
 */
public class VerifySampleState extends SampleState {

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
		Map<OpinionType, Integer> mapOpinions = mapOpinionsByOpinionType(sample);
		Entry<OpinionType, Integer> maxEntry = mapOpinions.entrySet().iterator().next();
		for (Entry<OpinionType, Integer> entry : mapOpinions.entrySet()) {
			if (entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return maxEntry.getKey();
	}

	private List<OpinionType> getOpinionsByExpertsAsList(Sample sample) {
		return sample.getOpinionHistory().stream().filter(o -> o.getUser().hasExpertKnowledge())
				.map(opinion -> opinion.getOpinionType()).collect(Collectors.toList());
	}

	private Map<OpinionType, Integer> mapOpinionsByOpinionType(Sample sample) {
		Map<OpinionType, Integer> mapOpinions = new TreeMap<>();
		getOpinionsByExpertsAsList(sample).forEach(opinion -> mapOpinions.put(opinion,
				Collections.frequency(getOpinionsByExpertsAsList(sample), opinion)));
		return mapOpinions;
	}
}
