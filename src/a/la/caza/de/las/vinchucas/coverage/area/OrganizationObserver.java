package a.la.caza.de.las.vinchucas.coverage.area;

import a.la.caza.de.las.vinchucas.samples.Sample;

public interface OrganizationObserver {
	public void uploadNewSample(CoverageArea coverageArea, Sample sample);

	public void validateSample(CoverageArea coverageArea, Sample sample);
}
