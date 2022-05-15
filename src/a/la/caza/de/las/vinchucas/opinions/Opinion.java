package a.la.caza.de.las.vinchucas.opinions;

import a.la.caza.de.las.vinchucas.users.User;

public abstract class Opinion {
	private User user;
	
	public Opinion (User user) {
		this.user= user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}