package a.la.caza.de.las.vinchucas.coverageArea;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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
	private OrganizationObserver organizationObserver;
	private Sample sample;

	@BeforeEach
	void setUp() {
		epicenter = mock(Location.class);
		organizationObserver = mock(OrganizationObserver.class);
		sample = mock(Sample.class);
		coverageArea = new CoverageArea("Zona1", epicenter, 400);
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
	}

	@Test
	void testDeleteOrganizationObserver() {
		coverageArea.addOrganizationObserver(organizationObserver);
		coverageArea.removeOrganizationObserver(organizationObserver);
		assertTrue(coverageArea.getOrganizationObservers().isEmpty());
	}

	@Test
	void testCantAddNewSampleBecauseTheSampleDoesntBelongsToTheArea() {
		Location location = mock(Location.class);
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(500d);
		coverageArea.addNewSample(sample);
		assertTrue(coverageArea.getSamples().isEmpty());
	}

	@Test
	void testAddNewSampleWhenCoverageAreaDoesntHaveOrganizationObsevers() {
		Location location = mock(Location.class);
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(300d);
		coverageArea.addNewSample(sample);
		assertFalse(coverageArea.getSamples().isEmpty());
	}

	@Test
	void testAddNewSampleWhenCoverageAreaHaveOrganizationObsevers() {
		Location location = mock(Location.class);
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(300d);
		coverageArea.addOrganizationObserver(organizationObserver);
		coverageArea.addNewSample(sample);
		assertFalse(coverageArea.getSamples().isEmpty());
		assertFalse(coverageArea.getOrganizationObservers().isEmpty());
	}

	@Test
	void testNotifyVerifySample() {
		coverageArea.addOrganizationObserver(organizationObserver);
		coverageArea.addVerifySample(sample);
		assertFalse(coverageArea.getOrganizationObservers().isEmpty());
	}
	
	@Test
	void testAreaAndArea2AreOverlappingAreas() {
		Location location2 = mock(Location.class);
		CoverageArea coverageArea2 = new CoverageArea("Zona2", location2, 200);
		
		when(epicenter.distanceBetweenTwoLocations(location2)).thenReturn((double) 300);
		
		assertTrue (coverageArea.coverageAreasAreOverlapped(coverageArea2));
	}
	
	@Test
	void testAreaAndArea2AreNotOverlappingAreas() {
		Location location2 = mock(Location.class);
		CoverageArea coverageArea2 = new CoverageArea("Zona2", location2, 50);
		
		when(epicenter.distanceBetweenTwoLocations(location2)).thenReturn((double) 800);
		
		assertFalse (coverageArea.coverageAreasAreOverlapped(coverageArea2));
	}
	
	@Test
	void testSamplesInCoverageArea() {
		
		Sample sample2 = mock(Sample.class);
		Sample sample3 = mock(Sample.class);
		
		List<Sample> samples = new ArrayList<Sample>();
		
		samples.add(sample);
		samples.add(sample2);
		samples.add(sample3);
		
		coverageArea.addNewSample(sample);
		coverageArea.addNewSample(sample2);
		
		List<Sample> expectedSamples = new ArrayList<Sample>();
		
		expectedSamples.add(sample);
		expectedSamples.add(sample2);
		
		assertEquals(coverageArea.samplesInCoverageArea(samples), expectedSamples);
	}

	
}
