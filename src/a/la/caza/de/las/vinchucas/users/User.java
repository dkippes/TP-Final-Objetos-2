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


public class User {
	private int id;
	private String name;
	private WebApplication webApplication;
	private Knowledge knowledge;
	private static int counter;
	
	public User(String name, WebApplication webApplication) {
		this.id= counter++;
		this.name = name;
		this.webApplication = webApplication;
		this.knowledge = new KnowledgeBasic();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return id;
	}
	
	public void sendSample(Sample sample) {
		this.webApplication.registerSample(sample);
	}
	
	public void opineSample(Sample sample, Opinion opinion) throws Exception {
		sample.addOpinion(opinion);
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

	public boolean isExpert() {
		return false;
	}
	
}