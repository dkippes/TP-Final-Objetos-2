package a.la.caza.de.las.vinchucas.samples;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVote;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class SamplesTest {
	
	Sample sample;
	Location location;
	User user;
	Photo photo;
	Opinion opinion;

	@BeforeEach
	void setUp() throws UserAlreadyVote {
		location = mock(Location.class);
		user = mock(User.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		sample = new Sample(location, photo, user, opinion);
	}

	
	@Test
	void testAddUserOpinionInSample() {
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		sample.addUserOpinion(opinion, user);
		assertEquals(sample.getOpinionHistory(), Map.of(user, opinion));
		assertEquals(sample.getCreationDate(), LocalDate.now());
		assertEquals(sample.getLastVotation(), LocalDate.now());
	}
	
	@Test
	void testSampleIsVotedWhenIsCreated() {
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
	}
	
	@Test
	void testSampleIsVerify() {
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
	}
	
	@Test
	void testUserAlreadyVote() throws UserAlreadyVote {
		User pablo = mock(User.class);
		sample.addOpinion(opinion, pablo);
		when(pablo.getName()).thenReturn("Pablo");
		
		UserAlreadyVote thrown = assertThrows(
				UserAlreadyVote.class,
		           () -> sample.addOpinion(opinion, pablo),
		           "The user Pablo already vote in this sample"
		    );
		assertTrue(thrown.getMessage().contains("Pablo"));
	}
	
	@Test
	void testUserThatSendTheSampleAlreadyVote() throws UserAlreadyVote {
		when(user.getName()).thenReturn("Diego");
		UserAlreadyVote thrown = assertThrows(
				UserAlreadyVote.class,
		           () -> sample.addOpinion(opinion, user),
		           "The user Diego already vote in this sample"
		    );
		assertTrue(thrown.getMessage().contains("Diego"));
	}
}
