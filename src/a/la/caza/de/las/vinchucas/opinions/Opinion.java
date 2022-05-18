package a.la.caza.de.las.vinchucas.opinions;

import java.time.LocalDate;

import a.la.caza.de.las.vinchucas.users.User;

public class Opinion {
	private OpinionType opinionType;
	private LocalDate dateOfIssue;
	
	public Opinion(OpinionType opinionType) {
		this.opinionType = opinionType;
		this.dateOfIssue = LocalDate.now();
	}
	
	public LocalDate getDateOfIssue() {
		return dateOfIssue;
	}

	public String getOpinionType() {
		return opinionType.getOpinionType();
	}
}