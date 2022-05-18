package a.la.caza.de.las.vinchucas.samples;

public enum PhotoType {
	JPG("jpg"),
	PNG("png"),
	JPEG("jpeg");
	
	PhotoType(String photoType) {
		this.photoType = photoType;
	}

	private final String photoType;
	
	public String getPhotoType(){
        return photoType;
    }
}
