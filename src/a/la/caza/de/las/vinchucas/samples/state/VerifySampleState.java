package a.la.caza.de.las.vinchucas.samples.state;

import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/**
 * Describe el estado de la muestra como verificada
 */
public class VerifySampleState extends SampleStateImpl {

	/**
	 * Una muestra verificada no puede agregar opiniones
	 * @param Sample, Opinion
	 */
	@Override
	public void addOpinion(Sample sample, Opinion opinion) {
	}

	/**
	 * Una muestra verificada no puede actualizarse
	 * @param Sample, Opinion
	 */
	@Override
	public void updatedState(Sample sample, Opinion opinion) {
	}

	@Override
	public Vote getLevelVerification() {
		return Vote.VERIFIED;
	}
}
