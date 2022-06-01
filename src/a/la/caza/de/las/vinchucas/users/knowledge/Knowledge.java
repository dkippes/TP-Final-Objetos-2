package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

public abstract class Knowledge implements IKnowledgeState, KnowledgeStatusUpdate {
	public void checkIfKnowledgeCanBeUpdated(User user, boolean condition, Knowledge knowledge) {
		if (condition) {
			user.setKnowledge(knowledge);
		}
	}
}
