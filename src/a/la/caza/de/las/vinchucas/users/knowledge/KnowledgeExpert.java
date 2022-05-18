package a.la.caza.de.las.vinchucas.users.knowledge;

public class KnowledgeExpert  implements Knowledge {

	@Override
	public boolean isUserBasic() {
		return false;
	}

	@Override
	public boolean isUserExpert() {
		return true;
	}

}
