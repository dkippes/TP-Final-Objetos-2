package a.la.caza.de.las.vinchucas.samples;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.users.User;

public class Photo {
	private String name;
	private PhotoType photoType;
	
	public Photo(String name, PhotoType photoType) {
		this.name = name;
		this.photoType = photoType;
	}
}
