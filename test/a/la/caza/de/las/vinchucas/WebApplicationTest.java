package a.la.caza.de.las.vinchucas;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.User;

public class WebApplicationTest {
	private WebApplication webApplication;
	private User user;
	private Set<User> registedUsers;
	private Set<Sample> registedSamples;
	private Sample sample;
	private Opinion opinion;

	@BeforeEach
	void setUp() {
		registedUsers = spy(new HashSet<User>());
		registedSamples = spy(new HashSet<Sample>());
		webApplication = new WebApplication(registedUsers, registedSamples);
		user = mock(User.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void testAddUser() {
		webApplication.registerUser(user);
		assertTrue(webApplication.getRegisteredUsers().contains(user));
		verify(registedUsers).add(user);
	}

	@Test
	void testAddSample() {
		webApplication.registerSample(sample);
		assertTrue(webApplication.getRegisteredSamples().contains(sample));
		verify(registedSamples).add(sample);
	}

	@Test
	void testGetUserOpinions() {
		User user2 = mock(User.class);
		Opinion opinion2 = mock(Opinion.class);
		when(opinion.getUser()).thenReturn(user);
		when(opinion2.getUser()).thenReturn(user2);
		when(sample.getOpinionHistory()).thenReturn(List.of(opinion, opinion2));
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		webApplication.registerUser(user2);
		assertEquals(webApplication.getUserOpinions(user).size(), 1);
		assertTrue(webApplication.getRegisteredSamples().contains(sample));
		assertTrue(webApplication.getRegisteredUsers().contains(user));
		assertTrue(webApplication.getRegisteredUsers().contains(user2));
		verify(registedUsers, times(1)).add(user);
		verify(registedUsers, times(1)).add(user2);
		verify(registedSamples, times(1)).add(sample);
	}

	@Test
	void testManyOpinionMadeByUserBeforeAnyDays() {
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(sample.getOpinionHistory()).thenReturn(List.of(opinion));
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertEquals(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30), 1);
		assertTrue(webApplication.getRegisteredSamples().contains(sample));
		assertTrue(webApplication.getRegisteredUsers().contains(user));
		verify(registedUsers, times(1)).add(user);
		verify(registedSamples, times(1)).add(sample);
	}

	@Test
	void testGetUserSamples() {
		when(sample.getUser()).thenReturn(user);
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertTrue(webApplication.getUserSamples(user).size() >= 1);
		assertTrue(webApplication.getRegisteredSamples().contains(sample));
		assertTrue(webApplication.getRegisteredUsers().contains(user));
		verify(registedUsers, times(1)).add(user);
		verify(registedSamples, times(1)).add(sample);
	}

	@Test
	void testManySamplesSendByUserBeforeAnyDays() {
		when(sample.getUser()).thenReturn(user);
		when(sample.getCreationDate()).thenReturn(LocalDate.now());
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertTrue(webApplication.getRegisteredSamples().contains(sample));
		assertTrue(webApplication.getRegisteredUsers().contains(user));
		assertEquals(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30), 1);
	}
}
