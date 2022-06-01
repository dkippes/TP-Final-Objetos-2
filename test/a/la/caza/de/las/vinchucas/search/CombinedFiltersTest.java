package a.la.caza.de.las.vinchucas.search;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

class CombinedFiltersTest {

	private Sample sample;
	private Sample sample1;
	private Sample sample2;
	private List<Sample> allSamples;
	
	private LocalDate creationDateSearched;
	private Vote verLevelSearched;
	private LocalDate otherCreationDate;
	private Vote otherVerLevel;
	private LocalDate lastVotedateSearched;
	private LocalDate otherLastVoteDate;
	private String insectSearched;
	private String otherInsect;
	
	private FilterCreationDate dateFilter;
	private FilterVerificationLevel verLevelFilter;
	private FilterLastVoteDate lastVotedateFilter;
	private FilterInsect insectFilter;
	
	
	private AndFilter andFilter;
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
		
		creationDateSearched = LocalDate.now();
		otherCreationDate = LocalDate.now().plusDays(10);
		insectSearched = OpinionType.VINCHUCA_GUASAYANA.getOpinionType();
		otherInsect = OpinionType.CHINCHE_FOLIADA.getOpinionType();
		lastVotedateSearched = LocalDate.now();
		otherLastVoteDate = LocalDate.now().plusDays(10);
		verLevelSearched = Vote.VOTED;
		otherVerLevel = Vote.VERIFIED;
		
		lastVotedateFilter = new FilterLastVoteDate(creationDateSearched);
		insectFilter = new FilterInsect(insectSearched);
		dateFilter = new FilterCreationDate(lastVotedateSearched);
		verLevelFilter = new FilterVerificationLevel(verLevelSearched);
		
		orFilter= new OrFilter();
		andFilter = new AndFilter();
		
		operatorEqual = new OperatorEqual();
		dateFilter.setOperator(operatorEqual);
		lastVotedateFilter.setOperator(operatorEqual);
		
	}

	@Test
	void testOrAnd() {
		List<Sample> samples = new ArrayList<Sample>();
		samples.add(sample);
		
		when(sample.getLastVotation()).thenReturn(LocalDate.now());
		when(sample1.getLastVotation()).thenReturn(LocalDate.now());
		when(sample2.getLastVotation()).thenReturn(otherLastVoteDate);
		
		when(sample.getActualResult()).thenReturn(insectSearched);
		when(sample1.getActualResult()).thenReturn(otherInsect);
		when(sample2.getActualResult()).thenReturn(insectSearched);
		
		when(sample.getLevelVerification()).thenReturn(verLevelSearched);
		when(sample1.getLevelVerification()).thenReturn(otherVerLevel);
		when(sample2.getLevelVerification()).thenReturn(otherVerLevel);
		
		orFilter.setFilters(lastVotedateFilter, insectFilter);
		andFilter.setFilters(verLevelFilter, orFilter);
		
		List<Sample> samplesFound = andFilter.searchSamples(allSamples) ;
	
		assertEquals(samples,samplesFound);
	}

}
