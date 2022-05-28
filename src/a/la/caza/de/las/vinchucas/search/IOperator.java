package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;


public interface IOperator {
	
	public boolean comparar(LocalDate fecha1, LocalDate fecha2); 
	//public List<Sample> searchSamples(List<Sample> samples, LocalDate date);

}

