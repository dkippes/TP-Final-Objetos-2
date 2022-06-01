package a.la.caza.de.las.vinchucas.location.ngo;

import a.la.caza.de.las.vinchucas.location.Location;

public class Ngo {
	private Location location;
	private NgoType ngoType;
	private int workingPeople;
	
	public Ngo(Location location, NgoType ngoType, int workingPeople) {
		this.location = location;
		this.ngoType = ngoType;
		this.workingPeople = workingPeople;
	}

	public Location getLocation() {
		return location;
	}

	public NgoType getNgoType() {
		return ngoType;
	}

	public int getWorkingPeople() {
		return workingPeople;
	}

	public String getNgoTypeString() {
		return ngoType.getNgoType();
	}
}
