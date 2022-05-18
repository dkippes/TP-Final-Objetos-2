package a.la.caza.de.las.vinchucas.samples.verification.level;

public enum Vote {
	VOTED("Voted"),
	VERIFIED("Verified");
	
	Vote(String vote) {
		this.vote = vote;
	}

	private final String vote;
	
	public String getVote(){
        return vote;
    }
}
