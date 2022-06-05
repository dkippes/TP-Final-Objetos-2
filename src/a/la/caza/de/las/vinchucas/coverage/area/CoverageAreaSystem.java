package a.la.caza.de.las.vinchucas.coverage.area;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.WebApplication;

public class CoverageAreaSystem {
	private Set<CoverageArea> registredCoverageAreas;
	
	public CoverageAreaSystem() {
		registredCoverageAreas = new HashSet<>();
	}

	public void registerCoverageArea(CoverageArea coverageArea) {
		registredCoverageAreas.add(coverageArea);
	}

	public Set<CoverageArea> getRegistredCoverageAreas() {
		// TODO Auto-generated method stub
		return registredCoverageAreas;
	}


	public Set<CoverageArea> getOverlappingAreas(CoverageArea coverageArea) {
		// TODO Auto-generated method stub
		Set<CoverageArea> areas =
		registredCoverageAreas.stream()
				.filter(c -> c.coverageAreasAreOverlapped(coverageArea))
				.collect(Collectors.toSet());
		areas.remove(coverageArea);
		return areas;
		
	}

}
