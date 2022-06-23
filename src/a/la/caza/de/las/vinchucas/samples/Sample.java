package a.la.caza.de.las.vinchucas.samples;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.opinions.UndefinedOpinion;
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

	public Sample(Location location, Photo photo, Opinion opinion) throws UserValidationException {
		this.location = location;
		this.photo = photo;
		this.user = opinion.getUser();
		this.creationDate = LocalDate.now();
		this.opinionHistory = new ArrayList<>();
		this.state = new BasicVotedSampleState();
		this.addOpinion(opinion);
	}

	/**
	 * Anade la opinion de un usuario segun el estado de la muestra
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
	 * @param User
	 * @return boolean
	 */
	public boolean userAlreadyVote(User user) {
		return opinionHistory.stream()
				.anyMatch(userOpinion -> userOpinion.getUser().equals(user));
	}

	/**
	 * Obtiene el resultado de una muestra, esta puede la opinion mas vota
	 * o en su defecto si hay empate devuelve indefinida
	 * @return GenericOpinionType
	 */
	public GenericOpinionType getActualResult() {
		if (anyExpertUserVote()) {
			return actualResultIfWasVotedByExpert();
		}
		Map<OpinionType, Integer> mapOpinions = mapOpinionsByOpinionType();
		return getMostVotedOpinionOrUndefinedIfDraw(mapOpinions);
	}

	/**
	 * Devuelve la opinion mas votada o si es empate indefinida
	 * @param Map<OpinionType, Integer>
	 * @return GenericOpinionType
	 */
	private GenericOpinionType getMostVotedOpinionOrUndefinedIfDraw(Map<OpinionType, Integer> mapOpinions) {
		Entry<OpinionType, Integer> maxEntry = mapOpinions.entrySet().iterator().next();
		for (Entry<OpinionType, Integer> entry : mapOpinions.entrySet()) {
			if (entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		mapOpinions.remove(maxEntry.getKey());
		return mapOpinions.containsValue(maxEntry.getValue()) 
				? UndefinedOpinion.UNDEFINED
				: maxEntry.getKey();
	}

	/**
	 * Mapea todas las opiniones por el tipo y su cantidad de repeticiones en el historial
	 * @return Map<OpinionType, Integer>
	 */
	private Map<OpinionType, Integer> mapOpinionsByOpinionType() {
		Map<OpinionType, Integer> mapOpinions = new TreeMap<>();
		getOpinionsAsList().forEach(
				opinion -> mapOpinions.put(opinion, Collections.frequency(getOpinionsAsList(), opinion)));
		return mapOpinions;
	}

	/**
	 * Obtiene todas las opiniones del historial
	 * @return List<OpinionType>
	 */
	private List<OpinionType> getOpinionsAsList() {
		return opinionHistory.stream()
				.map(opinion -> opinion.getOpinionType()).collect(Collectors.toList());
	}

	/**
	 * Obtiene la opinion mas votada por expertos
	 * @return OpinionType
	 */
	private OpinionType actualResultIfWasVotedByExpert() {
		return opinionHistory.stream()
				.filter(opinion -> opinion.getUser().hasExpertKnowledge()).findFirst().get()
				.getOpinionType();
	}

	/**
	 * Indica si algun usuario que haya votado es experto
	 * @return boolean
	 */
	private boolean anyExpertUserVote() {
		return this.opinionHistory.stream()
				.anyMatch(opinion -> opinion.getUser()
				.hasExpertKnowledge());
	}
}
