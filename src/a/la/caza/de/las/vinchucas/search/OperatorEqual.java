package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class OperatorEqual implements IOperator {

	@Override
	public boolean comparar(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.equals(fecha2);
	}

	
	/**public List<Sample> searchSamples(List<Sample> samples, LocalDate date) {
		
		
		Stream<Sample> samplesFound = samples.stream().filter(s -> s.getCreationDate().isEqual(date)) ;
		return samplesFound.collect(Collectors.toList()) ;
	}*/
}
