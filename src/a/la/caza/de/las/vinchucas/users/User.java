package a.la.caza.de.las.vinchucas.users;

import java.util.ArrayList;
import java.util.List;
import a.la.caza.de.las.vinchucas.exceptions.SampleCanNotBeOpine;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Location;
import a.la.caza.de.las.vinchucas.samples.Photo;
import a.la.caza.de.las.vinchucas.samples.Sample;

public class User {
	private String name;
	private List<Sample> samples;
	
	public User(String name) {
		this.name = name;
		this.samples = new ArrayList<>();
	}
	public String getName() {
		return this.name;
	}
	
	public void sendSample(Location location, Photo photo) {
		samples.add(new Sample(location, photo, this));
	}
	
	public void opineSample(Sample sample, Opinion opinion) throws SampleCanNotBeOpine {
		// TODO: Agregar verificacion cuando un usuario expert haya votado
		if(sample.wasSendByTheUser(this) || sample.isVerify()) {
			throw new SampleCanNotBeOpine("Sample can not be opine by the user");
		}
		sample.addOpinionInPhoto(opinion);
	}

	public List<Sample> getSamples() {
		return samples;
	}
}