package a.la.caza.de.las.vinchucas.location.ngo;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.NgoObserver;
import a.la.caza.de.las.vinchucas.coverage.area.functionality.ExternalFunctionality;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

public class Ngo implements NgoObserver {
	private Location location;
	private NgoType ngoType;
	private int workingPeople;
	private ExternalFunctionality uploadSampleFunctionality;
	private ExternalFunctionality validateSampleFunctionality;

	public Ngo(Location location, NgoType ngoType, int workingPeople) {
		this.location = location;
		this.ngoType = ngoType;
		this.workingPeople = workingPeople;
	}

	public Location getLocation() {
		return location;
	}

	public NgoType getNgoType() {
		return ngoType;
	}

	public int getWorkingPeople() {
		return workingPeople;
	}

	public String getNgoTypeString() {
		return ngoType.getNgoType();
	}

	public void setUploadSampleFunctionality(ExternalFunctionality uploadSampleFunctionality) {
		this.uploadSampleFunctionality = uploadSampleFunctionality;
	}

	public void setValidateSampleFunctionality(ExternalFunctionality validateSampleFunctionality) {
		this.validateSampleFunctionality = validateSampleFunctionality;
	}

	public void uploadNewSample(CoverageArea coverageArea, Sample sample) {
		uploadSampleFunctionality.newEvent(this, coverageArea, sample);
	}

	public void validateSample(CoverageArea coverageArea, Sample sample) {
		validateSampleFunctionality.newEvent(this, coverageArea, sample);
	}
}
