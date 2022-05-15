package a.la.caza.de.las.vinchucas.users;

import java.util.ArrayList;
import java.util.List;
import a.la.caza.de.las.vinchucas.exceptions.SampleCanNotBeOpine;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.knowledge.Knowledge;
import a.la.caza.de.las.vinchucas.users.knowledge.KnowledgeBasic;

public class User {
	private String name;
	private Knowledge knowledge;
	private List<Sample> samples;
	
	public User(String name) {
		this.name = name;
		this.samples = new ArrayList<>();
		this.setKnowledge(new KnowledgeBasic());
	}
	public String getName() {
		return this.name;
	}
	
	private void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
		this.knowledge.setUser(this);
	}

	public void sendSample(Location location, Photo photo) {
		samples.add(new Sample(location, photo, this));
	}
	
	public void opineSample(Sample sample, Opinion opinion) throws SampleCanNotBeOpine {
		// TODO: Agregar verificacion cuando un usuario expert haya votado
		if(sample.wasSendByTheUser(this) || sample.isVerify() || sample.wasOpineByTheUser(this)) {
			throw new SampleCanNotBeOpine("Sample can not be opine by the user");
		}
		this.knowledge.basic();	
		sample.addOpinionInPhoto(opinion, this);
	}

	public List<Sample> getSamples() {
		return samples;
	}
}