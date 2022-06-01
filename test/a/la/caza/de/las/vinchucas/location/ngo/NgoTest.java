package a.la.caza.de.las.vinchucas.location.ngo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;

public class NgoTest {
	
	private Ngo ngoHealth, ngoEducation, ngoCultural, ngoAssistance;
	private Location location;

	@BeforeEach 
	void setUp() {
		location = mock(Location.class);
		ngoHealth = new Ngo(location, NgoType.HEALTH, 4);
		ngoEducation = new Ngo(location, NgoType.EDUCATION, 4);
		ngoCultural = new Ngo(location, NgoType.CULTURAL, 4);
		ngoAssistance = new Ngo(location, NgoType.ASSISTANCE, 4);
	}
	
	@Test
	void testNgo() {
		assertAll(() -> assertEquals(ngoHealth.getLocation(), location),
				() -> assertEquals(ngoHealth.getWorkingPeople(), 4),
				() -> assertEquals(ngoHealth.getNgoType(), NgoType.HEALTH),
				() -> assertEquals(ngoEducation.getNgoType(), NgoType.EDUCATION),
				() -> assertEquals(ngoCultural.getNgoType(), NgoType.CULTURAL),
				() -> assertEquals(ngoAssistance.getNgoType(), NgoType.ASSISTANCE),
				() -> assertEquals(ngoAssistance.getNgoTypeString(), "Assistance")
		);
	}
}
