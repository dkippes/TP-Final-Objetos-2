package a.la.caza.de.las.vinchucas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.User;

public class WebApplicationTest {
	
	WebApplication webApplication;
	User user;
	Sample sample;

	@BeforeEach
	void setUp() {
		webApplication = WebApplication.createApp();
		user = mock(User.class);
		sample = mock(Sample.class);
	}
	
	@Test
	void testCreateSingleWebApp() {
		WebApplication webApplicationExpected = WebApplication.createApp();
		assertEquals(webApplicationExpected, webApplication);
	}
	
	@Test 
	void testAddUser() {
		webApplication.registerUser(user);
		assertEquals(webApplication.getRegisteredUsers().size(), 1);
	}
	@Test 
	void testAddSample() {
		webApplication.registerSample(sample);
		assertEquals(webApplication.getRegisteredSamples().size(), 1);
	}
	
}
