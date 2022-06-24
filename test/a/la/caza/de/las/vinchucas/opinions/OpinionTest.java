package a.la.caza.de.las.vinchucas.opinions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class OpinionTest {
	private Opinion nothing, imageUnclear, vinchucaInfestans, vinchucaSordida, vinchucaGuasayana, chinchePhtia,
			chincheFoliada;
	private User user;
	private WebApplication webApplication;
	GenericOpinionType undefined;

	@BeforeEach
	void setUp() throws CloneNotSupportedException {
		webApplication = mock(WebApplication.class);
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		nothing = new Opinion(OpinionType.NOTHING, user);
		imageUnclear = new Opinion(OpinionType.IMAGE_UNCLEAR, user);
		vinchucaInfestans = new Opinion(OpinionType.VINCHUCA_INFESTANS, user);
		vinchucaSordida = new Opinion(OpinionType.VINCHUCA_SORDIDA, user);
		vinchucaGuasayana = new Opinion(OpinionType.VINCHUCA_GUASAYANA, user);
		chinchePhtia = new Opinion(OpinionType.CHINCHE_PHTIA, user);
		chincheFoliada = new Opinion(OpinionType.CHINCHE_FOLIADA, user);
		undefined = UndefinedOpinion.UNDEFINED;
	}

	@Test
	void testCreationOpinion() {
		assertAll(
				() -> assertEquals(nothing.getOpinionType(), OpinionType.NOTHING),
				() -> assertEquals(nothing.getUser().getName(), user.getName()),
				() -> assertEquals(nothing.getDateOfIssue(), LocalDate.now()),
				() -> assertEquals(nothing.getOpinionType(), OpinionType.NOTHING),
				() -> assertEquals(imageUnclear.getOpinionType(), OpinionType.IMAGE_UNCLEAR),
				() -> assertEquals(vinchucaInfestans.getOpinionType(), OpinionType.VINCHUCA_INFESTANS),
				() -> assertEquals(vinchucaSordida.getOpinionType(), OpinionType.VINCHUCA_SORDIDA),
				() -> assertEquals(vinchucaGuasayana.getOpinionType(), OpinionType.VINCHUCA_GUASAYANA),
				() -> assertEquals(chinchePhtia.getOpinionType(), OpinionType.CHINCHE_PHTIA),
				() -> assertEquals(chincheFoliada.getOpinionType(), OpinionType.CHINCHE_FOLIADA),
				() -> assertEquals(undefined, UndefinedOpinion.UNDEFINED)
				);
	}
}
