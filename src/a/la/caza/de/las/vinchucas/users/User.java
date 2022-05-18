package a.la.caza.de.las.vinchucas.users;

import java.util.ArrayList;
import java.util.List;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;


public class User {
	private int id;
	private String name;
	private List<Sample> samplesSend;
	private WebApplication webApplication;
	private static int counter;
	
	public User(String name, WebApplication webApplication) {
		this.id= counter++;
		this.name = name;
		this.samplesSend = new ArrayList<>();
		//this.setKnowledge(new KnowledgeBasic());
		this.webApplication = webApplication;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return this.name;
	}
	
	
	public void sendSample(Sample sample) {
		samplesSend.add(sample);
		this.webApplication.registerSample(sample);
	}
	
	public void opineSample(Sample sample, Opinion opinion) {
		sample.addUserOpinion(opinion, this);
	}

	public List<Sample> getSamplesSend() {
		return samplesSend;
	}
}