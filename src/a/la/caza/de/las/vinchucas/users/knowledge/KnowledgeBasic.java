package a.la.caza.de.las.vinchucas.users.knowledge;

import java.time.LocalDate;
import java.util.List;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.User;
import net.bytebuddy.asm.Advice.Local;

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

	

}
