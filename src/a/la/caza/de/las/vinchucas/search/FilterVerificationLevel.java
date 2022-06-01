package a.la.caza.de.las.vinchucas.search;

import java.util.List;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

public class FilterVerificationLevel implements IFilter {
	private Vote verLevelSearched;
	
	public FilterVerificationLevel(Vote verLevel) {
		this.verLevelSearched = verLevel;
	}

	public List<Sample> searchSamples(List<Sample> samples) {
		return samples.stream()
				.filter(sample -> sample.getLevelVerification().equals(verLevelSearched))
				.collect(Collectors.toList());
	}
}
