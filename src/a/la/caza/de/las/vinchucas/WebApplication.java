package a.la.caza.de.las.vinchucas;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.User;

public class WebApplication {
	private static WebApplication webApplication;
	private Set<User> registeredUsers;
	private Set<Sample> registeredSamples;

	public WebApplication() {
		registeredUsers = new HashSet<>();
		registeredSamples = new HashSet<>();
	}

	public static WebApplication createApp() {
		if (webApplication == null) {
			return webApplication = new WebApplication();
		}
		return webApplication;
	}

	public WebApplication registerSample(Sample sample) {
		registeredSamples.add(sample);
		return this;
	}

	public WebApplication registerUser(User user) {
		registeredUsers.add(user);
		return this;
	}

	public Set<User> getRegisteredUsers() {
		return this.registeredUsers;
	}

	public Set<Sample> getRegisteredSamples() {
		return this.registeredSamples;
	}

	public List<Opinion> getUserOpinions(User user) {
		return this.getRegisteredSamples().stream()
				.flatMap(sample -> sample.getOpinionHistory().stream())
				.filter(userOpinion -> user.equals(userOpinion.getUser())).toList();
	}

	public List<Sample> getUserSamples(User user) {
		return this.getRegisteredSamples().stream()
				.filter(sampleByUser -> user.equals(sampleByUser.getUser())).toList();
	}
	
	public long manyOpinionMadeByUserBeforeAnyDays(User user, int daysBefore) {
		LocalDate dateBefore = LocalDate.now().minusDays(daysBefore);
		return getUserOpinions(user).stream()
				.filter(u -> dateBefore.isBefore(u.getDateOfIssue()))
				.count();
	}
	
	public long manySamplesSendByUserBeforeAnyDays(User user, int daysBefore) {
		LocalDate dateBefore = LocalDate.now().minusDays(daysBefore);
		return getUserSamples(user).stream()
				.filter(u -> dateBefore.isBefore(u.getCreationDate()))
				.count();
	}
}
