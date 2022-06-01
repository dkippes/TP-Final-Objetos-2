package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class AndFilter implements IFilter {
	private IFilter filter1;
	private IFilter filter2;

	public void setFilters(IFilter filter1, IFilter filter2) {
		this.filter1 = filter1;
		this.filter2 = filter2;
	}

	public List<Sample> searchSamples(List<Sample> allSamples) {
		List<Sample> filtredSamples = filter1.searchSamples(allSamples);
		return filter2.searchSamples(filtredSamples);
	}
}