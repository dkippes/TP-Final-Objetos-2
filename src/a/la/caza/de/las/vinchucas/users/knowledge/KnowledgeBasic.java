package a.la.caza.de.las.vinchucas.users.knowledge;

public class KnowledgeBasic implements Knowledge {

	@Override
	public boolean isUserBasic() {
		return true;
	}

	@Override
	public boolean isUserExpert() {
		return false;
	}

}
