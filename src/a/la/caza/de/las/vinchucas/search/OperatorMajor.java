package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;

public class OperatorMajor implements IOperator {

	@Override
	public boolean compareDate(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.isAfter(fecha2);
	}
}
