package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class OperatorMajor implements IOperator {

		@Override
		public boolean comparar(LocalDate fecha1, LocalDate fecha2) {
			// TODO Auto-generated method stub
			return fecha1.isAfter(fecha2);
		}
}


