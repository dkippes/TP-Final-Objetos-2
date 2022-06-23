package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Es un filtro de conjuncion.
 */
public class AndFilter extends BinaryFilter {

	public AndFilter(IFilter filter1, IFilter filter2) {
		super(filter1, filter2);
	}

	public List<Sample> searchSamples(List<Sample> allSamples) {
		List<Sample> filtredSamples = filter1.searchSamples(allSamples);
		return filter2.searchSamples(filtredSamples);
	}
}
