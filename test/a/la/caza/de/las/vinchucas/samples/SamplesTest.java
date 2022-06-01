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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.state.BasicVotedSampleState;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class SamplesTest {
	private Sample sample;
	private Location location;
	private Photo photo;
	private Opinion opinion;
	private User user;
	private WebApplication webApplication;

	@BeforeEach
	void setUp() throws Exception {
		location = mock(Location.class);
		opinion = mock(Opinion.class);
		photo = mock(Photo.class);
		user = mock(User.class);
		webApplication = mock(WebApplication.class);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.now());
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionTypeString()).thenReturn("Vinchuca Guasayana");
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
		sample.addOpinion(getOpinionBasic(1, OpinionType.CHINCHE_PHTIA));

		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Dada Basic");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testAddOpinionWhenTheUserHasExpertKnowledge() throws Exception {
		sample.addOpinion(getOpinionSpecialist(1, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VOTED);
		assertTrue(sample.getOpinionHistory().size() == 2);
		assertEquals(sample.getOpinionHistory().get(1).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testSampleIsVerifyWhen2ExpertVoted() throws Exception {
		sample.addOpinion(getOpinionSpecialist(1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(2, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testIsVerifyTheUserWhoSendTheSampleAndOtherExpertVoted() throws Exception {
		sample.addOpinion(getOpinionSpecialist(1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(2, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testIsVerifyAlthoughtAnotherUserVoted() throws Exception {
		sample.addOpinion(getOpinionSpecialist(1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(2, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionSpecialist(3, OpinionType.VINCHUCA_GUASAYANA));

		assertEquals(sample.getLevelVerification(), Vote.VERIFIED);
		assertTrue(sample.getOpinionHistory().size() == 3);
		assertEquals(sample.getOpinionHistory().get(2).getUser().getName(), "Dada Specialist");
		assertEquals(sample.getLastVotation(), LocalDate.of(2019, 7, 15));
	}

	@Test
	void testActualResultWhenSampleIsCreatedIsUndefined() throws Exception {
		sample = new Sample(location, photo, getOpinionBasic(1, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionBasic(2, OpinionType.IMAGE_UNCLEAR));
		sample.addOpinion(getOpinionBasic(3, OpinionType.IMAGE_UNCLEAR));
		sample.addOpinion(getOpinionBasic(4, OpinionType.VINCHUCA_GUASAYANA));
		sample.addOpinion(getOpinionBasic(5, OpinionType.NOTHING));
		assertEquals(sample.getActualResult(), "UNDEFINED");
	}

	@Test
	void testActualResultIsChincheFolidaWhenIsVerified() throws Exception {
		sample = new Sample(location, photo, getOpinionSpecialist(1, OpinionType.CHINCHE_FOLIADA));
		sample.addOpinion(getOpinionSpecialist(2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), "Chinche Foliada");
	}

	@Test
	void testActualResultIsChincheFolidaWhenIsVotedByOneExpert() throws Exception {
		sample = new Sample(location, photo, getOpinionBasic(1, OpinionType.IMAGE_UNCLEAR));
		sample.addOpinion(getOpinionSpecialist(2, OpinionType.CHINCHE_FOLIADA));
		assertEquals(sample.getActualResult(), "Chinche Foliada");
	}

	@Test
	void testActualResultShouldBeChincheFoliada() throws Exception {
		sample.addOpinion(getOpinionBasic(1, OpinionType.CHINCHE_FOLIADA));
		sample.addOpinion(getOpinionBasic(2, OpinionType.CHINCHE_FOLIADA));
		assertEquals("Chinche Foliada", sample.getActualResult());
		;
	}

	@Test
	void testActualResultWhenSampleIsCreatedIsImageUnclear() throws Exception {
		Sample sampleTest = new Sample(location, photo, getOpinionBasic(2, OpinionType.IMAGE_UNCLEAR));
		assertEquals("Image Unclear", sampleTest.getActualResult());
		;
	}

	@Test
	void testUserAlreadyVoteException() throws Exception {
		Sample sample = new Sample(location, photo, getOpinionBasic(1, OpinionType.IMAGE_UNCLEAR));
		assertThrows(UserValidationException.class,
				() -> sample.addOpinion(getOpinionBasic(1, OpinionType.IMAGE_UNCLEAR)));
	}

	@Test
	void testUserIsNotExpertException() throws Exception {
		Sample sample = new Sample(location, photo, getOpinionSpecialist(1, OpinionType.IMAGE_UNCLEAR));
		assertThrows(UserValidationException.class,
				() -> sample.addOpinion(getOpinionBasic(2, OpinionType.IMAGE_UNCLEAR)));
	}

	private Opinion getOpinionBasic(int id, OpinionType opinionType) {
		Opinion opinion = mock(Opinion.class);
		User user = mock(User.class);
		when(user.getId()).thenReturn(id);
		when(user.getName()).thenReturn("Dada Basic");
		when(user.getWebApplication()).thenReturn(webApplication);
		when(user.getKnowledge()).thenReturn(new KnowledgeBasic());
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn(opinionType);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinion.getOpinionTypeString()).thenReturn(opinionType.getOpinionType());
		return opinion;
	}

	private Opinion getOpinionSpecialist(int id, OpinionType opinionType) {
		Opinion opinion = mock(Opinion.class);
		User user = mock(User.class);
		when(user.getId()).thenReturn(id);
		when(user.getName()).thenReturn("Dada Specialist");
		when(user.getWebApplication()).thenReturn(webApplication);
		when(user.hasExpertKnowledge()).thenReturn(true);
		when(opinion.getUser()).thenReturn(user);
		when(opinion.getOpinionType()).thenReturn(opinionType);
		when(opinion.getDateOfIssue()).thenReturn(LocalDate.of(2019, 7, 15));
		when(opinion.getOpinionTypeString()).thenReturn(opinionType.getOpinionType());
		return opinion;
	}
}