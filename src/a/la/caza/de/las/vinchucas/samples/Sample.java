package a.la.caza.de.las.vinchucas.samples;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.state.BasicVotedSampleState;
import a.la.caza.de.las.vinchucas.samples.state.SampleStateImpl;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class Sample {
	private User user;
	private Photo photo;
	private Location location;
	private List<Opinion> opinionHistory;
	private LocalDate creationDate;
	private SampleStateImpl state;

	public Sample(Location location, Photo photo, Opinion opinion) throws Exception {
		this.location = location;
		this.photo = photo;
		this.user = opinion.getUser();
		this.creationDate = LocalDate.now();
		this.opinionHistory = new ArrayList<>();
		this.state = new BasicVotedSampleState();
		this.addOpinion(opinion);
	}

	public void addOpinion(Opinion opinion) throws Exception {
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
		return this.state.getLevelVerification(this);
	}

	public void setState(SampleStateImpl state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public boolean userAlreadyVote(List<Opinion> opinionHistory, User user) {
		return opinionHistory.stream().anyMatch(u -> u.getUser().getId() == user.getId());
	}

	public String getActualResult() {
		if(this.opinionHistory.stream().anyMatch(o -> o.getUser().hasExpertKnowledge())) {
			return opinionHistory.stream()
					.filter(o -> o.getUser().hasExpertKnowledge())
					.findFirst().get().getOpinionType();
		}
		
		List<String> opinions = opinionHistory.stream().map(o -> o.getOpinionType()).collect(Collectors.toList());
		Map<String, Integer> mapOpinions = new TreeMap<>();
		opinions.forEach(o -> mapOpinions.put(o, Collections.frequency(opinions, o)));

		Map.Entry<String, Integer> maxEntry = null;

		for (Map.Entry<String, Integer> entry : mapOpinions.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}

		mapOpinions.remove(maxEntry.getKey());

		if (mapOpinions.containsValue(maxEntry.getValue())) {
			return OpinionType.getUndefinedOpinion();
		}

		return maxEntry.getKey();
	}

}
