package a.la.caza.de.las.vinchucas.coverageArea;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.CoverageAreaSystem;
import a.la.caza.de.las.vinchucas.samples.Sample;

class CoverageAreaSystemTest {
	private CoverageAreaSystem system;
	private CoverageArea coverageArea1;
	private CoverageArea coverageArea2;
	private CoverageArea coverageArea3;
	private Set<CoverageArea> registredCoverageAreas;
	private Sample sample;

	@BeforeEach
	void setUp() throws Exception {
		sample = mock(Sample.class);
		registredCoverageAreas = spy(new HashSet<>());
		system = new CoverageAreaSystem(registredCoverageAreas);
		coverageArea1 = mock(CoverageArea.class);
		coverageArea2 = mock(CoverageArea.class);
		coverageArea3 = mock(CoverageArea.class);
	}
	
	@Test
	void testGetRegisteredCoverageAreasDoesntHaveCoveragesAreas() {
		assertTrue(system.getRegistredCoverageAreas().isEmpty());
		verify(registredCoverageAreas, times(0)).add(coverageArea1);
	}

	@Test
	void testRegisterACoverageArea() {
		system.registerCoverageArea(coverageArea1);
		assertFalse(system.getRegistredCoverageAreas().isEmpty());
		assertTrue(system.getRegistredCoverageAreas().contains(coverageArea1));
		verify(registredCoverageAreas).add(coverageArea1);
	}
	
	@Test
	void testAreasOverlappedWithCoverageArea1() {
		system.registerCoverageArea(coverageArea1);
		system.registerCoverageArea(coverageArea2);
		system.registerCoverageArea(coverageArea3);
		
		when(coverageArea2.coverageAreasAreOverlapped(coverageArea1)).thenReturn(true);
		when(coverageArea3.coverageAreasAreOverlapped(coverageArea1)).thenReturn(true);
		
		Set<CoverageArea> coverageArea = new HashSet<>();
		coverageArea.add(coverageArea2);
		coverageArea.add(coverageArea3);
		
		assertEquals(coverageArea,system.getOverlappingAreas(coverageArea1));
		assertTrue(system.getRegistredCoverageAreas().contains(coverageArea1));
		assertTrue(system.getRegistredCoverageAreas().contains(coverageArea2));
		assertTrue(system.getRegistredCoverageAreas().contains(coverageArea3));
		verify(registredCoverageAreas).add(coverageArea1);
		verify(registredCoverageAreas).add(coverageArea2);
		verify(registredCoverageAreas).add(coverageArea3);
	}
	
	@Test
	void testAddedSampleBelongsToMoreThanOneCoverageArea() {
		system.registerCoverageArea(coverageArea1);
		system.registerCoverageArea(coverageArea2);
		
		when(coverageArea1.belongsToCoverageArea(sample)).thenReturn(true);
		when(coverageArea2.belongsToCoverageArea(sample)).thenReturn(true);
		
		system.registerSampleInCoverageArea(sample);
		
		assertTrue(system.getRegistredCoverageAreas().contains(coverageArea1));
		assertTrue(system.getRegistredCoverageAreas().contains(coverageArea2));
		verify(registredCoverageAreas).add(coverageArea1);
		verify(registredCoverageAreas).add(coverageArea2);
		verify(coverageArea1, times(1)).addNewSample(sample);
		verify(coverageArea2, times(1)).addNewSample(sample);
	}
}
