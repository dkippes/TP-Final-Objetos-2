package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

public interface IOperator {
	public boolean comparar(LocalDate date1, LocalDate date2); 
}

