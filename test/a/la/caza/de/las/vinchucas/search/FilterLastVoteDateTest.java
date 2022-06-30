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

class FilterLastVoteDateTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;

	private FilterLastVoteDate filter;

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

		filter = new FilterLastVoteDate(dateSearched);

		operatorEqual = new OperatorEqual();
		operatorMajor = new OperatorMajor();
		operatorMinor = new OperatorMinor();
		filter.setOperator(operatorEqual);
	}
	
	@Test
	void testNoneOperatorSet() {

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getLastVotation()).thenReturn(dateSearched);
		when(sample1.getLastVotation()).thenReturn(otherDate);
		when(sample2.getLastVotation()).thenReturn(otherDate);

		List<Sample> samplesFound = filter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}

	@Test
	void testOneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(otherDate);
		when(sample2.getLastVotation()).thenReturn(otherDate);

		List<Sample> samplesFound = filter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}
	
	@Test
	void testOneSampleFoundWithSetEqualOperator() {
		
		filter.setOperator(operatorMajor);
		filter.setOperator(operatorEqual);
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(otherDate);
		when(sample2.getLastVotation()).thenReturn(otherDate);

		List<Sample> samplesFound = filter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}

	@Test
	void testTwoSampleFound() {

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample2);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(otherDate);
		when(sample2.getLastVotation()).thenReturn(LocalDate.now());

		List<Sample> samplesFound = filter.searchSamples(allSamples);
		//verify
		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}

	@Test
	void testNoneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();

		when(sample.getLastVotation()).thenReturn(otherDate);
		when(sample1.getLastVotation()).thenReturn(otherDate);
		when(sample2.getLastVotation()).thenReturn(otherDate);

		List<Sample> samplesFound = filter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}
	
	@Test
	void testMajorDate() {

		filter.setOperator(operatorMajor);

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample1);
		samples.add(sample2);

		when(sample.getLastVotation()).thenReturn(dateSearched);
		when(sample1.getLastVotation()).thenReturn(otherDate);
		when(sample2.getLastVotation()).thenReturn(otherDate);

		List<Sample> samplesFound = filter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}

	@Test
	void testMinorDate() {

		otherDate = LocalDate.now().minusDays(10);

		filter.setOperator(operatorMinor);

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample1);
		samples.add(sample2);

		when(sample.getLastVotation()).thenReturn(dateSearched);
		when(sample1.getLastVotation()).thenReturn(otherDate);
		when(sample2.getLastVotation()).thenReturn(otherDate);

		List<Sample> samplesFound = filter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}
}
