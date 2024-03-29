package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.exceptions.UserValidationException;
import a.la.caza.de.las.vinchucas.opinions.GenericOpinionType;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/**
 * Protocolo de estado de una muestra 
 */
public interface ISampleState {
	public void addOpinion(Sample sample, Opinion opinion) throws UserValidationException;

	public void updatedState(Sample sample, Opinion opinion);

	public Vote getLevelVerification();
	
	public GenericOpinionType getResult(Sample sample);
}
