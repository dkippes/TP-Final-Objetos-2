package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

public interface KnowledgeStatusUpdate {
	public void checkIfKnowledgeCanBeUpdated(User user, boolean condition, Knowledge knowledge);
}
