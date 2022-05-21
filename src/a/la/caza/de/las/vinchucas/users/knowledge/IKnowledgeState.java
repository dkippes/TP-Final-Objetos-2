package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

public interface IKnowledgeState {
	public boolean isUserBasic();
	public boolean isUserExpert();
	public void checkStatusUser(User user);
}
