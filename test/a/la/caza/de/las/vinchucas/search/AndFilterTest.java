package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

class AndFilterTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;

	private LocalDate dateSearched;
	private Vote verLevelSearched;
	private LocalDate otherDate;
	private Vote otherVerLevel;

	private FilterCreationDate dateFilter;
	private FilterVerificationLevel verLevelFilter;

	private AndFilter andFilter;

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
		verLevelSearched = Vote.VOTED;
		otherVerLevel = Vote.VERIFIED;

		dateFilter = new FilterCreationDate(dateSearched);
		verLevelFilter = new FilterVerificationLevel(verLevelSearched);

		andFilter = new AndFilter(dateFilter, verLevelFilter);

		operatorEqual = new OperatorEqual();

		dateFilter.setOperator(operatorEqual);

	}

	@Test
	void testSearchSamplesWithDateAndVerLevel() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		when(sample1.getCreationDate()).thenReturn(LocalDate.now());
		when(sample2.getCreationDate()).thenReturn(otherDate);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = andFilter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);

	}

}
