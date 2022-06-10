package a.la.caza.de.las.vinchucas.search;

import java.util.List;
import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Es un filtro de diyuncion
 */
public class OrFilter extends BinaryFilter {

	public List<Sample> searchSamples(List<Sample> allSamples) {
		List<Sample> filtredSamples1 = filter1.searchSamples(allSamples);
		List<Sample> filtredSamples2 = filter2.searchSamples(allSamples);
		filtredSamples1.removeAll(filtredSamples2);
		filtredSamples1.addAll(filtredSamples2);
		return filtredSamples1;
	}
}
