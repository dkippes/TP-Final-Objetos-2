package a.la.caza.de.las.vinchucas.coverageArea;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Shape;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.CoverageAreaSystem;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

class CoverageAreaSystemTest {
	private CoverageAreaSystem system;
	private CoverageArea coverageArea1;
	private CoverageArea coverageArea2;
	private CoverageArea coverageArea3;
	

	@BeforeEach
	void setUp() throws Exception {
		system = new CoverageAreaSystem();
		coverageArea1 = mock(CoverageArea.class);
		coverageArea2 = mock(CoverageArea.class);
		coverageArea3 = mock(CoverageArea.class);
	}
	
	@Test
	void testGetRegisteredCoverageAreasDoesntHaveCoveragesAreas() {
		assertTrue(system.getRegistredCoverageAreas().isEmpty());
	}

	@Test
	void testRegisterACoverageArea() {
		
		system.registerCoverageArea(coverageArea1);
		assertFalse(system.getRegistredCoverageAreas().isEmpty());
	}
	
	
	@Test
	void testAreasOverlappedWithCoverageArea1() {
		
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
	void testAddedSampleBelongsToMoreThanOneCoverageArea() {
		
		system.registerCoverageArea(coverageArea1);
		system.registerCoverageArea(coverageArea2);
		
		Sample sample = mock(Sample.class);
		
		when(coverageArea1.belongsToCoverageArea(sample)).thenReturn(true);
		when(coverageArea2.belongsToCoverageArea(sample)).thenReturn(true);
		
		system.registerSampleInCoverageArea(sample);
		
		verify(coverageArea1, times(1)).addNewSample(sample);
		verify(coverageArea2, times(1)).addNewSample(sample);
		
		
	}
	

}
