package a.la.caza.de.las.vinchucas.coverage.area.functionality;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.location.organization.Organization;
import a.la.caza.de.las.vinchucas.samples.Sample;

public interface ExternalFunctionality {
	public void newEvent(Organization organization, CoverageArea coverageArea, Sample sample);
}
