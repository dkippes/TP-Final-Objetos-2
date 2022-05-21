package a.la.caza.de.las.vinchucas.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

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
		user = new User("Tomas", knowledge);
		location = mock(Location.class);
		photo = mock(Photo.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}
	
	@Test 
	void testGetNameUserAndId() {
		User newUser = new User("Diego", knowledge);
		assertEquals(newUser.getName(), "Diego");
		assertTrue(newUser.getId() > 0);
	}
	
	@Test 
	void testWhenUserIsCreatedHisKnowledgeIsBasic() {
		user = new User("Diego", new KnowledgeBasic());
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserCantBeCreatedHasExpertAndComeBackToBasicKnowledge() {
		user = new User("Diego", new KnowledgeExpert());
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserIsCreatedAsSpecialist() {
		user = new User("Diego", new KnowledgeSpecialist());
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserCanBeUpdatedToExpert() {
		user = new User("Diego", new KnowledgeBasic());
		user.setWebApplication(webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(30L);
		user.sendSample(sample);
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}

	@Test 
	void testUserIsDowngradedToBasic() {
		user = new User("Diego", new KnowledgeBasic());
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		user.sendSample(sample);
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test 
	void testUserRemindBasicInTheOpinionAlthoutIsUpgradeToExpert() throws Exception {
		user = new User("Diego", new KnowledgeBasic());
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
}
