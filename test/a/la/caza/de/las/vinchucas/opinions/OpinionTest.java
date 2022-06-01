package a.la.caza.de.las.vinchucas.opinions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class OpinionTest {
	private Opinion nothing, imageUnclear, vinchucaInfestans, vinchucaSordida, vinchucaGuasayana, chinchePhtia,
			chincheFoliada;
	private User user;
	private WebApplication webApplication;

	@BeforeEach
	void setUp() {
		webApplication = mock(WebApplication.class);
		user = new User("Diego", new KnowledgeBasic(), webApplication);
		nothing = new Opinion(OpinionType.NOTHING, user);
		imageUnclear = new Opinion(OpinionType.IMAGE_UNCLEAR, user);
		vinchucaInfestans = new Opinion(OpinionType.VINCHUCA_INFESTANS, user);
		vinchucaSordida = new Opinion(OpinionType.VINCHUCA_SORDIDA, user);
		vinchucaGuasayana = new Opinion(OpinionType.VINCHUCA_GUASAYANA, user);
		chinchePhtia = new Opinion(OpinionType.CHINCHE_PHTIA, user);
		chincheFoliada = new Opinion(OpinionType.CHINCHE_FOLIADA, user);
	}

	@Test
	void testCreationOpinion() {
		assertAll(() -> assertEquals(nothing.getUser().getId(), user.getId()),
				() -> assertEquals(nothing.getOpinionType(), OpinionType.NOTHING),
				() -> assertEquals(nothing.getUser().getName(), user.getName()),
				() -> assertEquals(nothing.getDateOfIssue(), LocalDate.now()),
				() -> assertEquals(nothing.getOpinionTypeString(), "Nothing"),
				() -> assertEquals(imageUnclear.getOpinionTypeString(), "Image Unclear"),
				() -> assertEquals(vinchucaInfestans.getOpinionTypeString(), "Vinchuca Infestans"),
				() -> assertEquals(vinchucaSordida.getOpinionTypeString(), "Vinchuca Sordida"),
				() -> assertEquals(vinchucaGuasayana.getOpinionTypeString(), "Vinchuca Guasayana"),
				() -> assertEquals(chinchePhtia.getOpinionTypeString(), "Chinche Phtia"),
				() -> assertEquals(chincheFoliada.getOpinionTypeString(), "Chinche Foliada"),
				() -> assertEquals(OpinionType.getUndefinedOpinion(), "UNDEFINED"));
	}

	@Test
	@Disabled
	void testUserCanNotBeClone() throws CloneNotSupportedException {
		Object stringUser = new Object();
		// when(user.clone()).thenThrow(CloneNotSupportedException.class);
		assertThrows(CloneNotSupportedException.class,
				() -> new Opinion(OpinionType.CHINCHE_FOLIADA, (User) stringUser));
	}
}
