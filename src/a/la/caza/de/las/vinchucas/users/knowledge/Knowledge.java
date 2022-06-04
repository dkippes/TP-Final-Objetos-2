package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

/**
 * Clase abstracta Knowledge
 * 
 * Esta clase abstracta describe si el conocimiento de cada usuario se puede actualizar.
 *
 */

public abstract class Knowledge implements IKnowledgeState, KnowledgeStatusUpdate {
	public void checkIfKnowledgeCanBeUpdated(User user, boolean condition, Knowledge knowledge) {
		if (condition) {
			user.setKnowledge(knowledge);
		}
	}
}
