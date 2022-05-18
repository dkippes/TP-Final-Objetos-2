package a.la.caza.de.las.vinchucas.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.SampleCanNotBeOpine;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;

import static org.mockito.Mockito.*;

public class UserTest {
	
	WebApplication webApplication;
	User user;
	Location location;
	Photo photo;
	Sample sample;
	Opinion opinion;
	
	@BeforeEach
	void setUp() {
		webApplication = mock(WebApplication.class);
		user = new User("Tomas", webApplication);
		location = mock(Location.class);
		photo = mock(Photo.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void testCreateANewUserWithoutSamplesUploaded() {
		assertEquals(user.getSamplesSend().size(), 0);
	}
	
	@Test
	void testUserSendANewSample() {
		user.sendSample(new Sample(location, photo, user));
		assertEquals(user.getSamplesSend().size(), 1);
	}

	@Test
	void testUserOpineInASample() throws SampleCanNotBeOpine {
		user.opineSample(sample, opinion);
		verify(sample, times(1)).addUserOpinion(opinion, user);
	}
}
