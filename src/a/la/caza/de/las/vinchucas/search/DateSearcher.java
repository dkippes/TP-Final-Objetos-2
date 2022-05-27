package a.la.caza.de.las.vinchucas.search;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class DateSearcher {


	public List<Sample> searchSamplesWithDate(List<Sample> samples, LocalDate dateSearched) {
		// TODO Auto-generated method stub
		Stream<Sample> samplesFound = samples.stream().filter(s -> s.getCreationDate().isEqual(dateSearched)) ;
		
		return samplesFound.toList() ;
	}

}
