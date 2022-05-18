package a.la.caza.de.las.vinchucas.samples;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhotoTest {
	
	Photo photoJPG, photoJPEG,photoPNG; 
	
	
	
	@BeforeEach 
	void setUp() {
		
	}
	
	@Test
	void testCreationPhotoJPEG(){
		photoJPEG = new Photo("Bla", PhotoType.JPEG); 
		photoJPG  = new Photo("Bla", PhotoType.JPG);
		photoPNG  = new Photo("Bla", PhotoType.PNG); 
		assertAll(
				()-> assertEquals(photoJPEG.getName(), "Bla"),
				()-> assertEquals(photoJPG.getPhotoType(), "jpg"),
				()-> assertEquals(photoPNG.getPhotoType(), "png"),
				()-> assertEquals(photoJPEG.getPhotoType(), "jpeg")
				);
	}
}
