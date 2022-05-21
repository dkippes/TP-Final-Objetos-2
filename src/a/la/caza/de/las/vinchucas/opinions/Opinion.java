package a.la.caza.de.las.vinchucas.opinions;

import java.time.LocalDate;

import a.la.caza.de.las.vinchucas.WebApplication;
import a.la.caza.de.las.vinchucas.users.User;

public class Opinion {
	private User user;
	private OpinionType opinionType;
	private LocalDate dateOfIssue;

	public Opinion(OpinionType opinionType, User user) throws CloneNotSupportedException {
		this.user = user.clone();
		this.opinionType = opinionType;
		this.dateOfIssue = LocalDate.now();
	}

	public LocalDate getDateOfIssue() {
		return dateOfIssue;
	}

	public String getOpinionType() {
		return opinionType.getOpinionType();
	}

	public User getUser() {
		return user;
	}
}