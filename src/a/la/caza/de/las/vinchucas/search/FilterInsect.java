package a.la.caza.de.las.vinchucas.search;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class FilterInsect implements IFilter{
	
	private String insectSearched;
	
	public FilterInsect(String insect){
		
		this.insectSearched = insect;
	}
	

	public List<Sample> searchSamples(List<Sample> samples) {
		// TODO Auto-generated method stub
		Stream<Sample> samplesFound = samples.stream().filter(s -> s.getActualResult().equals(insectSearched)) ;
		
		return samplesFound.collect(Collectors.toList()) ;
	}

}
