package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;

/**
 * Protocolo de operadores
 */
public interface IOperator {
	public boolean compareDate(LocalDate date1, LocalDate date2);
}
