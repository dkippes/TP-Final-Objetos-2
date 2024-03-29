package a.la.caza.de.las.vinchucas.samples;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhotoTest {

	private Photo photoJPG, photoJPEG, photoPNG;

	@BeforeEach
	void setUp() {
		photoJPEG = new Photo("Bla", PhotoType.JPEG);
		photoJPG = new Photo("Bla", PhotoType.JPG);
		photoPNG = new Photo("Bla", PhotoType.PNG);
	}

	@Test
	void testCreationPhotoJPEG() {
		assertAll(() -> assertEquals(photoJPEG.getName(), "Bla"), 
				() -> assertEquals(photoJPG.getPhotoType(), PhotoType.JPG),
				() -> assertEquals(photoPNG.getPhotoType(), PhotoType.PNG),
				() -> assertEquals(photoJPEG.getPhotoType(), PhotoType.JPEG));
	}
}
