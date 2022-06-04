package a.la.caza.de.las.vinchucas.coverage.area;

import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Interfaz OrganizationObserver
 * 
 * Esta interfaz se encarga de subir y validar muestras de organización no gubernamental.
 *
 */

public interface OrganizationObserver {
	public void uploadNewSample(CoverageArea coverageArea, Sample sample);

	public void validateSample(CoverageArea coverageArea, Sample sample);
}
