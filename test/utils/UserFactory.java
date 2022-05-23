package utils;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.users.User;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class UserFactory implements Factory<User> {

	@Override
	public User create() {
		User user = new User("Diego", new KnowledgeBasic(), WebApplication.createApp());
		return user;
	}

}
