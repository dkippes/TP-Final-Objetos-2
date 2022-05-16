package a.la.caza.de.las.vinchucas;

import java.util.HashSet;
import java.util.Set;

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
	
	public void registerSample(Sample sample) {
		
	}

	public void registerUser(User user) {
		
	}
}

