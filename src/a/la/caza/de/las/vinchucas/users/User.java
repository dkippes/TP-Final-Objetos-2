package a.la.caza.de.las.vinchucas.users;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeState;

/**
 * Describe un usuario
 */
public class User implements Cloneable {
	private String name;
	private KnowledgeState knowledge;
	private WebApplication webApplication;

	public User(String name, KnowledgeState knowledge, WebApplication webApplication) {
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

	public void setKnowledge(KnowledgeState knowledge) {
		this.knowledge = knowledge;
	}

	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}

	public KnowledgeState getKnowledge() {
		this.knowledge.checkStatusUser(this);
		return knowledge;
	}

	public void checkIfCanVote() throws UserIsNotExpertException {
		this.knowledge.canVote(this);
	}
}