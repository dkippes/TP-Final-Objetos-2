package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.samples.Sample;

/*
 * Clase FilterCreationDate
 * 
 * Esta clase se encarga de filtrar cada muestra por una fecha determinada.
 * 
 */

public class FilterCreationDate implements IFilter {
	private LocalDate dateSearched;
	private IOperator operator;
	
	public FilterCreationDate(LocalDate date) {
		this.dateSearched = date;
	}

	public void setOperator(IOperator operator) {
		this.operator = operator;
	}

	public List<Sample> searchSamples(List<Sample> samples) {
		return samples.stream()
				.filter(sample -> this.operator.compareDate(sample.getCreationDate(), dateSearched))
				.collect(Collectors.toList());
	}
}
