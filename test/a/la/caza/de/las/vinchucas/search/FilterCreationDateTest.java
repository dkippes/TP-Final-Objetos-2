package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.samples.Sample;

class FilterCreationDateTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;
	private FilterCreationDate dateSearcher;
	private LocalDate dateSearched;
	private LocalDate otherDate;
	private OperatorEqual operatorEqual;
	private OperatorMajor operatorMajor;
	private OperatorMinor operatorMinor;

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

		operatorEqual = new OperatorEqual();
		operatorMajor = new OperatorMajor();
		operatorMinor = new OperatorMinor();

		dateSearcher = new FilterCreationDate(dateSearched);

	}
	
	@Test
	void testNoneOperatorSet() {

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(otherDate);
		when(sample2.getCreationDate()).thenReturn(otherDate);

		List<Sample> samplesFound = dateSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
	}
	
	@Test
	void testOneSampleFoundWithSetOperatorEqual() {
		
		dateSearcher.setOperator(operatorEqual);

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(otherDate);
		when(sample2.getCreationDate()).thenReturn(otherDate);

		List<Sample> samplesFound = dateSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
	}

	@Test
	void testOneSampleFound() {

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(otherDate);
		when(sample2.getCreationDate()).thenReturn(otherDate);

		List<Sample> samplesFound = dateSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
	}

	@Test
	void testTwoSampleFound() {

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample2);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(otherDate);
		when(sample2.getCreationDate()).thenReturn(dateSearched);

		List<Sample> samplesFound = dateSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
	}

	@Test
	void testNoneSampleFound() {

		List<Sample> samples = new ArrayList<Sample>();

		when(sample.getCreationDate()).thenReturn(otherDate);
		when(sample1.getCreationDate()).thenReturn(otherDate);
		when(sample2.getCreationDate()).thenReturn(otherDate);

		List<Sample> samplesFound = dateSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
	}

	@Test
	void testMajorDate() {

		dateSearcher.setOperator(operatorMajor);

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample1);
		samples.add(sample2);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(otherDate);
		when(sample2.getCreationDate()).thenReturn(otherDate);

		List<Sample> samplesFound = dateSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
	}

	@Test
	void testMinorDate() {

		otherDate = LocalDate.now().minusDays(10);

		dateSearcher.setOperator(operatorMinor);

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample1);
		samples.add(sample2);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(otherDate);
		when(sample2.getCreationDate()).thenReturn(otherDate);

		List<Sample> samplesFound = dateSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
	}
	

	
}
