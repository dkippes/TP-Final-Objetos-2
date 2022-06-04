package a.la.caza.de.las.vinchucas.samples;

/**
 * Describe una foto con su nombre y extension
 */
public class Photo {
	private String name;
	private PhotoType photoType;

	public Photo(String name, PhotoType photoType) {
		this.name = name;
		this.photoType = photoType;
	}

	public String getName() {
		return name;
	}

	public PhotoType getPhotoType() {
		return photoType;
	}
}
