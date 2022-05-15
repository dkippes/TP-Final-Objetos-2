package a.la.caza.de.las.vinchucas.samples;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.users.User;

public class Sample {
	private Photo photo;

	public Sample(Location location, Photo photo, User user) {
		// TODO Auto-generated constructor stub
	}

	public boolean wasSendByTheUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isVerify() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addOpinionInPhoto(Opinion opinion, User user) {
		opinion.setUser(user);
		photo.addOpinion(opinion);
		// TODO Auto-generated method stub
	}

	public boolean wasOpineByTheUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
}
