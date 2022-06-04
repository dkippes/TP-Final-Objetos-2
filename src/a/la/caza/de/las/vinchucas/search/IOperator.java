package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;

/* 
 * Interfaz IOperator
 *
 * Esta interfaz se encarga de comparar fechas.
 */

public interface IOperator {
	public boolean compareDate(LocalDate date1, LocalDate date2);
}
