package a.la.caza.de.las.vinchucas;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageAreaSystem;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.users.User;

/**
 * Aplicacion web general, donde se permite registrar usuarios, muestras y zonas de coberturas
 */
public class WebApplication {
	private Set<User> registeredUsers;
	private Set<Sample> registeredSamples;
	private CoverageAreaSystem coverageAreaSystem;
	
	public WebApplication(Set<User> users, Set<Sample> samples) {
		registeredUsers = users;
		registeredSamples = samples;
		coverageAreaSystem = new CoverageAreaSystem(new HashSet<>());
	}
	
	/**
	 * Registra una muestra en la aplicacion web y en el sistema de areas de cobertura.
	 */

	public WebApplication registerSample(Sample sample) {
		registeredSamples.add(sample);
		coverageAreaSystem.registerSampleInCoverageArea(sample);
		return this;
	}
	
	/**
	 * Registra un usuario en la aplicacion web
	 */

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
	
	/**
	 * Dado un usuario, retorna las opiniones emitidas por el mismo
	 * @param User
	 * @return List<Opinion>
	 */

	public List<Opinion> getUserOpinions(User user) {
		return this.getRegisteredSamples().stream()
				.flatMap(sample -> sample.getOpinionHistory().stream())
				.filter(userOpinion -> user.equals(userOpinion.getUser()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Dado un usuario, retorna las muestras subidas por el mismo al sistema
	 * @param User
	 * @return List<Sample>
	 */

	public List<Sample> getUserSamples(User user) {
		return this.getRegisteredSamples().stream()
				.filter(sampleByUser -> user.equals(sampleByUser.getUser()))
				.collect(Collectors.toList());
	}

	/**
	 * Cuenta la cantidad de opiones hechas por un usuario segun una cantidad de dias 
	 * posteriores a la fecha actual
	 * @param User, int
	 * @return long
	 */
	public long manyOpinionMadeByUserBeforeAnyDays(User user, int daysBefore) {
		LocalDate dateBefore = LocalDate.now().minusDays(daysBefore);
		return getUserOpinions(user).stream()
				.filter(u -> dateBefore.isBefore(u.getDateOfIssue()))
				.count();
	}

	/**
	 * Cuenta la cantidad de muestras hechas por un usuario segun una cantidad de dias 
	 * posteriores a la fecha actual
	 * @param User, int
	 * @return long
	 */
	public long manySamplesSendByUserBeforeAnyDays(User user, int daysBefore) {
		LocalDate dateBefore = LocalDate.now().minusDays(daysBefore);
		return getUserSamples(user).stream()
				.filter(u -> dateBefore.isBefore(u.getCreationDate()))
				.count();
	}
}
