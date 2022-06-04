package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

/**
 * Protocolo de estado de conocimiento del usuario
 */
public interface KnowledgeStatusUpdate {
	public void checkIfKnowledgeCanBeUpdated(User user, boolean condition, Knowledge knowledge);
}
