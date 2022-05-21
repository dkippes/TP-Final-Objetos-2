package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.users.User;

public abstract class Knowledge {	
	public abstract boolean isUserBasic();
	public abstract boolean isUserExpert();
	public abstract void checkStatusUser(User user);
	
	public void checkIfKnowledgeCanBeUpdated(User user, boolean condition, Knowledge knowledge) {
		if(condition) {
			user.setKnowledge(knowledge);
		}
	}
}
