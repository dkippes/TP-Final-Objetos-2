package a.la.caza.de.las.vinchucas.users;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVote;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeExpert;

import static org.mockito.Mockito.*;

public class UserTest {
	
	WebApplication webApplication;
	User user;
	Location location;
	Photo photo;
	Sample sample;
	Opinion opinion;
	
	@BeforeEach
	void setUp() {
		webApplication = mock(WebApplication.class);
		user = new User("Tomas", webApplication);
		location = mock(Location.class);
		photo = mock(Photo.class);
		sample = mock(Sample.class);
		opinion = mock(Opinion.class);
	}
	
	@Test 
	void testGetNameUserAndId() {
		User newUser = new User("Diego", webApplication);
		assertEquals(newUser.getName(), "Diego");
		assertTrue(newUser.getId() > 0);
	}
	
	@Test 
	void testWhenUserIsCreatedHisKnowledgeIsBasic() {
		assertTrue(user.hasBasicKnowledge());
		assertFalse(user.hasExpertKnowledge());
	}
	
	@Test 
	void testWhenUserIsExpert() {
		user.setKnowledge(new KnowledgeExpert());
		assertFalse(user.hasBasicKnowledge());
		assertTrue(user.hasExpertKnowledge());
	}

	@Test
	void testUserOpineInASample() throws UserAlreadyVote {
		user.opineSample(sample, opinion);
		verify(sample, times(1)).addOpinion(opinion);
	}
}
