package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;

/**
 * 
 * Clase OperatorEqual  
 *
 * Esta clase es un operador de igualdad.
 */

public class OperatorEqual implements IOperator {

	@Override
	public boolean compareDate(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.equals(fecha2);
	}

	/**public List<Sample> searchSamples(List<Sample> samples, LocalDate date) {
		
		
		Stream<Sample> samplesFound = samples.stream().filter(s -> s.getCreationDate().isEqual(date)) ;
		return samplesFound.collect(Collectors.toList()) ;
	}*/
}
