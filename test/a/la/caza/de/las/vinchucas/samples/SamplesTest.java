package a.la.caza.de.las.vinchucas.samples;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.state.BasicVotedSampleState;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.Knowledge;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeExpert;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeSpecialist;

public class SamplesTest {

	private Sample sample;
	private Location location;
	private Photo photo;
	private Opinion opinion;
	private User user;
	private WebApplication weApplication;

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
	void testCreateANewSample() throws Exception {
		when(opinion.getUser()).thenReturn(user);
		assertEquals(location, sample.getLocation());
		assertEquals(photo, sample.getPhoto());
		assertEquals(sample.getCreationDate(), LocalDate.now());
		assertTrue(sample.getOpinionHistory().size() > 0);
		assertEquals(sample.getUser(), user);
	}

	@Test
	void testWhenSampleIsCreatedHasBeenVotedByTheUserWhoSendIt() throws Exception {
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertEquals(sample.getUser().getName(), "Tomas");
		
	}
	
	@Test
	void testSampleCanGetHisOpinionHistoryAndLastVotation() throws Exception {
		assertEquals(sample.getLastVotation(), LocalDate.now());
		assertTrue(sample.getOpinionHistory().size() > 0);
	}
	
	@Test
	void testSampleKnowsHisLevelVerification() throws Exception {
		BasicVotedSampleState basicState = mock(BasicVotedSampleState.class);
		sample.setState(basicState);
		sample.getLevelVerification();
		verify(basicState, times(1)).getLevelVerification(sample);
	}
	
	@Test
	void testUserDidntVoteBefore() throws Exception {
		User newUser = mock(User.class);
		when(newUser.getId()).thenReturn(1);
		when(user.getId()).thenReturn(2);
		when(opinion.getUser()).thenReturn(newUser);		
		assertFalse(sample.userAlreadyVote(List.of(opinion), user));
	}
	
	@Test
	void testUserAlreadyVote() throws Exception {
		when(user.getId()).thenReturn(1);
		when(opinion.getUser()).thenReturn(user);		
		assertTrue(sample.userAlreadyVote(List.of(opinion), user));
	}
	
	@Test
	void testAddUserOpinion() throws Exception {
		sample.addUserOpinion(opinion);
		assertTrue(sample.getOpinionHistory().size() > 0);
	}
	
	@Test
	void testAddOpinionWithState() throws Exception {
		BasicVotedSampleState basicState = mock(BasicVotedSampleState.class);
		sample.setState(basicState);
		sample.addOpinion(opinion);
		verify(basicState, times(1)).addOpinion(sample, opinion);
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
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Diego");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testSampleIsVerifyWhen2ExpertVoted() throws Exception {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testIsVerifyTheUserWhoSendTheSampleAndOtherExpertVoted() throws Exception {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}
	
	@Test
	void testIsVerifyAlthoughtAnotherUserVoted() throws Exception {
		addOpinionDiego(opinion, sample);
		addOpinionJuanito(opinion, sample);
		addOpinionJuanito(opinion, sample);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Juanito");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}
	
	
	
	
	
	@Test
	void testActualResultWhenSampleIsCreatedIsUndefined() throws Exception {
		//when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		//when(opinion.getUser()).thenReturn(user);
		//when(opinion.getOpinionType()).thenReturn("Vinchuca Guasayana");
		//when(user.getName()).thenReturn("Tomas");
		WebApplication webApp = new WebApplication();
		sample = new Sample(location, photo, new Opinion(OpinionType.VINCHUCA_GUASAYANA, new User("Tomas", new KnowledgeBasic(), webApp)));
		
		Opinion opinion2 = new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Diego", new KnowledgeBasic(), webApp));
		//when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2020, 10, 5));
		//when(opinion.getUser()).thenReturn(user);
		//when(user.getName()).thenReturn("Diego");
		//when(opinion.getOpinionType()).thenReturn("Image Unclear");
		sample.addOpinion(opinion2);
		
		Opinion opinion3 = new Opinion(OpinionType.VINCHUCA_GUASAYANA, new User("Julio", new KnowledgeBasic(), webApp));
		Opinion opinion4 = new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Julio", new KnowledgeBasic(), webApp));
		
		Opinion opinion5 = new Opinion(OpinionType.NOTHING, new User("Julio", new KnowledgeBasic(), webApp));
		sample.addOpinion(opinion3);
		sample.addOpinion(opinion4);
		sample.addOpinion(opinion5);
		
		assertEquals(sample.getActualResult(), "UNDEFINED");
	}
	
	@Test
	void testActualResultIsChincheFolidaWhenIsVerified() throws Exception {
		WebApplication webApp = new WebApplication();
		sample = new Sample(location, photo, new Opinion(OpinionType.CHINCHE_FOLIADA, new User("Tomas", new KnowledgeSpecialist(), webApp)));
		
		Opinion opinion2 = new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Diego", new KnowledgeSpecialist(), webApp));
		sample.addOpinion(opinion2);		
		assertEquals(sample.getActualResult(), "Chinche Foliada");
	}
	
	@Test
	void testActualResultIsChincheFolidaWhenIsVotedByOneExpert() throws Exception {
		WebApplication webApp = new WebApplication();
		sample = new Sample(location, photo, new Opinion(OpinionType.IMAGE_UNCLEAR, new User("Tomas", new KnowledgeBasic(), webApp)));
		
		Opinion opinion2 = new Opinion(OpinionType.CHINCHE_FOLIADA, new User("Diego", new KnowledgeSpecialist(), webApp));
		sample.addOpinion(opinion2);		
		assertEquals(sample.getActualResult(), "Chinche Foliada");
	}
	
	@Test
	void testActualResultShouldBeChincheFoliada() throws Exception {
		WebApplication webApp = new WebApplication();
		sample.addOpinion(new Opinion(OpinionType.CHINCHE_FOLIADA, new User("Diego", new KnowledgeBasic(), webApp)));
		sample.addOpinion(new Opinion(OpinionType.CHINCHE_FOLIADA, new User("Pepe", new KnowledgeBasic(), webApp)));
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