package a.la.caza.de.las.vinchucas.search;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

public class FilterVerificationLevel implements IFilter {
	
	private Vote verLevelSearched;
	
	public FilterVerificationLevel(Vote verLevel) {
		
		this.verLevelSearched = verLevel;
	}

	public List<Sample> searchSamples(List<Sample> samples) {
		// TODO Auto-generated method stub
		Stream<Sample> samplesFound = samples.stream().filter(s -> s.getLevelVerification().equals(verLevelSearched)) ;
		
		return samplesFound.collect(Collectors.toList()) ;
	}
	

}
