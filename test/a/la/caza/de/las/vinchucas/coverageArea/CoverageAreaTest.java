package a.la.caza.de.las.vinchucas.coverageArea;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.location.Location;

public class CoverageAreaTest {

	private CoverageArea coverageArea;
	private Location location;
	
	@BeforeEach
	void setUp() {
		location= mock(Location.class); 
		coverageArea = new CoverageArea("Zona1", location, 400);
	}
	
	@Test
	void testCreateCoverageArea() {
		assertEquals(coverageArea.getName(), "Zona1");
		assertEquals(coverageArea.getEpicenter(), location);
	    assertEquals(coverageArea.getRadio(), 400);
	    assertTrue(coverageArea.getNgoObservers().isEmpty());
	    assertTrue(coverageArea.getSamples().isEmpty());
	}
	
}
