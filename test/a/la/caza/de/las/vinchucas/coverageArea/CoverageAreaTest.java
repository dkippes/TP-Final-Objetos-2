package a.la.caza.de.las.vinchucas.coverageArea;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.NgoObserver;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;


public class CoverageAreaTest {

	private CoverageArea coverageArea;
	private Location epicenter;
	private NgoObserver ngoObserver;   
	private Sample sample;
	
	@BeforeEach
	void setUp() {
		epicenter= mock(Location.class); 
		ngoObserver= mock(NgoObserver.class);
		sample= mock(Sample.class);
		coverageArea = new CoverageArea("Zona1", epicenter, 400);
	}
	
	@Test
	void testCreateCoverageArea() {
		assertEquals(coverageArea.getName(), "Zona1");
		assertEquals(coverageArea.getEpicenter(), epicenter);
	    assertEquals(coverageArea.getRadio(), 400);
	    assertTrue(coverageArea.getNgoObservers().isEmpty());
	    assertTrue(coverageArea.getSamples().isEmpty());
	}
	
	@Test 
	void testAddNgoObserver() {
		coverageArea.addNgoObserver(ngoObserver);
		assertFalse(coverageArea.getNgoObservers().isEmpty());
	}
	
	@Test 
	void testDeleteNgoObserver() {
		coverageArea.addNgoObserver(ngoObserver);
		coverageArea.removeNgoObserver(ngoObserver);
		assertTrue(coverageArea.getNgoObservers().isEmpty());
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
	void testAddNewSampleWhenCoverageAreaDoesntHaveNgoObsevers() {
		Location location = mock(Location.class);
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(300d);
		coverageArea.addNewSample(sample);
		assertFalse(coverageArea.getSamples().isEmpty());
	}
	
	@Test
	void testAddNewSampleWhenCoverageAreaHaveNgoObsevers() {
		Location location = mock(Location.class);
		when(sample.getLocation()).thenReturn(location);
		when(epicenter.distanceBetweenTwoLocations(sample.getLocation())).thenReturn(300d);
		coverageArea.addNgoObserver(ngoObserver);
		coverageArea.addNewSample(sample);
		assertFalse(coverageArea.getSamples().isEmpty());
		assertFalse(coverageArea.getNgoObservers().isEmpty());
	}
	
	@Test
	void testNotifyVerifySample() {
		coverageArea.addNgoObserver(ngoObserver);
		coverageArea.addVerifySample(sample);
		assertFalse(coverageArea.getNgoObservers().isEmpty());
	}
}
	
