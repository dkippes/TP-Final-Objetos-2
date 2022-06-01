package a.la.caza.de.las.vinchucas;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.User;

public class WebApplicationTest {
	private WebApplication webApplication;
	private User user;
	private Sample sample;
	private Opinion opinion;

	@BeforeEach
	void setUp() {
		webApplication = new WebApplication();
		user = mock(User.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void testAddUser() {
		webApplication.registerUser(user);
		assertTrue(webApplication.getRegisteredUsers().size() > 0);
	}

	@Test
	void testAddSample() {
		webApplication.registerSample(sample);
		assertTrue(webApplication.getRegisteredSamples().size() > 0);
	}

	@Test
	void testGetUserOpinions() {
		User user2 = mock(User.class);
		Opinion opinion2 = mock(Opinion.class);
		when(user.getId()).thenReturn(1);
		when(user2.getId()).thenReturn(2);
		when(opinion.getUser()).thenReturn(user);
		when(opinion2.getUser()).thenReturn(user2);
		when(sample.getOpinionHistory()).thenReturn(List.of(opinion, opinion2));
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		webApplication.registerUser(user2);
		assertEquals(webApplication.getUserOpinions(user).size(), 1);
	}

	@Test
	void testManyOpinionMadeByUserBeforeAnyDays() {
		when(user.getId()).thenReturn(1);
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(sample.getOpinionHistory()).thenReturn(List.of(opinion));
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertEquals(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30), 1);
	}

	@Test
	void testGetUserSamples() {
		when(user.getId()).thenReturn(1);
		when(sample.getUser()).thenReturn(user);
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertEquals(webApplication.getUserSamples(user).size(), 1);
	}

	@Test
	void testManySamplesSendByUserBeforeAnyDays() {
		when(user.getId()).thenReturn(1);
		when(sample.getUser()).thenReturn(user);
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertEquals(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30), 1);
	}
}
