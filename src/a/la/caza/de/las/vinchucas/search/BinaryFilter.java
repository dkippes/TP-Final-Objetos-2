package a.la.caza.de.las.vinchucas.search;

import java.util.List;

import a.la.caza.de.las.vinchucas.samples.Sample;

public abstract class BinaryFilter implements IFilter {
		protected IFilter filter1;
		protected IFilter filter2;
		
		public BinaryFilter(IFilter filter1, IFilter filter2) {
			this.filter1 = filter1;
			this.filter2 = filter2;
		}
		
		abstract public List<Sample> searchSamples(List<Sample> allSamples);
	
}
