package a.la.caza.de.las.vinchucas.coverage.area.functionality;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.location.ngo.Ngo;
import a.la.caza.de.las.vinchucas.samples.Sample;

public interface ExternalFunctionality {
	public void newEvent(Ngo ngo, CoverageArea coverageArea, Sample sample);
}
