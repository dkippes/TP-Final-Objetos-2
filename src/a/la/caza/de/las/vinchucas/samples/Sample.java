package a.la.caza.de.las.vinchucas.samples;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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
		return opinionHistory.stream().anyMatch(u -> u.equals(user));
	}
	
	public String getActualResult() {
		List<String> opinions = opinionHistory.stream()
				.map(o -> o.getOpinionType())
				.collect(Collectors.toList());
		Map<String, Integer> mapOpinions = new HashMap<>();
		opinions.forEach(o -> mapOpinions.put(o, Collections.frequency(opinions, o)));
		
		List<Entry<String, Integer>> sorted =
				mapOpinions.entrySet().stream()
			       .sorted(Map.Entry.comparingByValue())
			       .collect(Collectors.toList());
		
		if(sorted.size() >= 2 || sorted.get(sorted.size() - 1).getValue() == sorted.get(sorted.size() - 2).getValue()) {
			return "UNDEFINED";
		}
		return sorted.get(0).getKey();
	}

}
