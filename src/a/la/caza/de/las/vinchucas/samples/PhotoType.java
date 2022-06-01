package a.la.caza.de.las.vinchucas.samples;

public enum PhotoType {
	JPG("jpg"), PNG("png"), JPEG("jpeg");

	private final String photoType;

	PhotoType(String photoType) {
		this.photoType = photoType;
	}

	public String getPhotoType() {
		return photoType;
	}
}
