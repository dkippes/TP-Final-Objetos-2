package a.la.caza.de.las.vinchucas.opinions;

public enum OpinionType {
	NOTHING("Nothing"),
	IMAGE_UNCLEAR("Image Unclear"),
	VINCHUCA_INFESTANS("Vinchuca Infestans"),
	VINCHUCA_SORDIDA("Vinchuca Sordida"),
	VINCHUCA_GUASAYANA("Vinchuca Guasayana"),
	CHINCHE_PHTIA("Chinche Phtia"),
	CHINCHE_FOLIADA("Chinche Foliada");
	
	OpinionType(String opinionType) {
		this.opinionType = opinionType;
	}

	private final String opinionType;
	
	public String getOpinionType(){
        return opinionType;
    }
	
	public String getUndefinedOpinion() {
		return "UNDEFINED";
	}
}
