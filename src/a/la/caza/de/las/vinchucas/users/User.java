package a.la.caza.de.las.vinchucas.users;

import java.util.ArrayList;
import java.util.List;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.knowledge.Knowledge;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class User implements Cloneable {
	private int id;
	private String name;
	private Knowledge knowledge;
	private static int counter;
	private WebApplication webApplication;

	public User(String name, Knowledge knowledge, WebApplication webApplication) {
		this.id = counter++;
		this.name = name;
		this.knowledge = knowledge;
		this.webApplication = webApplication;
		this.knowledge.checkStatusUser(this);
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return id;
	}

	public WebApplication getWebApplication() {
		return webApplication;
	}

	public void setWebApplication(WebApplication webApplication) {
		this.webApplication = webApplication;
	}

	public void sendSample(Sample sample) {
		webApplication.registerSample(sample);
		this.knowledge.checkStatusUser(this);
	}

	public void opineSample(Sample sample, Opinion opinion) throws Exception {
		sample.addOpinion(opinion);
		this.knowledge.checkStatusUser(this);
	}

	public boolean hasBasicKnowledge() {
		return this.knowledge.isUserBasic();
	}

	public boolean hasExpertKnowledge() {
		return this.knowledge.isUserExpert();
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}

	public Knowledge getKnowledge() {
		return this.knowledge;
	}
	
	public void updateKnowledgeBaseOnCondition() {
		this.knowledge.checkStatusUser(this);
	}
	
	
}