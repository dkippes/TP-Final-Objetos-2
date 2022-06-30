package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

class CombinedFiltersTest {
	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;

	private LocalDate dateSearched;
	private LocalDate minorDate;
	private LocalDate majorDate;
	
	private Vote verLevelSearched;
	private Vote otherVerLevel;
	
	private GenericOpinionType insectSearched;
	private GenericOpinionType otherInsect;
	
	private FilterCreationDate creationDateFilter;
	private FilterVerificationLevel verLevelFilter;
	private FilterLastVoteDate lastVotedDateFilter;
	private FilterInsect insectFilter;


	private AndFilter andFilter;
	private AndFilter andFilter2;
	private OrFilter orFilter;
	private OrFilter orFilter2;

	private OperatorMajor operatorMajor;
	private OperatorMinor operatorMinor;


	@BeforeEach
	void setUp() throws Exception {
		
		sample = mock(Sample.class);
		sample1 = mock(Sample.class);
		sample2 = mock(Sample.class);
		allSamples = new ArrayList<Sample>();
		allSamples.add(sample);
		allSamples.add(sample1);
		allSamples.add(sample2);

		dateSearched = LocalDate.now();
		majorDate = LocalDate.now().plusDays(10);
		minorDate = LocalDate.now().minusDays(10);
		
		insectSearched = OpinionType.VINCHUCA_GUASAYANA;
		otherInsect = OpinionType.CHINCHE_FOLIADA;

		verLevelSearched = Vote.VOTED;
		otherVerLevel = Vote.VERIFIED;

		lastVotedDateFilter = new FilterLastVoteDate(dateSearched);
		insectFilter = new FilterInsect(insectSearched);
		creationDateFilter = new FilterCreationDate(dateSearched);
		verLevelFilter = new FilterVerificationLevel(verLevelSearched);

		operatorMajor = new OperatorMajor();
		operatorMinor = new OperatorMinor();
		
	}
	

	@Test
	void testOrAnd() {
		orFilter = new OrFilter(lastVotedDateFilter, insectFilter);
		andFilter = new AndFilter(verLevelFilter, orFilter);
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(majorDate);

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = andFilter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(0)).getLastVotation();
		verify(sample2, times(0)).getLastVotation();
		
		verify(sample, times(1)).getActualResult();
		verify(sample1, times(0)).getActualResult();
		verify(sample2, times(0)).getActualResult();
		
