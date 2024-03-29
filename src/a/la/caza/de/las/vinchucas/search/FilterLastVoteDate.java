package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.samples.Sample;


/**
 * Esta clase se encarga de filtrar cada muestra segun la última fecha de votación
 */
public class FilterLastVoteDate implements IFilter {
	private IOperator operator;
	private LocalDate dateSearched;
	
	public FilterLastVoteDate(LocalDate date) {
		this.dateSearched = date;
		this.operator = new OperatorEqual();
	}

	public void setOperator(IOperator operator) {
		this.operator = operator;
	}

	public List<Sample> searchSamples(List<Sample> samples) {
		return samples.stream()
				.filter(sample -> this.operator.compareDate(sample.getLastVotation(), dateSearched))
				.collect(Collectors.toList());
	}
}
