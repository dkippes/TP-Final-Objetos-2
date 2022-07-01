package a.la.caza.de.las.vinchucas.samples;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.opinions.UndefinedOpinion;
import a.la.caza.de.las.vinchucas.samples.state.BasicVotedSample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class SamplesTest {
	private Sample sample;
	private Location location;
	private Photo photo;
	private Opinion opinion;
	private User user;
	private User user1;
	private User user2;
	private WebApplication webApplication;
	private List<Opinion> opinionHistory;

	@BeforeEach
	void setUp() throws Exception {
		location = mock(Location.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		user = mock(User.class);
		user1 = mock(User.class);
		user2 = mock(User.class);
		opinionHistory = spy(new ArrayList<>());
		webApplication = mock(WebApplication.class);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn(OpinionType.VINCHUCA_GUASAYANA);
		when(user.getName()).thenReturn("Tomas");
		sample = new Sample(location, photo, opinion, opinionHistory);
	}

	@Test
	void testCreateANewSample() throws Exception {
		when(opinion.getUser()).thenReturn(user);
		assertEquals(location, sample.getLocation());
		assertEquals(photo, sample.getPhoto());
		assertEquals(sample.getCreationDate(), LocalDate.now());
		assertFalse(sample.getOpinionHistory().isEmpty());
		assertTrue(sample.getOpinionHistory().contains(opinion));
		assertEquals(sample.getLastVotation(), LocalDate.now());
		assertEquals(sample.getUser(), user);
		verify(opinionHistory).add(opinion);
	}

	@Test
	void testWhenSampleIsCreatedHasBeenVotedByTheUserWhoSendIt() throws Exception {
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertEquals(sample.getUser().getName(), "Tomas");
		assertTrue(sample.getOpinionHistory().contains(opinion));
		verify(opinionHistory).add(opinion);
	}

	@Test
	void testSampleKnowsHisLevelVerification() throws Exception {
		BasicVotedSample basicState = mock(BasicVotedSample.class);
		sample.setState(basicState);
		sample.getLevelVerification();
		verify(basicState, times(1)).getLevelVerification();
		verify(opinionHistory).add(opinion);
	}

	@Test
	void testUserDidntVoteBefore() throws Exception {
		User newUser = mock(User.class);
		when(opinion.getUser()).thenReturn(newUser);
		assertFalse(sample.userAlreadyVote(user));
	}

	@Test
	void testUserAlreadyVote() throws Exception {
		when(opinion.getUser()).thenReturn(user);
		assertTrue(sample.userAlreadyVote(user));
		verify(opinion, times(4)).getUser();
	}

	@Test
	void testAddOpinionWithState() throws Exception {
		BasicVotedSample basicState = mock(BasicVotedSample.class);
		sample.setState(basicState);
		sample.addOpinion(opinion);
		assertTrue(sample.getOpinionHistory().contains(opinion));
		verify(basicState, times(1)).addOpinion(sample, opinion);
		verify(opinionHistory).add(opinion);
	}

	@Test
	void testAddOpinionWhenTheUserHasBasicKnowledge() throws Exception {
		Opinion basicOpinion = getOpinionBasic(user1, OpinionType.CHINCHE_PHTIA);
		sample.addOpinion(basicOpinion);

		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Dada Basic");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
		assertTrue(sample.getOpinionHistory().contains(opinion));
		verify(opinionHistory).add(opinion);
	}

	@Test
	void testAddOpinionWhenTheUserHasExpertKnowledge() throws Exception {
		Opinion opinionSpecialist = getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA);
		sample.addOpinion(opinionSpecialist);
		
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
		assertTrue(sample.getOpinionHistory().contains(opinionSpecialist));
		verify(opinionHistory).add(opinionSpecialist);
	}

	@Test
	void testSampleIsVerifyWhen2ExpertVoted() throws Exception {
		Opinion opinionSpecialist1 = getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA);
		Opinion opinionSpecialist2 = getOpinionSpecialist(user2, OpinionType.VINCHUCA_GUASAYANA);
		sample.addOpinion(opinionSpecialist1);
		sample.addOpinion(opinionSpecialist2);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
		assertTrue(sample.getOpinionHistory().contains(opinionSpecialist1));
		assertTrue(sample.getOpinionHistory().contains(opinionSpecialist2));
		verify(opinionHistory).add(opinionSpecialist1);
		verify(opinionHistory).add(opinionSpecialist2);
	}

	@Test
	void testIsVerifyTheUserWhoSendTheSampleAndOtherExpertVoted() throws Exception {
		Opinion opinionSpecialist1 = getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA);
		Opinion opinionSpecialist2 = getOpinionSpecialist(user2, OpinionType.VINCHUCA_GUASAYANA);
		sample.addOpinion(opinionSpecialist1);
		sample.addOpinion(opinionSpecialist2);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
		assertTrue(sample.getOpinionHistory().contains(opinionSpecialist1));
		assertTrue(sample.getOpinionHistory().contains(opinionSpecialist2));
		verify(opinionHistory).add(opinionSpecialist1);
		verify(opinionHistory).add(opinionSpecialist2);
	}

	@Test
	void testIsVerifyAlthoughtAnotherUserVoted() throws Exception {
		Opinion opinionSpecialist1 = getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA);
		Opinion opinionSpecialist2 = getOpinionSpecialist(user2, OpinionType.VINCHUCA_GUASAYANA);
		sample.addOpinion(opinionSpecialist1);
		sample.addOpinion(opinionSpecialist2);
		sample.addOpinion(opinionSpecialist1);

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
		assertTrue(sample.getOpinionHistory().contains(opinionSpecialist1));
		assertTrue(sample.getOpinionHistory().contains(opinionSpecialist2));
		verify(opinionHistory).add(opinionSpecialist1);
		verify(opinionHistory).add(opinionSpecialist2);
	}

	@Test
	void testActualResultWhenSampleIsCreatedIsUndefined() throws Exception {
		Opinion opinionBasic2 = getOpinionBasic(user2, OpinionType.IMAGE_UNCLEAR);
		Opinion opinionBasic3 = getOpinionBasic(mock(User.class), OpinionType.NOTHING);
		Opinion opinionBasic4 = getOpinionBasic(mock(User.class), OpinionType.VINCHUCA_GUASAYANA);
		Opinion opinionBasic5 = getOpinionBasic(mock(User.class), OpinionType.IMAGE_UNCLEAR);
		opinionHistory = spy(new ArrayList<>());
		sample = new Sample(location, photo, getOpinionBasic(user1, OpinionType.VINCHUCA_GUASAYANA), opinionHistory);
		sample.addOpinion(opinionBasic2);
		sample.addOpinion(opinionBasic3);
		sample.addOpinion(opinionBasic4);
		sample.addOpinion(opinionBasic5);
		assertEquals(sample.getActualResult(), UndefinedOpinion.UNDEFINED);
		verify(opinionHistory).add(opinionBasic2);
		verify(opinionHistory).add(opinionBasic3);
		verify(opinionHistory).add(opinionBasic4);
		verify(opinionHistory).add(opinionBasic5);
	}

	@Test
	void testActualResultIsChincheFolidaWhenIsVerified() throws Exception {
		sample = new Sample(location, photo, getOpinionSpecialist(user1, OpinionType.CHINCHE_FOLIADA), opinionHistory);
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), OpinionType.CHINCHE_FOLIADA);
	}

	@Test
	void testActualResultIsChincheFolidaWhenIsVotedByOneExpert() throws Exception {
		sample = new Sample(location, photo, getOpinionBasic(user1, OpinionType.IMAGE_UNCLEAR), opinionHistory);
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), OpinionType.CHINCHE_FOLIADA);
	}

	@Test
	void testActualResultShouldBeChincheFoliada() throws Exception {
		sample.addOpinion(getOpinionBasic(user1, OpinionType.CHINCHE_FOLIADA));
		sample.addOpinion(getOpinionBasic(user2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(OpinionType.CHINCHE_FOLIADA, sample.getActualResult());
	}

	@Test
	void testActualResultWhenSampleIsCreatedIsImageUnclear() throws Exception {
		opinionHistory = spy(new ArrayList<>());
		Sample sampleTest = new Sample(location, photo, getOpinionBasic(user, OpinionType.IMAGE_UNCLEAR), opinionHistory);
		assertEquals(OpinionType.IMAGE_UNCLEAR, sampleTest.getActualResult());
	}

	@Test
	void testUserAlreadyVoteException() throws Exception {
		opinionHistory = spy(new ArrayList<>());
		Sample sample = new Sample(location, photo, getOpinionBasic(user, OpinionType.IMAGE_UNCLEAR), opinionHistory);
		assertThrows(UserValidationException.class,
				() -> sample.addOpinion(getOpinionBasic(user, OpinionType.IMAGE_UNCLEAR)));
	}

	@Test
	void testUserIsNotExpertException() throws Exception {
		User userBasic = spy(new User("Diego", new KnowledgeBasic(), webApplication));
		opinionHistory = spy(new ArrayList<>());
		Sample sample = new Sample(location, photo, getOpinionSpecialist(user, OpinionType.IMAGE_UNCLEAR), opinionHistory);
		assertThrows(UserValidationException.class,
				() -> sample.addOpinion(getOpinionBasic(userBasic, OpinionType.CHINCHE_FOLIADA)));
	}
	
	@Test
	void testWhenSampleIsVotedBy2ExpertsWithDifferentsOpinionsIsUndefined() throws Exception {
		User anotherSpecialistUser = mock(User.class);
		opinionHistory = spy(new ArrayList<>());
		Sample sample = new Sample(location, photo, getOpinionSpecialist(user, OpinionType.IMAGE_UNCLEAR), opinionHistory);
		sample.addOpinion(getOpinionSpecialist(anotherSpecialistUser, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), UndefinedOpinion.UNDEFINED);
		assertEquals(sample.getLevelVerification(), Vote.VOTED);
	}
	
	@Test
	void testWhenSampleIsVerifiedButHas3ExpertVoted() throws Exception {
		User user1 = mock(User.class);
		User user2 = mock(User.class);
		opinionHistory = spy(new ArrayList<>());
		Sample sample = new Sample(location, photo, getOpinionSpecialist(user, OpinionType.IMAGE_UNCLEAR), opinionHistory);
		sample.addOpinion(getOpinionSpecialist(user1, OpinionType.CHINCHE_FOLIADA));
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), OpinionType.CHINCHE_FOLIADA);
		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
	}

	private Opinion getOpinionBasic(User user, OpinionType opinionType) {
		Opinion opinion = mock(Opinion.class);
		when(user.getName()).thenReturn("Dada Basic");
		when(user.getWebApplication()).thenReturn(webApplication);
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn(opinionType);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinion.getOpinionType()).thenReturn(opinionType);
		return opinion;
	}

	
	private Opinion getOpinionSpecialist(User user, OpinionType opinionType) {
		Opinion opinion = mock(Opinion.class);
		when(user.getName()).thenReturn("Dada Specialist");
		when(user.getWebApplication()).thenReturn(webApplication);
		when(user.hasExpertKnowledge()).thenReturn(true);
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn(opinionType);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinion.getOpinionType()).thenReturn(opinionType);
		return opinion;
	}
}