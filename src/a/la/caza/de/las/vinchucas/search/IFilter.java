package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Protocolo de filtrado
 */
public interface IFilter {
	public List<Sample> searchSamples(List<Sample> samples);
}
