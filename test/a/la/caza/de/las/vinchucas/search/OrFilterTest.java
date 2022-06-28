package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;

class OrFilterTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;

	private LocalDate dateSearched;
	private LocalDate otherDate;
	private GenericOpinionType insectSearched;
	private GenericOpinionType otherInsect;

	private FilterLastVoteDate lastVotedateFilter;
	private FilterInsect insectFilter;

	private OrFilter orFilter;
	private OperatorEqual operatorEqual;

	@BeforeEach
	void setUp() throws Exception {

		allSamples = new ArrayList<Sample>();
		sample = mock(Sample.class);
		sample1 = mock(Sample.class);
		sample2 = mock(Sample.class);
		allSamples.add(sample);
		allSamples.add(sample1);
		allSamples.add(sample2);

		dateSearched = LocalDate.now();
		otherDate = LocalDate.now().plusDays(10);
		insectSearched = OpinionType.VINCHUCA_GUASAYANA;
		otherInsect = OpinionType.CHINCHE_FOLIADA;

		lastVotedateFilter = new FilterLastVoteDate(dateSearched);
		insectFilter = new FilterInsect(insectSearched);

		orFilter = new OrFilter(lastVotedateFilter, insectFilter);

		operatorEqual = new OperatorEqual();
		lastVotedateFilter.setOperator(operatorEqual);

	}

	@Test
	void testSearchSamplesWithLastVotationDateAndInsect() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample1);
		samples.add(sample2);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(otherDate);

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		List<Sample> samplesFound = orFilter.searchSamples(allSamples);

		assertTrue(samplesFound.containsAll(samples));
	}
	// ningun filtro
	// todos los unitarios
	
}
