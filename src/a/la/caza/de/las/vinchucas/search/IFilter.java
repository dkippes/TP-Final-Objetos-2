package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

/*
 * Interfaz IFilter 
 * 
 * Esta Interfaz se encarga de filtrar muestras.
 * 
 */

public interface IFilter {
	public List<Sample> searchSamples(List<Sample> samples);
}
