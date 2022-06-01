package a.la.caza.de.las.vinchucas.search;

import java.util.List;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class FilterInsect implements IFilter{
	private String insectSearched;
	
	public FilterInsect(String insect){
		this.insectSearched = insect;
	}
	
	public List<Sample> searchSamples(List<Sample> samples) {
		return samples.stream()
				.filter(sample -> sample.getActualResult().equals(insectSearched))
				.collect(Collectors.toList());
	}
}
