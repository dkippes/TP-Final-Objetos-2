package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Es un filtro de conjuncion.
 */
public class AndFilter extends BinaryFilter {

	public List<Sample> searchSamples(List<Sample> allSamples) {
		List<Sample> filtredSamples = filter1.searchSamples(allSamples);
		return filter2.searchSamples(filtredSamples);
	}
}
