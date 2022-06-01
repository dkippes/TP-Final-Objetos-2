package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;

public interface IOperator {
	public boolean compareDate(LocalDate date1, LocalDate date2);
}
