package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

class FilterVerificationLevelTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;

	private FilterVerificationLevel verificationLevelSearcher;

	private Vote verLevelSearched;
	private Vote otherVerLevel;

	@BeforeEach
	void setUp() throws Exception {

		allSamples = new ArrayList<Sample>();
		sample = mock(Sample.class);
		sample1 = mock(Sample.class);
		sample2 = mock(Sample.class);
		allSamples.add(sample);
		allSamples.add(sample1);
		allSamples.add(sample2);

		verLevelSearched = Vote.VOTED;
		otherVerLevel = Vote.VERIFIED;

		verificationLevelSearcher = new FilterVerificationLevel(verLevelSearched);

	}

	@Test
	void testOneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = verificationLevelSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
	}

	@Test
	void testTwoSampleFound() {

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample2);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(verLevelSearched);

		List<Sample> samplesFound = verificationLevelSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
	}

	@Test
	void testNoneSampleFound() {
		List<Sample> samples = new ArrayList<Sample>();

		when(sample.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = verificationLevelSearcher.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
	}
}
