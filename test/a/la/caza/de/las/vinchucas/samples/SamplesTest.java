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
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.Knowledge;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeExpert;

public class SamplesTest {

	Sample sample;
	Location location;
	Photo photo;
	Opinion opinion;
	User user;
	WebApplication weApplication;

	@BeforeEach
	void setUp() throws Exception {
		location = mock(Location.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		user = mock(User.class);
		weApplication = mock(WebApplication.class);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn("Vinchuca Guasayana");
		when(user.getName()).thenReturn("Tomas");
		sample = new Sample(location, photo, opinion);
	}

	@Test
	void testSampleIsVotedWhenIsCreated() throws Exception {
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertEquals(sample.getUser().getName(), "Tomas");
		assertEquals(sample.getCreationDate(), LocalDate.now());
		assertEquals(sample.getLastVotation(), LocalDate.now());
	}

	@Test
	void testAddOpinionWhenTheUserHasBasicKnowledge() throws Exception {
		User userRoberto = mock(User.class);
		Opinion opinion = mock(Opinion.class);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2020, 10, 5));
		when(opinion.getUser()).thenReturn(userRoberto);
		when(userRoberto.getName()).thenReturn("Roberto");
		when(userRoberto.getId()).thenReturn(15434534);
		sample.addOpinion(opinion);
		
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Roberto");
		assertEquals(sample.getLastVotation(), LocalDate.of(2020, 10, 5));
	}

	@Test
	void testAddOpinionWhenTheUserHasExpertKnowledge() throws Exception {
		addOpinionDiego(opinion, sample);
		
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 1);
		assertEquals(sample.getOpinionHistory().get(0).getUser().getName(), "Diego");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testSampleIsVerifyWhen2ExpertVoted() throws Exception {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testIsVerifyTheUserWhoSendTheSampleAndOtherExpertVoted() throws Exception {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}
	
	@Test
	void testIsVerifyAlthoughtAnotherUserVoted() throws Exception {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}
	
	@Test
	void testActualResultWhenSampleIsCreatedIsUndefined() throws Exception {
		//when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		//when(opinion.getUser()).thenReturn(user);
		//when(opinion.getOpinionType()).thenReturn("Vinchuca Guasayana");
		//when(user.getName()).thenReturn("Tomas");
		sample = new Sample(location, photo, new Opinion(OpinionType.VINCHUCA_GUASAYANA, new User("Tomas", new KnowledgeBasic(), WebApplication.createApp())));
		
		Opinion opinion2 = new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Diego", new KnowledgeBasic(), WebApplication.createApp()));
		//when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2020, 10, 5));
		//when(opinion.getUser()).thenReturn(user);
		//when(user.getName()).thenReturn("Diego");
		//when(opinion.getOpinionType()).thenReturn("Image Unclear");
		sample.addOpinion(opinion2);
		
		Opinion opinion3 = new Opinion(OpinionType.VINCHUCA_GUASAYANA, new User("Julio", new KnowledgeBasic(), WebApplication.createApp()));
		Opinion opinion4 = new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Julio", new KnowledgeBasic(), WebApplication.createApp()));
		
		Opinion opinion5 = new Opinion(OpinionType.NOTHING, new User("Julio", new KnowledgeBasic(), WebApplication.createApp()));
		sample.addOpinion(opinion3);
		sample.addOpinion(opinion4);
		sample.addOpinion(opinion5);
		
		assertEquals(sample.getActualResult(), "UNDEFINED");
	}
	
	@Test
	void testActualResultShouldBeChincheFoliada() throws Exception {
		sample.addOpinion(new Opinion(OpinionType.CHINCHE_FOLIADA, new User("Diego", new KnowledgeBasic(), WebApplication.createApp())));
		sample.addOpinion(new Opinion(OpinionType.CHINCHE_FOLIADA, new User("Pepe", new KnowledgeBasic(), weApplication.createApp())));
		assertEquals("Chinche Foliada", sample.getActualResult());;
	}
	
	@Test
	void testActualResultWhenSampleIsCreatedIsImageUnclear() throws Exception {
		Sample sampleTest = new Sample(location, photo, new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Diego", new KnowledgeExpert(), weApplication)));
		assertEquals("Image Unclear", sampleTest.getActualResult());;
	}
	
	private void addOpinionJuanito(Opinion opinion, Sample sample) throws Exception {
		User userJuancito = mock(User.class);
		Opinion opinionJuan = mock(Opinion.class);
		when(opinionJuan.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinionJuan.getUser()).thenReturn(userJuancito);
		when(userJuancito.getName()).thenReturn("Juanito");
		when(userJuancito.getId()).thenReturn(2206);
		when(userJuancito.hasExpertKnowledge()).thenReturn(true);
		sample.addOpinion(opinionJuan);
	}
	
	private void addOpinionDiego(Opinion opinion, Sample sample) throws Exception {
		User userDiego = mock(User.class);
		Opinion opinionDiego = mock(Opinion.class);
		when(opinionDiego.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinionDiego.getUser()).thenReturn(userDiego);
		when(userDiego.getName()).thenReturn("Diego");
		when(userDiego.getId()).thenReturn(1000);
		when(userDiego.hasExpertKnowledge()).thenReturn(true);
		sample.addOpinion(opinionDiego);
	}
}