package a.la.caza.de.las.vinchucas.users.knowledge;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.users.User;

/**
 * Esta clase describe el conocimiento basico de usuario.
 */
public class KnowledgeBasic extends Knowledge {

	@Override
	public boolean isUserBasic() {
		return true;
	}

	@Override
	public boolean isUserExpert() {
		return false;
	}

	@Override
	public void checkStatusUser(User user) {
		WebApplication webApplication = user.getWebApplication();
		long samples = webApplication.manySamplesSendByUserBeforeAnyDays(user, 30);
		long opinions = webApplication.manyOpinionMadeByUserBeforeAnyDays(user, 30);
		checkIfKnowledgeCanBeUpdated(user, samples >= 10 && opinions >= 20, new KnowledgeExpert());
	}

	@Override
	public void canVote(User user) throws UserIsNotExpertException {
		throw new UserIsNotExpertException("User " + user.getName() + " can not vote because is not expert");
	}
}
