package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

public interface ISampleState {
	/**
	 * Interfaz ISampleState
	 * 
	 * Esta interfaz describe el estado de cada muestra. 
	 * 
	 */
	
	public void addOpinion(Sample sample, Opinion opinion) throws UserValidationException;

	public void updatedState(Sample sample, Opinion opinion);

	public Vote getLevelVerification(Sample sample);
}
