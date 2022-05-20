package a.la.caza.de.las.vinchucas.samples;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVote;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class SamplesTest {

	Sample sample;
	Location location;
	Photo photo;
	Opinion opinion;
	User user;

	@BeforeEach
	void setUp() throws UserAlreadyVote {
		location = mock(Location.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		user = mock(User.class);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn("Vinchuca Guasayana");
		when(user.getName()).thenReturn("Tomas");
		sample = new Sample(location, photo, opinion);
	}

	@Test
	void testSampleIsVotedWhenIsCreated() throws UserAlreadyVote {
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertEquals(sample.getUser().getName(), "Tomas");
		assertEquals(sample.getCreationDate(), LocalDate.now());
		assertEquals(sample.getLastVotation(), LocalDate.now());
	}

	@Test
	void testAddOpinionWhenTheUserHasBasicKnowledge() throws UserAlreadyVote {
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2020, 10, 5));
		when(opinion.getUser()).thenReturn(user);
		when(user.getName()).thenReturn("Diego");
		sample.addOpinion(opinion);
		
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Diego");
		assertEquals(sample.getLastVotation(), LocalDate.of(2020, 10, 5));
	}

	@Test
	void testAddOpinionWhenTheUserHasExpertKnowledge() throws UserAlreadyVote {
		addOpinionDiego(opinion, sample);
		
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 1);
		assertEquals(sample.getOpinionHistory().get(0).getUser().getName(), "Diego");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testSampleIsVerifyWhen2ExpertVoted() throws UserAlreadyVote {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testIsVerifyTheUserWhoSendTheSampleAndOtherExpertVoted() throws UserAlreadyVote {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}
	
	@Test
	void testIsVerifyAlthoughtAnotherUserVoted() throws UserAlreadyVote {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}
	
	@Test
	@Disabled
	void testActualResultWhenSampleIsCreatedIsGuasayana() throws UserAlreadyVote {

		assertEquals(sample.getActualResult(), "Vinchuca Guasayana");
	}
	
	@Test
	void testActualResultWhenSampleIsCreatedIsUndefined() throws UserAlreadyVote {
		//when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		//when(opinion.getUser()).thenReturn(user);
		//when(opinion.getOpinionType()).thenReturn("Vinchuca Guasayana");
		//when(user.getName()).thenReturn("Tomas");
		sample = new Sample(location, photo, new Opinion(OpinionType.VINCHUCA_GUASAYANA, new User("Tomas", WebApplication.createApp())));
		
		Opinion opinion2 = new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Diego", WebApplication.createApp()));
		//when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2020, 10, 5));
		//when(opinion.getUser()).thenReturn(user);
		//when(user.getName()).thenReturn("Diego");
		//when(opinion.getOpinionType()).thenReturn("Image Unclear");
		sample.addOpinion(opinion2);
		
		Opinion opinion3 = new Opinion(OpinionType.VINCHUCA_GUASAYANA, new User("Julio", WebApplication.createApp()));
		Opinion opinion4 = new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Julio", WebApplication.createApp()));
		
		Opinion opinion5 = new Opinion(OpinionType.NOTHING, new User("Julio", WebApplication.createApp()));
		sample.addOpinion(opinion3);
		sample.addOpinion(opinion4);
		sample.addOpinion(opinion5);
		
		assertEquals(sample.getActualResult(), "UNDEFINED");
	}
	
	private void addOpinionJuanito(Opinion opinion, Sample sample) throws UserAlreadyVote {
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinion.getUser()).thenReturn(user);
		when(user.getName()).thenReturn("Juanito");
		when(user.isExpert()).thenReturn(true);
		sample.addOpinion(opinion);
	}
	
	private void addOpinionDiego(Opinion opinion, Sample sample) throws UserAlreadyVote {
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinion.getUser()).thenReturn(user);
		when(user.getName()).thenReturn("Diego");
		when(user.isExpert()).thenReturn(true);
		sample.addOpinion(opinion);
	}
}