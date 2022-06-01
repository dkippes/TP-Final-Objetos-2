package a.la.caza.de.las.vinchucas.location.ngo;

public enum NgoType {
	HEALTH("Health"), 
	EDUCATION("Education"), 
	CULTURAL("Cultural"), 
	ASSISTANCE("Assistance");

	NgoType(String ngoType) {
		this.ngoType = ngoType;
	}

	private final String ngoType;

	public String getNgoType() {
		return ngoType;
	}
}
