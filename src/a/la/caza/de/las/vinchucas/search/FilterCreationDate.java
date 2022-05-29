package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class FilterCreationDate implements IFilter{
	private LocalDate dateSearched;
	private IOperator operator;
	
	public void setOperator(IOperator operator) {
		this.operator = operator;
	}
	
	public FilterCreationDate(LocalDate date){
		this.dateSearched = date; 
	}

	public List<Sample> searchSamples(List<Sample> samples) {
		return samples.
				stream().
				filter(sample -> this.operator.comparar(sample.getCreationDate(), dateSearched)).
				collect(Collectors.toList());
	}
}
