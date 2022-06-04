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

	public void addOpinion(Opinion opinion) throws UserValidationException {
		this.state.addOpinion(this, opinion);
	}

	public void addUserOpinion(Opinion opinion) {
		this.opinionHistory.add(opinion);
	}

	public List<Opinion> getOpinionHistory() {
		return opinionHistory;
	}

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

	public boolean userAlreadyVote(User user) {
		return opinionHistory.stream()
				.anyMatch(userOpinion -> userOpinion.getUser().getId() == user.getId());
	}

	public GenericOpinionType getActualResult() {
		if (anyExpertUserVote()) {
			return actualResultIfWasVotedByExpert();
		}
		Map<OpinionType, Integer> mapOpinions = mapOpinionsByOpinionType();
		return getMostVotedOpinionOrUndefinedIfDraw(mapOpinions);
	}

	private GenericOpinionType getMostVotedOpinionOrUndefinedIfDraw(Map<OpinionType, Integer> mapOpinions) {
		Entry<OpinionType, Integer> maxEntry = null;
		for (Entry<OpinionType, Integer> entry : mapOpinions.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		mapOpinions.remove(maxEntry.getKey());
		return mapOpinions.containsValue(maxEntry.getValue()) 
				? UndefinedOpinion.UNDEFINED
				: maxEntry.getKey();
	}

	private Map<OpinionType, Integer> mapOpinionsByOpinionType() {
		Map<OpinionType, Integer> mapOpinions = new TreeMap<>();
		getOpinionsAsList().forEach(
				opinion -> mapOpinions.put(opinion, Collections.frequency(getOpinionsAsList(), opinion)));
		return mapOpinions;
	}

	private List<OpinionType> getOpinionsAsList() {
		return opinionHistory.stream()
				.map(opinion -> opinion.getOpinionType()).collect(Collectors.toList());
	}

	private OpinionType actualResultIfWasVotedByExpert() {
		return opinionHistory.stream()
				.filter(opinion -> opinion.getUser().hasExpertKnowledge()).findFirst().get()
				.getOpinionType();
	}

	private boolean anyExpertUserVote() {
		return this.opinionHistory.stream()
				.anyMatch(opinion -> opinion.getUser()
				.hasExpertKnowledge());
	}

	public Location getLocation() {
		return location;
	}

	public Photo getPhoto() {
		return photo;
	}
}
