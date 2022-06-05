package a.la.caza.de.las.vinchucas.coverageArea;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Shape;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.CoverageAreaSystem;
import a.la.caza.de.las.vinchucas.location.Location;

class CoverageAreaSystemTest {
	private CoverageAreaSystem system;

	@BeforeEach
	void setUp() throws Exception {
		system = new CoverageAreaSystem();
	}
	
	@Test
	void testGetRegisteredCoverageAreasDoesntHaveCoveragesAreas() {
		assertTrue(system.getRegistredCoverageAreas().isEmpty());
	}

	@Test
	void testRegisterACoverageArea() {
		CoverageArea coverageArea = mock(CoverageArea.class);
		system.registerCoverageArea(coverageArea);
		assertFalse(system.getRegistredCoverageAreas().isEmpty());
	}
	
	
	@Test
	void testAreasOverlappedWithCoverageArea1() {
		CoverageArea coverageArea1 = mock(CoverageArea.class);
		CoverageArea coverageArea2 = mock(CoverageArea.class);
		CoverageArea coverageArea3 = mock(CoverageArea.class);
		
		system.registerCoverageArea(coverageArea1);
		system.registerCoverageArea(coverageArea2);
		system.registerCoverageArea(coverageArea3);
		
		when(coverageArea2.coverageAreasAreOverlapped(coverageArea1)).thenReturn(true);
		when(coverageArea3.coverageAreasAreOverlapped(coverageArea1)).thenReturn(true);
		
		Set<CoverageArea> ca = new HashSet<>();
		ca.add(coverageArea2);
		ca.add(coverageArea3);
		
		assertEquals(ca,system.getOverlappingAreas(coverageArea1));
	}
	
	@Test
	void testSamplesInCoverageArea() {
		//assertEquals(coverageArea.samplesInCoverageArea(), null);
	}
	

}
