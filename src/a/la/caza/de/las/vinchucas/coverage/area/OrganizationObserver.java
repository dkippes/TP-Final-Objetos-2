package a.la.caza.de.las.vinchucas.coverage.area;

import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Se encarga de subir y validar muestras de organizacion no gubernamental.
 */
public interface OrganizationObserver {
	public void uploadNewSample(CoverageArea coverageArea, Sample sample);

	public void validateSample(CoverageArea coverageArea, Sample sample);
}
