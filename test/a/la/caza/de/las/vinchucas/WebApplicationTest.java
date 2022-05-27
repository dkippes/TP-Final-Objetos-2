package a.la.caza.de.las.vinchucas;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		Opinion o = new Opinion(OpinionType.CHINCHE_FOLIADA, new User("Diego", new KnowledgeBasic(), webApplication));
		doCallRealMethod().when(sample).getOpinionHistory().add(o);
		doCallRealMethod().when(sample).getOpinionHistory();
		User user = mock(User.class);
		assertEquals(webApplication.getUserOpinions(user).size(), 1);
	}
	
	@Test 
	void testmanyOpinionMadeByUserBeforeAnyDays() {
		webApplication.registerSample(sample);
		webApplication.registerUser(user);
		Opinion opinion = new Opinion(OpinionType.CHINCHE_FOLIADA, user);
		when(sample.getOpinionHistory()).thenReturn(List.of(opinion));
		//doCallRealMethod().when(sample).getOpinionHistory().add(o);
		assertEquals(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30), 1);
	}
}
