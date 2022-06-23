package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.users.User;

/**
 * Esta clase abstracta describe si el conocimiento de un usuario.
 */
public abstract class Knowledge implements IKnowledgeState {
	
	/**
	 * Se fija si el conocimiento de un usuario puede ser actualizado segun la condicion pasada
	 * @param User, boolean, Knowledge
	 */
	public void checkIfKnowledgeCanBeUpdated(User user, boolean condition, Knowledge knowledge) {
		if (condition) {
			user.setKnowledge(knowledge);
		}
	}
}
