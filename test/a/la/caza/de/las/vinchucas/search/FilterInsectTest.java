package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;

class FilterInsectTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;

	private GenericOpinionType insectSearched;
	private GenericOpinionType otherInsect;

	private FilterInsect insectSearcher;

	@BeforeEach
	void setUp() throws Exception {

		allSamples = new ArrayList<Sample>();
		sample = mock(Sample.class);
		sample1 = mock(Sample.class);
		sample2 = mock(Sample.class);
		allSamples.add(sample);
		allSamples.add(sample1);
		allSamples.add(sample2);

		insectSearched = OpinionType.VINCHUCA_GUASAYANA;
		otherInsect = OpinionType.CHINCHE_FOLIADA;

		insectSearcher = new FilterInsect(insectSearched);
	}

	@Test
	void testOneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(otherInsect);

		List<Sample> samplesFound = insectSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getActualResult();
		verify(sample1, times(1)).getActualResult();
		verify(sample2, times(1)).getActualResult();
	}

	@Test
	void testTwoSampleFound() {

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample2);

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		List<Sample> samplesFound = insectSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getActualResult();
		verify(sample1, times(1)).getActualResult();
		verify(sample2, times(1)).getActualResult();
	}

	@Test
	void testNoneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();

		when(sample.getActualResult()).thenReturn(otherInsect);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(otherInsect);

		List<Sample> samplesFound = insectSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		verify(sample, times(1)).getActualResult();
		verify(sample1, times(1)).getActualResult();
		verify(sample2, times(1)).getActualResult();
	}
}
