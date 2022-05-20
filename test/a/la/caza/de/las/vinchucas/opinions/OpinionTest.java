package a.la.caza.de.las.vinchucas.opinions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.users.User;

public class OpinionTest {

	Opinion nothing, imageUnclear, vinchucaInfestans, vinchucaSordida, vinchucaGuasayana, chinchePhtia, chincheFoliada;
	User user;

	@BeforeEach
	void setUp() {
		user = mock(User.class);
	}

	@Test
	void testCreationOpinion() {
		nothing = new Opinion(OpinionType.NOTHING, user);
		imageUnclear = new Opinion(OpinionType.IMAGE_UNCLEAR, user);
		vinchucaInfestans = new Opinion(OpinionType.VINCHUCA_INFESTANS, user);
		vinchucaSordida = new Opinion(OpinionType.VINCHUCA_SORDIDA, user);
		vinchucaGuasayana = new Opinion(OpinionType.VINCHUCA_GUASAYANA, user);
		chinchePhtia = new Opinion(OpinionType.CHINCHE_PHTIA, user);
		chincheFoliada = new Opinion(OpinionType.CHINCHE_FOLIADA, user);
		assertAll(() -> assertEquals(nothing.getUser(), user),
				() -> assertEquals(nothing.getDateOfIssue(), LocalDate.now()),
				() -> assertEquals(nothing.getOpinionType(), "Nothing"),
				() -> assertEquals(imageUnclear.getOpinionType(), "Image Unclear"),
				() -> assertEquals(vinchucaInfestans.getOpinionType(), "Vinchuca Infestans"),
				() -> assertEquals(vinchucaSordida.getOpinionType(), "Vinchuca Sordida"),
				() -> assertEquals(vinchucaGuasayana.getOpinionType(), "Vinchuca Guasayana"),
				() -> assertEquals(chinchePhtia.getOpinionType(), "Chinche Phtia"),
				() -> assertEquals(chincheFoliada.getOpinionType(), "Chinche Foliada"));
	}
}