		verify(sample, times(1)).getLevelVerification();
		verify(sample1, times(1)).getLevelVerification();
		verify(sample2, times(1)).getLevelVerification();
	}
	
	@Test
	void testAndAnd() {
		
		andFilter = new AndFilter(verLevelFilter, creationDateFilter);
		andFilter2 = new AndFilter (andFilter, insectFilter);
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		when(sample1.getCreationDate()).thenReturn(LocalDate.now());
		when(sample2.getCreationDate()).thenReturn(majorDate);

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = andFilter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		
		verify(sample, times(0)).getLastVotation();
		verify(sample1, times(0)).getLastVotation();
		verify(sample2, times(0)).getLastVotation();
		
		verify(sample, times(0)).getActualResult();
		verify(sample1, times(0)).getActualResult();
		verify(sample2, times(0)).getActualResult();
		
		verify(sample, times(1)).getLevelVerification();
		verify(sample1, times(1)).getLevelVerification();
		verify(sample2, times(1)).getLevelVerification();
	}
	
	@Test
	void testOrOr() {
		
		orFilter = new OrFilter(lastVotedDateFilter, creationDateFilter);
		orFilter2 = new OrFilter (orFilter, insectFilter);
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample1);
		samples.add(sample2);
		
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		when(sample1.getCreationDate()).thenReturn(majorDate);
		when(sample2.getCreationDate()).thenReturn(LocalDate.now());
		

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(majorDate);

		List<Sample> samplesFound = orFilter.searchSamples(allSamples);

		assertTrue(samples.containsAll(samplesFound));
		
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
		
		verify(sample, times(0)).getActualResult();
		verify(sample1, times(0)).getActualResult();
		verify(sample2, times(0)).getActualResult();
		
		verify(sample, times(0)).getLevelVerification();
		verify(sample1, times(0)).getLevelVerification();
		verify(sample2, times(0)).getLevelVerification();
	}
	
	@Test
	void testAndOr() {

		andFilter = new AndFilter(verLevelFilter, lastVotedDateFilter);
		orFilter = new OrFilter(andFilter, insectFilter);
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample1);
		samples.add(sample2);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(majorDate);

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = orFilter.searchSamples(allSamples);

		assertTrue(samples.containsAll(samplesFound));
		
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(0)).getLastVotation();
		verify(sample2, times(0)).getLastVotation();
		
		verify(sample, times(1)).getActualResult();
		verify(sample1, times(1)).getActualResult();
		verify(sample2, times(1)).getActualResult();
		
		verify(sample, times(1)).getLevelVerification();
		verify(sample1, times(1)).getLevelVerification();
		verify(sample2, times(1)).getLevelVerification();
	}
	
	@Test
	void testAllFiltersWithAnd() {

		andFilter = new AndFilter(verLevelFilter, lastVotedDateFilter);
		andFilter2 = new AndFilter(creationDateFilter, insectFilter);
		AndFilter andFilter3 = new AndFilter(andFilter, andFilter2);
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(majorDate);
		
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		when(sample1.getCreationDate()).thenReturn(majorDate);
		when(sample2.getCreationDate()).thenReturn(LocalDate.now());
		

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = andFilter3.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(0)).getLastVotation();
		verify(sample2, times(0)).getLastVotation();
		
		verify(sample, times(1)).getActualResult();
		verify(sample1, times(0)).getActualResult();
		verify(sample2, times(0)).getActualResult();
		
		verify(sample, times(1)).getLevelVerification();
		verify(sample1, times(1)).getLevelVerification();
		verify(sample2, times(1)).getLevelVerification();
	}
	
	@Test
	void testAllFiltersWithOr() {

		orFilter = new OrFilter(verLevelFilter, lastVotedDateFilter);
		orFilter2 = new OrFilter(creationDateFilter, insectFilter);
		OrFilter orFilter3 = new OrFilter(orFilter, orFilter2);
		
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		samples.add(sample1);
		samples.add(sample2);

		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(majorDate);
		
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		when(sample1.getCreationDate()).thenReturn(majorDate);
		when(sample2.getCreationDate()).thenReturn(LocalDate.now());
		

		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);

		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);

		List<Sample> samplesFound = orFilter3.searchSamples(allSamples);

		assertTrue(samples.containsAll(samplesFound));
		
		verify(sample, times(1)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
		
		verify(sample, times(1)).getActualResult();
		verify(sample1, times(1)).getActualResult();
		verify(sample2, times(1)).getActualResult();
		
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
		
		verify(sample, times(1)).getLevelVerification();
		verify(sample1, times(1)).getLevelVerification();
		verify(sample2, times(1)).getLevelVerification();
	}
	
	@Test
	void testMajorMinorDateWithAnd() {

		creationDateFilter.setOperator(operatorMinor);
		lastVotedDateFilter.setOperator(operatorMajor);
		
		andFilter = new AndFilter(creationDateFilter, lastVotedDateFilter);

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample2);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(minorDate);
		when(sample2.getCreationDate()).thenReturn(minorDate);
		
		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(majorDate);

		List<Sample> samplesFound = andFilter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		
		verify(sample, times(1)).getCreationDate();
		verify(sample1, times(1)).getCreationDate();
		verify(sample2, times(1)).getCreationDate();
		
		verify(sample, times(0)).getLastVotation();
		verify(sample1, times(1)).getLastVotation();
		verify(sample2, times(1)).getLastVotation();
	}
	
	@Test
	void testMajorMinorDateWithOr() {
		FilterCreationDate creationDateFilter2 = new FilterCreationDate(dateSearched);
		
		creationDateFilter.setOperator(operatorMinor);
		creationDateFilter2.setOperator(operatorMajor);
		
		orFilter = new OrFilter(creationDateFilter, creationDateFilter2);

		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample2);

		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(minorDate);
		when(sample2.getCreationDate()).thenReturn(minorDate);
		
		when(sample.getCreationDate()).thenReturn(dateSearched);
		when(sample1.getCreationDate()).thenReturn(dateSearched);
		when(sample2.getCreationDate()).thenReturn(majorDate);

		List<Sample> samplesFound = orFilter.searchSamples(allSamples);

		assertEquals(samples, samplesFound);
		
		verify(sample, times(2)).getCreationDate();
		verify(sample1, times(2)).getCreationDate();
		verify(sample2, times(2)).getCreationDate();
	}
	
}
