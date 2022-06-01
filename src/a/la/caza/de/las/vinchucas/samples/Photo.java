package a.la.caza.de.las.vinchucas.samples;

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

	public String getPhotoType() {
		return photoType.getPhotoType();
	}
}
