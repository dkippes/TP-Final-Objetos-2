package a.la.caza.de.las.vinchucas.samples;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVote;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.state.BasicVotedSampleState;
import a.la.caza.de.las.vinchucas.samples.state.SampleState;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;
import a.la.caza.de.las.vinchucas.users.User;

public class Sample {
	private User user;
	private Photo photo;
	private Location location;
	private List<Opinion> opinionHistory;
	private LocalDate creationDate;
	private LocalDate lastVotation;
	private SampleState state;

	public Sample(Location location, Photo photo, Opinion opinion) throws UserAlreadyVote {
		this.location = location;
		this.photo = photo;
		this.user = opinion.getUser();
		this.creationDate = LocalDate.now();
		this.opinionHistory = List.of(opinion);
		this.lastVotation = opinion.getDateOfIssue();
		this.state = new BasicVotedSampleState();
	}
	
	public void addOpinion(Opinion opinion) throws UserAlreadyVote {
		this.state.addOpinion(this, opinion);
	}

	public Sample addUserOpinion(Opinion opinion, User user) {
		this.lastVotation = opinion.getDateOfIssue();
		this.opinionHistory.add(opinion);
		user.sendSample(this);
		return this;
	}
	
	public List<Opinion> getOpinionHistory() {
		return opinionHistory;
	}
	
	public LocalDate getLastVotation() {
		return lastVotation;
	}
	
	public LocalDate getCreationDate() {
		return creationDate;
	}
	
	public Vote getLevelVerification() {
		return this.state.getLevelVerification(this);
	}
	
	public void setState(SampleState state) {
		this.state = state;
	}

	public boolean userHasVoteBefore(User user) {
		return false;
	}

	public User getUser() {
		return user;
	}
}
