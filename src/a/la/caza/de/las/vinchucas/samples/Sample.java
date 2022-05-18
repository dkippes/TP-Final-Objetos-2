package a.la.caza.de.las.vinchucas.samples;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.users.User;

public class Sample {
	private User user;
	private Photo photo;
	private Location location;
	private Map<User, Opinion> opinionHistory;
	private LocalDate creationDate;
	private LocalDate lastVotation;

	public Sample(Location location, Photo photo, User user) {
		this.location = location;
		this.photo = photo;
		this.user = user;
		this.creationDate = LocalDate.now();
		this.opinionHistory = new HashMap<>();
	}

	public Sample addUserOpinion(Opinion opinion, User user) {
		this.lastVotation = opinion.getDateOfIssue();
		this.opinionHistory.put(user, opinion);
		return this;
	}
	
	public Map<User, Opinion> getOpinionHistory() {
		return opinionHistory;
	}
	
	public LocalDate getLastVotation() {
		return lastVotation;
	}
	
	public LocalDate getCreationDate() {
		return creationDate;
	}
}
