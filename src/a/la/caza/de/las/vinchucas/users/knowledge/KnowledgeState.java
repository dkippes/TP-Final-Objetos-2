package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

/**
 * Protocolo de estado de conocimiento usuario.
 */
public interface KnowledgeState {
	public boolean isUserBasic();

	public boolean isUserExpert();

	public void checkStatusUser(User user);
}
