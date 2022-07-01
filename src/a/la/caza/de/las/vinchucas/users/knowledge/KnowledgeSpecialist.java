package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.users.User;

/**
 * Esta clase describe el conocimiento especialista de usuario.
 * No puede cambiar, ni actualizar su conocimiento al ser especialista
 */
public class KnowledgeSpecialist extends KnowledgeExpert {

	/**
	 * No tiene comportamiento ya que un especialista no puede actualizarse
	 * @param User
	 */
	@Override
	public void checkStatusUser(User user) {
	}
}
