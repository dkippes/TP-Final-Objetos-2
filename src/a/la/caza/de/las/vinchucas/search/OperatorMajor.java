package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;

/**
 * Esta clase es un operador de mayor.
 */
public class OperatorMajor implements IOperator {

	@Override
	public boolean compareDate(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.isAfter(fecha2);
	}
}
