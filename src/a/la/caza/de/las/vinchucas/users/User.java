package a.la.caza.de.las.vinchucas.users;

import java.util.Objects;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.knowledge.IKnowledgeState;

/**
 * Describe un usuario
 */
public class User implements Cloneable {
	private String name;
	private IKnowledgeState knowledge;
	private WebApplication webApplication;

	public User(String name, IKnowledgeState knowledge, WebApplication webApplication) {
		this.name = name;
		this.knowledge = knowledge;
		this.webApplication = webApplication;
		this.knowledge.checkStatusUser(this);
	}

	public String getName() {
		return this.name;
	}

	public WebApplication getWebApplication() {
		return webApplication;
	}

	public void sendSample(Sample sample) {
		this.webApplication.registerSample(sample);
		this.knowledge.checkStatusUser(this);
	}

	public void opineSample(Sample sample, Opinion opinion) throws UserValidationException {
		sample.addOpinion(opinion);
		this.knowledge.checkStatusUser(this);
	}

	public boolean hasBasicKnowledge() {
		return this.knowledge.isUserBasic();
	}

	public boolean hasExpertKnowledge() {
		return this.knowledge.isUserExpert();
	}

	public void setKnowledge(IKnowledgeState knowledge) {
		this.knowledge = knowledge;
	}

	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}

	public void getKnowledge() {
		this.knowledge.checkStatusUser(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(knowledge, other.knowledge) && Objects.equals(name, other.name)
				&& Objects.equals(webApplication, other.webApplication);
	}
}