package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

/**
 * Interfaz IKnowledgeState
 * 
 * Esta Interfaz describe estado de conocimiento de cada usuario.
 */


public interface IKnowledgeState {
	public boolean isUserBasic();

	public boolean isUserExpert();

	public void checkStatusUser(User user);
}
