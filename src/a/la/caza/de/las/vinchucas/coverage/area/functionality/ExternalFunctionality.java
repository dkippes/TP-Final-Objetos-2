package a.la.caza.de.las.vinchucas.coverage.area.functionality;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.location.organization.Organization;
import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Interfaz ExternalFuncionality
 * 
 * Esta interfaz es una especie de plugin para ser utilizada por cada una de la organizaciones no gubernamentales.
 *
 */

public interface ExternalFunctionality {
	public void newEvent(Organization organization, CoverageArea coverageArea, Sample sample);
}
