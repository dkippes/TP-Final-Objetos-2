package a.la.caza.de.las.vinchucas.opinions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class OpinionTest {

	Opinion nothing, imageUnclear, vinchucaInfestans, vinchucaSordida, vinchucaGuasayana, chinchePhtia, chincheFoliada;
	User user;
	WebApplication webApplication;

	@BeforeEach
	void setUp() {
		webApplication = mock(WebApplication.class);
		user = new User("Diego", new KnowledgeBasic(), webApplication);
	}

	@Test
	void testCreationOpinion() throws CloneNotSupportedException {
		nothing = new Opinion(OpinionType.NOTHING, user);
		imageUnclear = new Opinion(OpinionType.IMAGE_UNCLEAR, user);
		vinchucaInfestans = new Opinion(OpinionType.VINCHUCA_INFESTANS, user);
		vinchucaSordida = new Opinion(OpinionType.VINCHUCA_SORDIDA, user);
		vinchucaGuasayana = new Opinion(OpinionType.VINCHUCA_GUASAYANA, user);
		chinchePhtia = new Opinion(OpinionType.CHINCHE_PHTIA, user);
		chincheFoliada = new Opinion(OpinionType.CHINCHE_FOLIADA, user);
		assertAll(() -> assertEquals(nothing.getUser().getId(), user.getId()),
				() -> assertEquals(nothing.getUser().getName(), user.getName()),
				() -> assertEquals(nothing.getDateOfIssue(), LocalDate.now()),
				() -> assertEquals(nothing.getOpinionType(), "Nothing"),
				() -> assertEquals(imageUnclear.getOpinionType(), "Image Unclear"),
				() -> assertEquals(vinchucaInfestans.getOpinionType(), "Vinchuca Infestans"),
				() -> assertEquals(vinchucaSordida.getOpinionType(), "Vinchuca Sordida"),
				() -> assertEquals(vinchucaGuasayana.getOpinionType(), "Vinchuca Guasayana"),
				() -> assertEquals(chinchePhtia.getOpinionType(), "Chinche Phtia"),
				() -> assertEquals(chincheFoliada.getOpinionType(), "Chinche Foliada"),
				() -> assertEquals(OpinionType.getUndefinedOpinion(), "UNDEFINED"));
	}
}
