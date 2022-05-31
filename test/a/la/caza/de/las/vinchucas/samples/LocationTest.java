package a.la.caza.de.las.vinchucas.samples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationTest {
	
	private Location location;

	@BeforeEach 
	void setUp() {
		location = new Location(); 
	}
	
	@Test
	void testCreateALocation(){
		assertEquals(location.getClass(), Location.class);
	}
	
}
