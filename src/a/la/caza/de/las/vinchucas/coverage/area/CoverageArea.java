package a.la.caza.de.las.vinchucas.coverage.area;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 * Describe la informacion de cada area de cobertura.
 */
public class CoverageArea {
	private String name;
	private Location epicenter;
	private float radio;
	private Set<OrganizationObserver> organizationObservers;
	private Set<Sample> samples;

	public CoverageArea(String name, Location epicenter, float radio) {
		this.name = name;
		this.epicenter = epicenter;
		this.radio = radio;
		this.organizationObservers = new HashSet<>();
		this.samples = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public Set<OrganizationObserver> getOrganizationObservers() {
		return organizationObservers;
	}

	public Set<Sample> getSamples() {
		return samples;
	}

	public Location getEpicenter() {
		return epicenter;
	}

	public float getRadio() {
		return radio;
	}
	
	
	/**
	 * Dada una lista de muestras, retorna las que pertenecen al area de cobertura.
	 * @param List<Sample>
	 * @return List<Sample>
	 */

	public List<Sample> samplesInCoverageArea(List<Sample> samples) {
		return samples.stream()
				.filter(c -> this.samples.contains(c))
				.collect(Collectors.toList());
	}

	/**
	 * Aniade Organizaciones que quieran suscribirse a un area de covertura
	 * @param OrganizationObserver
	 */
	public void addOrganizationObserver(OrganizationObserver organizationObserver) {
		organizationObservers.add(organizationObserver);
	}

	/**
	 * Remueve Organizaciones que quieran desuscribirse a un area de covertura
	 * @param OrganizationObserver
	 */
	public void removeOrganizationObserver(OrganizationObserver organizationObserver) {
		organizationObservers.remove(organizationObserver);
	}

	/**
	 * Aniade una muestra, y si pertenece al area de covertura notifica a todas las ong
	 * que estan suscriptas
	 * @param Sample
	 */
	public void addNewSample(Sample sample) {
		if (belongsToCoverageArea(sample)) {
			samples.add(sample);
			notifyNewSampleAdd(sample);
		}
	}

	/**
	 * Aniade una muestra verificada y notifica a todas las ong que estan suscriptas
	 * @param Sample
	 */
	public void addVerifySample(Sample sample) {
		notifyVerifySample(sample);
	}

	/**
	 * Notifica a todas las ong que se verifico una muestra
	 * @param Sample
	 */
	private void notifyVerifySample(Sample sample) {
		organizationObservers.forEach(observer -> observer.validateSample(this, sample));
	}

	/**
	 * Notifica a todas las ong que se agrego una muestra
	 * @param Sample
	 */
	private void notifyNewSampleAdd(Sample sample) {
		organizationObservers.forEach(observer -> observer.uploadNewSample(this, sample));
	}

	/**
	 * Indica si la muestra pertence al area de cobertura
	 * @param Sample
	 */
	public boolean belongsToCoverageArea(Sample sample) {
		return epicenter.distanceBetweenTwoLocations(sample.getLocation()) <= radio;
	}
	
	/**
	 * Indica si el area de cobertura se solapa
	 * @param CoverageArea
	 */
	public boolean coverageAreasAreOverlapped(CoverageArea coverageArea2) {
		return epicenter.distanceBetweenTwoLocations(coverageArea2.getEpicenter()) < radio;
		
	}
}
