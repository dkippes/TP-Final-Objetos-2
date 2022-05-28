package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
	private LocalDate lastVotationDate;
	
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
		lastVotationDate = LocalDate.now().plusDays(10);
		
		filter = new FilterLastVoteDate(dateSearched);
		
		operatorEqual = new OperatorEqual();
		filter.setOperator(operatorEqual);
	}

	@Test
	void testOneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		
		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(lastVotationDate);
		when(sample2.getLastVotation()).thenReturn(lastVotationDate);
		
		List<Sample> samplesFound = filter.searchSamples(allSamples);
		
		assertEquals(samples, samplesFound);
	}
	
	@Test
	void testTwoSampleFound() {
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample2);
		
		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(lastVotationDate);
		when(sample2.getLastVotation()).thenReturn(LocalDate.now());
		
		List<Sample> samplesFound = filter.searchSamples(allSamples);
		
		assertEquals(samples, samplesFound);
	}
	
	@Test
	void testNoneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();
		
		when(sample.getLastVotation()).thenReturn(lastVotationDate);
		when(sample1.getLastVotation()).thenReturn(lastVotationDate);
		when(sample2.getLastVotation()).thenReturn(lastVotationDate);
		
		List<Sample> samplesFound = filter.searchSamples(allSamples);
		
		assertEquals(samples, samplesFound);
	}

}
