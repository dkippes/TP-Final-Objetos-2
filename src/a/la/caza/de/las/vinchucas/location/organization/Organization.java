package a.la.caza.de.las.vinchucas.location.organization;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.OrganizationObserver;
import a.la.caza.de.las.vinchucas.coverage.area.functionality.ExternalFunctionality;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

public class Organization implements OrganizationObserver {
/*
 * Clase Organization.
 * 
 * Describe la infromación de cada organización no gubernamental.
 *
 */
	private Location location;
	private OrganizationType organizationType;
	private int workingPeople;
	private ExternalFunctionality uploadSampleFunctionality;
	private ExternalFunctionality validateSampleFunctionality;

	public Organization(Location location, OrganizationType organizationType, int workingPeople) {
		this.location = location;
		this.organizationType = organizationType;
		this.workingPeople = workingPeople;
	}

	public Location getLocation() {
		return location;
	}

	public OrganizationType getOrganizationType() {
		return organizationType;
	}

	public int getWorkingPeople() {
		return workingPeople;
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
