package a.la.caza.de.las.vinchucas.opinions;

import java.time.LocalDate;

import a.la.caza.de.las.vinchucas.users.User;

public class Opinion {
	private User user;
	private OpinionType opinionType;
	private LocalDate dateOfIssue;

	public Opinion(OpinionType opinionType, User user) throws CloneNotSupportedException {
		cloneUser(user);
		this.opinionType = opinionType;
		this.dateOfIssue = LocalDate.now();
	}

	public LocalDate getDateOfIssue() {
		return dateOfIssue;
	}

	public String getOpinionTypeString() {
		return opinionType.getOpinionType();
	}

	public OpinionType getOpinionType() {
		return opinionType;
	}

	public User getUser() {
		return user;
	}

	private void cloneUser(User user) throws CloneNotSupportedException {
		this.user = user.clone();
	}
}