package a.la.caza.de.las.vinchucas.coverage.area;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

public class CoverageArea {
	private String name;
	private Location epicenter;
	private float radio;
	private Set<NgoObserver> ngoObservers;
	private Set<Sample> samples;

	public CoverageArea(String name, Location epicenter, float radio) {
		this.name = name;
		this.epicenter = epicenter;
		this.radio = radio;
		this.ngoObservers = new HashSet<>();
		this.samples = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public Set<NgoObserver> getNgoObservers() {
		return ngoObservers;
	}

	public Set<Sample> getSamples() {
		return samples;
	}

	public Location getEpicenter() {
		return epicenter;
	}

	public float getRadio() {
		return radio;
	}

	public List<CoverageArea> overlappingAreas() {
		return null;
	}

	public List<Sample> samplesInCoverageArea() {
		return null;
	}

	public void addNgoObserver(NgoObserver ngoObserver) {
		ngoObservers.add(ngoObserver);
	}

	public void removeNgoObserver(NgoObserver ngoObserver) {
		ngoObservers.remove(ngoObserver);
	}

	public void addNewSample(Sample sample) {
		if (belongsToCoverageArea(sample)) {
			samples.add(sample);
			notifyNewSampleAdd(sample);
		}
	}

	public void addVerifySample(Sample sample) {
		notifyVerifySample(sample);
	}

	private void notifyVerifySample(Sample sample) {
		ngoObservers.forEach(observer -> observer.validateSample(this, sample));
	}

	private void notifyNewSampleAdd(Sample sample) {
		ngoObservers.forEach(observer -> observer.uploadNewSample(this, sample));
	}

	private boolean belongsToCoverageArea(Sample sample) {
		return epicenter.distanceBetweenTwoLocations(sample.getLocation()) <= radio;
	}

}
