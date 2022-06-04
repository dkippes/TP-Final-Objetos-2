package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/**
 * Clase VerifySampleState
 * 
 * Esta clase describe el estado de verificación de cada muestra.
 */

public class VerifySampleState extends SampleStateImpl {

	@Override
	public void addOpinion(Sample sample, Opinion opinion) {
	}

	@Override
	public void updatedState(Sample sample, Opinion opinion) {
	}

	@Override
	public Vote getLevelVerification() {
		return Vote.VERIFIED;
	}
}
