package a.la.caza.de.las.vinchucas.coverageArea;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.OrganizationObserver;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

public class CoverageAreaTest {
	private CoverageArea coverageArea;
	private Location epicenter;
	private Location location;
	private OrganizationObserver organizationObserver;
	private Sample sample;
	private Sample sample2;
	private Sample sample3;
	private Set<OrganizationObserver> organizationObservers;
	private Set<Sample> samples;

	@BeforeEach
	void setUp() {
		organizationObservers = spy(new HashSet<>());
		samples = spy(new HashSet<>());
		epicenter = mock(Location.class);
		location = mock(Location.class);
		organizationObserver = mock(OrganizationObserver.class);
		sample = mock(Sample.class);
		sample2 = mock(Sample.class);
		sample3 = mock(Sample.class);
		coverageArea = new CoverageArea("Zona1", epicenter, 400, samples, organizationObservers);
	}

	@Test
	void testCreateCoverageArea() {
		assertEquals(coverageArea.getName(), "Zona1");
		assertEquals(coverageArea.getEpicenter(), epicenter);
		assertEquals(coverageArea.getRadio(), 400);
		assertTrue(coverageArea.getOrganizationObservers().isEmpty());
		assertTrue(coverageArea.getSamples().isEmpty());
	}

	@Test
	void testAddOrganizationObserver() {
		coverageArea.addOrganizationObserver(organizationObserver);
		assertFalse(coverageArea.getOrganizationObservers().isEmpty());
		assertTrue(coverageArea.getOrganizationObservers().contains(organizationObserver));
		verify(organizationObservers).add(organizationObserver);
	}

	@Test
	void testDeleteOrganizationObserver() {
		coverageArea.addOrganizationObserver(organizationObserver);
		coverageArea.removeOrganizationObserver(organizationObserver);
		assertTrue(coverageArea.getOrganizationObservers().isEmpty());
		assertFalse(coverageArea.getOrganizationObservers().contains(organizationObserver));
		verify(organizationObservers).remove(organizationObserver);
	}

	@Test
	void testCantAddNewSampleBecauseTheSampleDoesntBelongsToTheArea() {
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(500d);
		coverageArea.addNewSample(sample);
		assertTrue(coverageArea.getSamples().isEmpty());
		assertFalse(coverageArea.getSamples().contains(sample));
		verify(organizationObserver, times(0)).uploadNewSample(coverageArea, sample);
		verify(samples, times(0)).add(sample);
	}

	@Test
	void testAddNewSampleWhenCoverageAreaDoesntHaveOrganizationObsevers() {
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(300d);
		coverageArea.addNewSample(sample);
		assertFalse(coverageArea.getSamples().isEmpty());
		assertTrue(coverageArea.getSamples().contains(sample));
		verify(organizationObserver, times(0)).uploadNewSample(coverageArea, sample);
		verify(samples).add(sample);
	}

	@Test
	void testAddNewSampleWhenCoverageAreaHaveOrganizationObsevers() {
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(300d);
		coverageArea.addOrganizationObserver(organizationObserver);
		coverageArea.addNewSample(sample);
		assertFalse(coverageArea.getSamples().isEmpty());
		assertFalse(coverageArea.getOrganizationObservers().isEmpty());
		assertTrue(coverageArea.getSamples().contains(sample));
		verify(organizationObservers).add(organizationObserver);
		verify(organizationObserver, times(1)).uploadNewSample(coverageArea, sample);
		verify(samples).add(sample);
	}

	@Test
	void testNotifyVerifySample() {
		coverageArea.addOrganizationObserver(organizationObserver);
		coverageArea.notifyVerifySample(sample);
		assertFalse(coverageArea.getOrganizationObservers().isEmpty());
		assertTrue(coverageArea.getOrganizationObservers().contains(organizationObserver));
		verify(organizationObserver, times(1)).validateSample(coverageArea, sample);
		verify(organizationObservers).add(organizationObserver);
		verify(organizationObserver, times(1)).validateSample(coverageArea, sample);
	}
	
	@Test
	void testAreaAndArea2AreOverlappingAreas() {
		CoverageArea coverageArea2 = new CoverageArea("Zona2", location, 200, samples, organizationObservers);
		
		when(epicenter.distanceBetweenTwoLocations(location)).thenReturn((double) 300);
		
		assertTrue(coverageArea.coverageAreasAreOverlapped(coverageArea2));
		verify(epicenter, times(1)).distanceBetweenTwoLocations(location);
	}
	
	@Test
	void testAreaAndArea2AreNotOverlappingAreas() {
		CoverageArea coverageArea2 = new CoverageArea("Zona2", location, 50, samples, organizationObservers);
		
		when(epicenter.distanceBetweenTwoLocations(location)).thenReturn((double) 800);
		
		assertFalse(coverageArea.coverageAreasAreOverlapped(coverageArea2));
		verify(epicenter, times(1)).distanceBetweenTwoLocations(location);
	}
	
	@Test
	void testSamplesInCoverageArea() {		
		List<Sample> samples = spy(new ArrayList<>());
		samples.add(sample);
		samples.add(sample2);
		samples.add(sample3);
		
		coverageArea.addNewSample(sample);
		coverageArea.addNewSample(sample2);
		
		List<Sample> expectedSamples = new ArrayList<Sample>();
		
		expectedSamples.add(sample);
		expectedSamples.add(sample2);
		
		assertEquals(coverageArea.samplesInCoverageArea(samples), expectedSamples);
		verify(samples).add(sample);
		verify(samples).add(sample2);
		verify(samples).add(sample3);
	}
}
