package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.users.User;

/**
 * Clase KnowledgeExpert 
 * 
 * Esta clase describe el conocimiento experto de cada usuario.
 *
 */

public class KnowledgeExpert extends Knowledge {

	@Override
	public boolean isUserBasic() {
		return false;
	}

	@Override
	public boolean isUserExpert() {
		return true;
	}

	@Override
	public void checkStatusUser(User user) {
		WebApplication webApplication = user.getWebApplication();
		long samples = webApplication.manySamplesSendByUserBeforeAnyDays(user, 30);
		long opinions = webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30);
		super.checkIfKnowledgeCanBeUpdated(user, samples < 10 || opinions < 20, new KnowledgeBasic());
	}
}
