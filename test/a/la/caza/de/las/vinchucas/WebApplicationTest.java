package a.la.caza.de.las.vinchucas;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class WebApplicationTest {
	
	WebApplication webApplication;
	User user;
	Sample sample;

	@BeforeEach
	void setUp() {
		webApplication = new WebApplication();
		user = mock(User.class);
		sample = mock(Sample.class);
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
		Opinion opinion = mock(Opinion.class);
		when(user.getId()).thenReturn(1);
		when(opinion.getUser()).thenReturn(user);
		when(sample.getOpinionHistory()).thenReturn(List.of(opinion));
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertEquals(webApplication.getUserOpinions(user).size(), 1);
	}
	
	@Test 
	void testmanyOpinionMadeByUserBeforeAnyDays() {
		user = mock(User.class);
		sample = mock(Sample.class);
		Opinion opinion = mock(Opinion.class);
		when(user.getId()).thenReturn(1);
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(sample.getOpinionHistory()).thenReturn(List.of(opinion));
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		assertEquals(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30), 1);
	}
}
