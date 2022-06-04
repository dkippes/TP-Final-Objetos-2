package a.la.caza.de.las.vinchucas.location;

import java.util.List;
import java.util.stream.Collectors;

import a.la.caza.de.las.vinchucas.samples.Sample;

/**
 *  Describe la informacion de una ubicacion medida en kilometros
 */
public class Location {
	private double latitude;
	private double lenght;

	public Location(double latitude, double lenght) {
		this.latitude = latitude;
		this.lenght = lenght;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLenght() {
		return lenght;
	}

	/**
	 * Calcula la distancia entre 2 ubicaciones
	 * @param Location
	 * @return double
	 */
	public double distanceBetweenTwoLocations(Location location2) {
		double earthRadius = 6371;

		double dLat = Math.toRadians(location2.getLatitude() - this.getLatitude());
		double dLng = Math.toRadians(location2.getLenght() - this.getLatitude());
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(this.getLatitude()))
				* Math.cos(Math.toRadians(location2.getLatitude()));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		double distance = Math.abs(earthRadius * va2);

		return Math.round(distance * 100d) / 100d;
	}

	/**
	 * Segun una lista de ubicaciones y una distancia obtiene las ubicaciones mas cercanas
	 * @param List<Location>, double
	 * @return List<Location>
	 */
	public List<Location> getNearLocationsInDistance(List<Location> locations, double kilometers) {
		return locations.stream().filter(location -> this.distanceBetweenTwoLocations(location) <= kilometers)
				.collect(Collectors.toList());
	}

	/**
	 * Segun una lista de muestras, una muestra y una distancia obtiene 
	 * las muestras con el mismo resultado actual de la muestra pasada y que esta igual o mas
	 * cerca de la distancia pasado por parametro
	 * @param Sample, List<Sample>, double
	 * @return List<Sample>
	 */
	public List<Sample> getNearSamplesInDistance(Sample sample, List<Sample> samples, double kilometers) {
		return samples.stream().filter(sampleType -> sample.getActualResult().equals(sampleType.getActualResult()))
				.filter(sampleLocation -> sample.getLocation()
						.distanceBetweenTwoLocations(sampleLocation.getLocation()) <= kilometers)
				.collect(Collectors.toList());
	}
}
