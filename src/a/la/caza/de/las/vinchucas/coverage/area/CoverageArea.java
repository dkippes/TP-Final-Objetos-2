package a.la.caza.de.las.vinchucas.coverage.area;

import java.util.List;

import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

public class CoverageArea {
	
	private String name;
	private Location epicenter;
	private float distance;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Location getEpicenter() {
		return epicenter;
	}

	public void setEpicenter(Location epicenter) {
		this.epicenter = epicenter;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public List <CoverageArea> overlappingAreas() {
		return null;
	}
	
	public List<Sample> samplesInCoverageArea(){
		return null;
	}
}
