package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

public abstract class Knowledge {
	private User user;
	private int sampleSends;
	private int revisions;
	
	public abstract void expert();
	public abstract void basic();
	public abstract void specialist();
	public abstract void setUser(User user);
	
	public void resetState(User user) {
		
	}
}
