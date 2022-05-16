package a.la.caza.de.las.vinchucas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebApplicationTest {
	
	WebApplication webApplication;

	@BeforeEach
	void setUp() {
		webApplication = WebApplication.createApp();
	}
	
	@Test
	void testCreateSingleWebApp() {
		WebApplication webApplicationExpected = WebApplication.createApp();
		assertEquals(webApplicationExpected, webApplication);
	}
	
}
