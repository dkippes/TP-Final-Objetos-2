package a.la.caza.de.las.vinchucas.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
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
		user = new User("Tomas", knowledge, webApplication);
		location = mock(Location.class);
		photo = mock(Photo.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}
	
	@Test 
	void testGetNameUserAndId() {
		User newUser = new User("Diego", knowledge, webApplication);
		assertEquals(newUser.getName(), "Diego");
		assertTrue(newUser.getId() > 0);
	}
	
	@Test 
	void testWhenUserIsCreatedHisKnowledgeIsBasic() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
		assertEquals(user.getKnowledge().getClass(), new KnowledgeBasic().getClass());
	}
	
	@Test 
	void testUserCantBeCreatedHasExpertAndComeBackToBasicKnowledge() {
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
	void testUserCanBeUpdatedToExpert() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		user.setWebApplication(webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		user.sendSample(sample);
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}

	@Test 
	void testUserIsDowngradedToBasic() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		user.sendSample(sample);
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserVoteBeforeSoItThrowAnException() throws Exception {
		doCallRealMethod().when(sample).addOpinion(opinion);
		assertThrows(Exception.class, () -> user.opineSample(sample, opinion));
	}
	
	@Test
	void testUserExpertVoteBeforeSoItThrowAnException() throws Exception {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		user.setWebApplication(webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		user.sendSample(sample);
		User uNew = new User("Test", new KnowledgeBasic(), webApplication);
		doCallRealMethod().when(sample).addOpinion(opinion);
		uNew.opineSample(sample, new Opinion(OpinionType.CHINCHE_FOLIADA, uNew));
		//when(user.hasExpertKnowledge()).thenReturn(false);
		
		assertThrows(Exception.class, () -> user.opineSample(sample, opinion));
	}
	
	@Test 
	void testUserRemindBasicInTheOpinionAlthoutIsUpgradeToExpert() throws Exception {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		Opinion o = new Opinion(OpinionType.IMAGE_UNCLEAR, user);
		sample = new Sample(location, photo, o);
		user.sendSample(sample);
		user.setKnowledge(new KnowledgeExpert());
		assertTrue(o.getUser().hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserOpineInASample() throws Exception {
		user.opineSample(sample, opinion);
		verify(sample, times(1)).addOpinion(opinion);
	}
	
	@Test
	void testUserCanBeUpdatedHisKnowledgeWhenEverItWantsIt() throws Exception {
		user.setKnowledge(new KnowledgeBasic());
		user.updateKnowledgeBaseOnCondition();
		assertEquals(user.getKnowledge().getClass(), new KnowledgeBasic().getClass());
	}
	
	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManyReviews() throws Exception {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(20L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManySamples() throws Exception {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManySamplesAndOpinions() throws Exception {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test
	void testUserExpertStillExpert() throws Exception {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		user.opineSample(sample, opinion);
		assertTrue(user.hasExpertKnowledge());
	}
}
