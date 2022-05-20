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
	Photo photo;
	Opinion opinion;
	User user;

	@BeforeEach
	void setUp() throws UserAlreadyVote {
		location = mock(Location.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		user = mock(User.class);
		sample = new Sample(location, photo, opinion);
	}
	
	@Test
	void testSampleIsVotedWhenIsCreated() throws UserAlreadyVote {
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(opinion.getUser()).thenReturn(user);
		when(user.getName()).thenReturn("Tomas");
		sample = new Sample(location, photo, opinion);
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertEquals(sample.getUser().getName(), "Tomas");
		assertEquals(sample.getCreationDate(), LocalDate.now());
		assertEquals(sample.getLastVotation(), LocalDate.now());
	}
}