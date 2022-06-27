package a.la.caza.de.las.vinchucas.samples;

import java.time.LocalDate;
import java.util.List;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.state.BasicVotedSampleState;
import a.la.caza.de.las.vinchucas.samples.state.ISampleState;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

/**
 * Describe la informacion de una muestra con su estado inicial
 */
public class Sample {
	private User user;
	private Photo photo;
	private Location location;
	private List<Opinion> opinionHistory;
	private LocalDate creationDate;
	private ISampleState state;

	public Sample(Location location, Photo photo, Opinion opinion, List<Opinion> opinionHistory) throws UserValidationException {
		this.location = location;
		this.photo = photo;
		this.user = opinion.getUser();
		this.creationDate = LocalDate.now();
		this.opinionHistory = opinionHistory;
		this.state = new BasicVotedSampleState();
		this.addOpinion(opinion);
	}

	/**
	 * Anade la opinion de un usuario segun el estado de la muestra
	 * 
	 * @exception UserValidationException
	 * @throws UserValidationException
	 * @param Opinion
	 */
	public void addOpinion(Opinion opinion) throws UserValidationException {
		this.state.addOpinion(this, opinion);
	}

	/**
	 * Anade la opion de un usuario al historial directamente, se debe usar addOpion
	 * si se quiere agregar segun el estado de la muestra
	 * 
	 * @param Opinion
	 * @return LocalDate
	 */
	public void addUserOpinion(Opinion opinion) {
		this.opinionHistory.add(opinion);
	}

	public List<Opinion> getOpinionHistory() {
		return opinionHistory;
	}

	/**
	 * Obtiene la ultima votacion
	 * 
	 * @return LocalDate
	 */
	public LocalDate getLastVotation() {
		return opinionHistory.get(opinionHistory.size() - 1).getDateOfIssue();
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public Vote getLevelVerification() {
		return this.state.getLevelVerification();
	}

	public void setState(ISampleState state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public Location getLocation() {
		return location;
	}

	public Photo getPhoto() {
		return photo;
	}

	/**
	 * Indica si el usuario paso por parametro ya voto previamente
	 * 
	 * @param User
	 * @return boolean
	 */
	public boolean userAlreadyVote(User user) {
		return opinionHistory.stream().anyMatch(userOpinion -> userOpinion.getUser().equals(user));
	}

	/**
	 * Obtiene el resultado de una muestra, esta puede la opinion mas vota o en su
	 * defecto si hay empate devuelve indefinida
	 * 
	 * @return GenericOpinionType
	 */
	public GenericOpinionType getActualResult() {
		return this.state.getResult(this);
	}

	public boolean expertUserHasTheSameOpinion(Opinion opinion) {
		return opinionHistory.stream()
				.filter(u -> u.getUser().hasExpertKnowledge())
				.anyMatch(o -> o.getOpinionType().equals(opinion.getOpinionType()));
	}
}
