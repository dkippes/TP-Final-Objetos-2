package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

public interface BinaryFilter extends IFilter{
	
	public void setFilters(IFilter filter1, IFilter filter2);
	
	public List<Sample> searchSamples(List<Sample> allSamples);

}
