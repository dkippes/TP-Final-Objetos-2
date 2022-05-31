package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class FilterLastVoteDate implements IFilter{
	private IOperator operator;
	private LocalDate dateSearched;
	
	public void setOperator(IOperator operator) {
		this.operator = operator;
	}
	
	public FilterLastVoteDate(LocalDate date){
		this.dateSearched = date;
	}

	public List<Sample> searchSamples(List<Sample> samples) {
		return samples.stream().
				filter(sample -> this.operator.comparar(sample.getLastVotation(), dateSearched)).
				collect(Collectors.toList());
	}
}
