package a.la.caza.de.las.vinchucas.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.knowledge.Knowledge;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeExpert;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeSpecialist;

public class UserTest {
	private WebApplication webApplication;
	private User user;
	private Sample sample;
	private Opinion opinion;
	private Knowledge knowledge;

	@BeforeEach
	void setUp() {
		knowledge = mock(Knowledge.class);
		webApplication = mock(WebApplication.class);
		user = new User("Diego", knowledge, webApplication);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void testCreateAnUser() {
		assertEquals("Diego", user.getName());
		assertEquals(user.getWebApplication(), webApplication);
	}

	@Test
	void testWhenUserIsCreatedHisKnowledgeIsBasic() {
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
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
		user.getKnowledge();
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
		user.getKnowledge();
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}

	@Test
	void testUserCantBeDowngradedToBasicIfHeIsAnSpecialist() {
		user = new User("Diego", new KnowledgeSpecialist(), webApplication);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(10L);
		user.getKnowledge();
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
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManyReviews() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		user = new User("Diego", new KnowledgeExpert(), webApplication);
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(20L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
		verify(sample, times(1)).addOpinion(opinion);
		verify(knowledge, times(0)).checkStatusUser(user);
	}

	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManySamples() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
		verify(sample, times(1)).addOpinion(opinion);
		verify(knowledge, times(1)).checkStatusUser(user);
	}

	@Test
	void testUserExpertDowngradeToBasicBecauseHeDoesntHaveManySamplesAndOpinions() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(5L);
		user.opineSample(sample, opinion);
		assertFalse(user.hasExpertKnowledge());
		verify(sample, times(1)).addOpinion(opinion);
		verify(knowledge, times(1)).checkStatusUser(user);
	}

	@Test
	void testUserExpertStillExpert() throws UserValidationException {
		user.setKnowledge(new KnowledgeExpert());
		when(webApplication.manySamplesSendByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		when(webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30)).thenReturn(25L);
		user.opineSample(sample, opinion);
		assertTrue(user.hasExpertKnowledge());
		verify(sample, times(1)).addOpinion(opinion);
		verify(knowledge, times(1)).checkStatusUser(user);
	}
	
	@Test
	void testUserExpertCanVote() throws UserValidationException {
		Knowledge knowledge = spy(new KnowledgeExpert());
		user.setKnowledge(knowledge);
		user.canVote();
		verify(knowledge, times(1)).canVote(user);
	}
	
	@Test
	void testUserSpecialistCanVote() throws UserValidationException {
		Knowledge knowledge = spy(new KnowledgeSpecialist());
		user.setKnowledge(knowledge);
		user.canVote();
		verify(knowledge, times(1)).canVote(user);
	}
	
	@Test
	void testUserBasicCanNotVote() throws UserValidationException {
		Knowledge knowledge = spy(new KnowledgeBasic());
		user.setKnowledge(knowledge);
		assertThrows(UserValidationException.class, () -> user.canVote());
		verify(knowledge, times(1)).canVote(user);
	}
}
