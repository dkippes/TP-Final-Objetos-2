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
	private Set<OrganizationObserver> organizationObservers;
	private Set<Sample> samples;

	public CoverageArea(String name, Location epicenter, float radio) {
		this.name = name;
		this.epicenter = epicenter;
		this.radio = radio;
		this.organizationObservers = new HashSet<>();
		this.samples = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public Set<OrganizationObserver> getOrganizationObservers() {
		return organizationObservers;
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

	public void addOrganizationObserver(OrganizationObserver organizationObserver) {
		organizationObservers.add(organizationObserver);
	}

	public void removeOrganizationObserver(OrganizationObserver organizationObserver) {
		organizationObservers.remove(organizationObserver);
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
		organizationObservers.forEach(observer -> observer.validateSample(this, sample));
	}

	private void notifyNewSampleAdd(Sample sample) {
		organizationObservers.forEach(observer -> observer.uploadNewSample(this, sample));
	}

	private boolean belongsToCoverageArea(Sample sample) {
		return epicenter.distanceBetweenTwoLocations(sample.getLocation()) <= radio;
	}

}
