package a.la.caza.de.las.vinchucas.samples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.users.User;

public class SamplesTest {
	
	Sample sample;
	Location location;
	User user;
	Photo photo;
	Opinion opinion;

	@BeforeEach
	void setUp() {
		location = mock(Location.class);
		user = mock(User.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		sample = new Sample(location, photo, user);
	}

	@Test
	void testAddUserOpinionInSample() {
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		sample.addUserOpinion(opinion, user);
		assertEquals(sample.getOpinionHistory(), Map.of(user, opinion));
		assertEquals(sample.getCreationDate(), LocalDate.now());
		assertEquals(sample.getLastVotation(), LocalDate.now());
	}
}
