package a.la.caza.de.las.vinchucas.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.exceptions.SampleCanNotBeOpine;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;

import static org.mockito.Mockito.*;

public class UserTest {
	
	User user;
	Location location;
	Photo photo;
	Sample sample;
	Opinion opinion;
	
	@BeforeEach
	void setUp() {
		user = new User("Tomas");
		location = mock(Location.class);
		photo = mock(Photo.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void testCreateANewUserWithoutSamplesUploaded() {
		assertEquals(user.getSamples().size(), 0);
	}
	
	@Test
	void testUserSendANewSample() {
		user.sendSample(location, photo);
		assertEquals(user.getSamples().size(), 1);
	}
	
	@Test
	void testUserOpineInHisExample() throws SampleCanNotBeOpine {
		when(sample.wasSendByTheUser(user)).thenReturn(true);
		assertThrows(SampleCanNotBeOpine.class, () -> user.opineSample(sample, opinion));
	}
	
	@Test
	void testUserOpineInAVerifySample() throws SampleCanNotBeOpine {
		when(sample.isVerify()).thenReturn(true);
		assertThrows(SampleCanNotBeOpine.class, () -> user.opineSample(sample, opinion));
	}
	
	@Test
	void testUserOpineInASample() throws SampleCanNotBeOpine {
		when(sample.wasSendByTheUser(user)).thenReturn(false);
		when(sample.isVerify()).thenReturn(false);
		sample.addOpinionInPhoto(opinion);
		verify(sample, times(1)).addOpinionInPhoto(opinion);
	}
}
