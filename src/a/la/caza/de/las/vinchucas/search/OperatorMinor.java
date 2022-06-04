package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;

/**
 * 
 * Clase OperatorMinor. 
 *
 * Esta clase es un operador de menor.
 */


public class OperatorMinor implements IOperator {

	@Override
	public boolean compareDate(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.isBefore(fecha2);
	}
}
