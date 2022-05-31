package a.la.caza.de.las.vinchucas.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class LocationTest {
	
	private Location location;
	private Location location2;
	private Location location3;
	
	@BeforeEach 
	void setUp() {
		location  = new Location(1, 2); 
		location2 = new Location(3, 4);
		location3 = new Location(5, 6);
	}
	
	@Test
	void testCreateALocation(){
		assertEquals(location.getLatitude(), 1);
		assertEquals(location.getLenght(), 2);
	}
	@Test
	void testDistanceBetweenTwoLocations() {
	 assertEquals(400.74, location.distanceBetweenTwoLocations(location2));
	}
	
	@Test
	void testNearLocationsInDistance() {
		List<Location> locations = List.of(location2, location3); 
		assertEquals(location.getNearLocationsInDistance(locations, 500), List.of(location2));
	}
	
	@Test 
	void testNearSamplesInDistance() {
		Sample sample1 = mock(Sample.class); 
		Sample sample2 = mock(Sample.class);
		Sample sample3 = mock(Sample.class); 
		Sample sample4 = mock(Sample.class); 
		when(sample1.getLocation()).thenReturn(location);
		when(sample1.getActualResult()).thenReturn("Vinchuca");
		when(sample2.getLocation()).thenReturn(location2);
		when(sample2.getActualResult()).thenReturn("Vinchuca");
		when(sample3.getLocation()).thenReturn(location3);
		when(sample3.getActualResult()).thenReturn("Vinchuca");
		when(sample4.getLocation()).thenReturn(location2);
		when(sample4.getActualResult()).thenReturn("Chinche");
		List<Sample> samples = List.of(sample2, sample3); 
		assertEquals(location.getNearSamplesInDistance(sample1, samples, 500), List.of(sample2));
	}
}
