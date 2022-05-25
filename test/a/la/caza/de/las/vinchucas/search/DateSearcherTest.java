package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.samples.Sample;

class DateSearcherTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;
	private DateSearcher dateSearcher;
	private LocalDate dateSearched;
	private LocalDate creationDate;

	@BeforeEach
	void setUp() throws Exception {
		dateSearcher = new DateSearcher();
		allSamples = new ArrayList<Sample>();
		sample = mock(Sample.class);
		sample1 = mock(Sample.class);
		sample2 = mock(Sample.class);
		allSamples.add(sample);
		allSamples.add(sample1);
		allSamples.add(sample2);
		dateSearched = LocalDate.now();
		creationDate = LocalDate.now().plusDays(10);
		
	}

	@Test
	void testOneSampleSearchedFound() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		when(sample1.getCreationDate()).thenReturn(creationDate);
		when(sample2.getCreationDate()).thenReturn(creationDate);
		
		List<Sample> samplesFound = dateSearcher.searchSamplesWithDate(allSamples,dateSearched);
		
		assertEquals(samples, samplesFound);
	}
	
	@Test
	void testTwoSampleSearchedFound() {
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample2);
		
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		when(sample1.getCreationDate()).thenReturn(creationDate);
		when(sample2.getCreationDate()).thenReturn(LocalDate.now());
		
		List<Sample> samplesFound = dateSearcher.searchSamplesWithDate(allSamples,dateSearched);
		
		assertEquals(samples, samplesFound);
	}
	
	@Test
	void testNoneSampleSearchedFound() {
		List<Sample> samples = new ArrayList<Sample>();
		
		when(sample.getCreationDate()).thenReturn(creationDate);
		when(sample1.getCreationDate()).thenReturn(creationDate);
		when(sample2.getCreationDate()).thenReturn(creationDate);
		
		List<Sample> samplesFound = dateSearcher.searchSamplesWithDate(allSamples,dateSearched);
		
		assertEquals(samples, samplesFound);
	}
	
	

}
