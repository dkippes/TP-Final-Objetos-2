package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

/**
 * Interfaz KnowledgeStatusUpdate 
 * 
 * Esta Interfaz se encarga de chequear si el conocimiento de cada usuario se puede actualizar.
 *
 */

public interface KnowledgeStatusUpdate {
	public void checkIfKnowledgeCanBeUpdated(User user, boolean condition, Knowledge knowledge);
}
