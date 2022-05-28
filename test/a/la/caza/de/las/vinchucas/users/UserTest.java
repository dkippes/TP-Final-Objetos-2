package a.la.caza.de.las.vinchucas.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.knowledge.Knowledge;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeExpert;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeSpecialist;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTest {
	
	WebApplication webApplication;
	User user;
	Location location;
	Photo photo;
	Sample sample;
	Opinion opinion;
	Knowledge knowledge;
	
	@BeforeEach
	void setUp() {
		knowledge = mock(Knowledge.class);
		webApplication = mock(WebApplication.class);
		user = new User("Diego", knowledge, webApplication);
		location = mock(Location.class);
		photo = mock(Photo.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}
	
	@Test 
	void testCreateAnUser() {
		assertEquals("Diego", user.getName());
		assertTrue(user.getId() > 0);
		assertEquals(user.getKnowledge(), knowledge);
		assertEquals(user.getWebApplication(), webApplication);
	}
	
	@Test 
	void testWhenUserIsCreatedHisKnowledgeIsBasic() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
		assertEquals(user.getKnowledge().getClass(), new KnowledgeBasic().getClass());
	}
	
	@Test 
	void testUserCantBeCreatedHasExpertIsDoesntMeetTheRequirementsAndSetWithBasicKnowledge() {
		user = new User("Diego", new KnowledgeExpert(), webApplication);
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserIsCreatedAsSpecialist() {
		user = new User("Diego", new KnowledgeSpecialist(), webApplication);
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserIsUpdatedToExpert() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		user.updateKnowledgeBaseOnCondition();
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserIsUpdatedWhenHeSendASampleToExpert() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		user.sendSample(sample);
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}

	@Test 
	void testUserIsDowngradedToBasic() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		user.updateKnowledgeBaseOnCondition();
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserCantBeDowngradedToBasicIfHeIsAnSpecialist() {
		user = new User("Diego", new KnowledgeSpecialist(), webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		user.updateKnowledgeBaseOnCondition();
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserAddOpinion() throws UserValidationException {
		user.opineSample(sample, opinion);
		verify(sample, times(1)).addOpinion(opinion);
		verify(knowledge, times(2)).checkStatusUser(user);
	}
	
	@Test
	void testUserSetANewWebApplication() throws UserValidationException {
		WebApplication newWebApp = mock(WebApplication.class);
		user.setWebApplication(newWebApp);
		assertEquals(newWebApp, user.getWebApplication());
	}
	
	@Test
	void testUserCanBeCloneSoItDoesntChangeHerKnowledgeInTheOpinion() throws CloneNotSupportedException {
		User userCloned = user.clone();
		assertEquals(userCloned.getId(), user.getId());
	}
	
	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManyReviews() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(20L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManySamples() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManySamplesAndOpinions() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserExpertStillExpert() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		user.opineSample(sample, opinion);
		assertTrue(user.hasExpertKnowledge());
	}
}
