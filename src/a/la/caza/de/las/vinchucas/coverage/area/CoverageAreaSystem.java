package a.la.caza.de.las.vinchucas.coverage.area;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Describe la informacion del sistema de areas de covertura
 */

public class CoverageAreaSystem {
	
	private Set<CoverageArea> registredCoverageAreas;
	
	public CoverageAreaSystem() {
		registredCoverageAreas = new HashSet<>();
	}

	public Set<CoverageArea> getRegistredCoverageAreas() {
		// TODO Auto-generated method stub
		return registredCoverageAreas;
	}
	
	/**
	 * Registra un area de cobertura
	 * @param CoverageArea
	 */
	public void registerCoverageArea(CoverageArea coverageArea) {
		registredCoverageAreas.add(coverageArea);
	}
	
	/**
	 * Dada un area de cobertura, retorna las areas de cobertura que la solapan
	 * @param CoverageArea
	 * @return Set<CoverageArea>
	 */

	public Set<CoverageArea> getOverlappingAreas(CoverageArea coverageArea) {
		// TODO Auto-generated method stub
		Set<CoverageArea> areas =
		registredCoverageAreas.stream()
				.filter(c -> c.coverageAreasAreOverlapped(coverageArea))
				.collect(Collectors.toSet());
		areas.remove(coverageArea);
		return areas;
		
	}
	
	/**
	 * Registra una muestra en el area de cobertura
	 * @param Sample
	 */

	public void registerSampleInCoverageArea(Sample sample) {
		// TODO Auto-generated method stub
		registredCoverageAreas.stream()
		.filter(c -> c.belongsToCoverageArea(sample))
		.forEach(c -> c.addNewSample(sample));
	}
	
	


}
