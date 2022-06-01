package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

public class OrFilter implements IFilter {
	private IFilter filter1;
	private IFilter filter2;

	public void setFilters(IFilter filter1, IFilter filter2) {
		this.filter1 = filter1;
		this.filter2 = filter2;
	}

	public List<Sample> searchSamples(List<Sample> allSamples) {
		List<Sample> filtredSamples1 = filter1.searchSamples(allSamples);
		List<Sample> filtredSamples2 = filter2.searchSamples(allSamples);
		filtredSamples1.removeAll(filtredSamples2);
		filtredSamples1.addAll(filtredSamples2);
		return filtredSamples1;
	}
}
