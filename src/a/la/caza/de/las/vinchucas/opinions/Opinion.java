package a.la.caza.de.las.vinchucas.opinions;

import java.time.LocalDate;

import a.la.caza.de.las.vinchucas.users.User;

/**
 * Describe la informacion de cada opinion.
 */
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

	public OpinionType getOpinionType() {
		return opinionType;
	}

	public User getUser() {
		return user;
	}

	/**
	 * Clona el usuario ya que queremos hacer un snapshot con la informacion del momento
	 * del usuario. Ya que si cambia a futuro cambiaria en la opinion
	 * @param User
	 */
	private void cloneUser(User user) throws CloneNotSupportedException {
		this.user = user.clone();
	}
}