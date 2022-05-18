package a.la.caza.de.las.vinchucas.opinions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OpinionTest {

	Opinion nothing, imageUnclear, 
	vinchucaInfestans, vinchucaSordida, vinchucaGuasayana, 
	chinchePhtia, chincheFoliada;
	
	
	@BeforeEach 
	void setUp() {
		
	}
	
	@Test
	void testCreationOpinion(){
		nothing = new Opinion(OpinionType.NOTHING); 
		imageUnclear  = new Opinion(OpinionType.IMAGE_UNCLEAR);
	 vinchucaInfestans  = new Opinion(OpinionType.VINCHUCA_INFESTANS);
	 vinchucaSordida  = new Opinion(OpinionType.VINCHUCA_SORDIDA);
	 vinchucaGuasayana = new Opinion(OpinionType.VINCHUCA_GUASAYANA);
	 chinchePhtia = new  Opinion(OpinionType.CHINCHE_PHTIA);
	 chincheFoliada = new  Opinion(OpinionType.CHINCHE_FOLIADA);
		assertAll(
				()-> assertEquals(nothing.getDateOfIssue(), LocalDate.now()),
				()-> assertEquals(nothing.getOpinionType(), "Nothing"),
				()-> assertEquals(imageUnclear.getOpinionType(), "Image Unclear"),
				()-> assertEquals(vinchucaInfestans.getOpinionType(), "Vinchuca Infestans"),
				()-> assertEquals(vinchucaSordida.getOpinionType(), "Vinchuca Sordida"),
				()-> assertEquals(vinchucaGuasayana.getOpinionType(), "Vinchuca Guasayana"), 
				()-> assertEquals(chinchePhtia.getOpinionType(), "Chinche Phtia"),
				()-> assertEquals(chincheFoliada.getOpinionType(), "Chinche Foliada")
				);
	}
}
