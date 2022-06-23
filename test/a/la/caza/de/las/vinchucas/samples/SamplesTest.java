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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.opinions.UndefinedOpinion;
import a.la.caza.de.las.vinchucas.samples.state.BasicVotedSampleState;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class SamplesTest {
	private Sample sample;
	private Location location;
	private Photo photo;
	private Opinion opinion;
	private User user;
	private User user1;
	private User user2;
	private WebApplication webApplication;

	@BeforeEach
	void setUp() throws Exception {
		location = mock(Location.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		user = mock(User.class);
		user1 = mock(User.class);
		user2 = mock(User.class);
		webApplication = mock(WebApplication.class);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn(OpinionType.VINCHUCA_GUASAYANA);
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
		verify(basicState, times(1)).getLevelVerification();
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
		sample.addOpinion(getOpinionBasic(user1, OpinionType.CHINCHE_PHTIA));

		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Dada Basic");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testAddOpinionWhenTheUserHasExpertKnowledge() throws Exception {
		sample.addOpinion(getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testSampleIsVerifyWhen2ExpertVoted() throws Exception {
		sample.addOpinion(getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testIsVerifyTheUserWhoSendTheSampleAndOtherExpertVoted() throws Exception {
		sample.addOpinion(getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testIsVerifyAlthoughtAnotherUserVoted() throws Exception {
		sample.addOpinion(getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(user1, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testActualResultWhenSampleIsCreatedIsUndefined() throws Exception {
		User user3 = mock(User.class);
		User user4 = mock(User.class);
		User user5 = mock(User.class);
		sample = new Sample(location, photo, getOpinionBasic(user1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionBasic(user2, OpinionType.IMAGE_UNCLEAR));
		sample.addOpinion(getOpinionBasic(user3, OpinionType.IMAGE_UNCLEAR));
		sample.addOpinion(getOpinionBasic(user4, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionBasic(user5, OpinionType.NOTHING));
		assertEquals(sample.getActualResult(), UndefinedOpinion.UNDEFINED);
	}

	@Test
	void testActualResultIsChincheFolidaWhenIsVerified() throws Exception {
		sample = new Sample(location, photo, getOpinionSpecialist(user1, OpinionType.CHINCHE_FOLIADA));
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), OpinionType.CHINCHE_FOLIADA);
	}

	@Test
	void testActualResultIsChincheFolidaWhenIsVotedByOneExpert() throws Exception {
		sample = new Sample(location, photo, getOpinionBasic(user1, OpinionType.IMAGE_UNCLEAR));
		sample.addOpinion(getOpinionSpecialist(user2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), OpinionType.CHINCHE_FOLIADA);
	}

	@Test
	void testActualResultShouldBeChincheFoliada() throws Exception {
		sample.addOpinion(getOpinionBasic(user1, OpinionType.CHINCHE_FOLIADA));
		sample.addOpinion(getOpinionBasic(user2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(OpinionType.CHINCHE_FOLIADA, sample.getActualResult());
		;
	}

	@Test
	void testActualResultWhenSampleIsCreatedIsImageUnclear() throws Exception {
		Sample sampleTest = new Sample(location, photo, getOpinionBasic(user, OpinionType.IMAGE_UNCLEAR));
		assertEquals(OpinionType.IMAGE_UNCLEAR, sampleTest.getActualResult());
	}

	@Test
	void testUserAlreadyVoteException() throws Exception {
		Sample sample = new Sample(location, photo, getOpinionBasic(user, OpinionType.IMAGE_UNCLEAR));
		assertThrows(UserValidationException.class,
				() -> sample.addOpinion(getOpinionBasic(user, OpinionType.IMAGE_UNCLEAR)));
	}

	@Test
	void testUserIsNotExpertException() throws Exception {
		User userBasic = mock(User.class);
		Sample sample = new Sample(location, photo, getOpinionSpecialist(user, OpinionType.IMAGE_UNCLEAR));
		assertThrows(UserValidationException.class,
				() -> sample.addOpinion(getOpinionBasic(userBasic, OpinionType.IMAGE_UNCLEAR)));
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