package a.la.caza.de.las.vinchucas.samples.state;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import a.la.caza.de.las.vinchucas.exceptions.UserAlreadyVoteException;
import a.la.caza.de.las.vinchucas.exceptions.UserIsNotExpertException;
import a.la.caza.de.las.vinchucas.opinions.Opinion;
import a.la.caza.de.las.vinchucas.opinions.OpinionType;
import a.la.caza.de.las.vinchucas.samples.Sample;
import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

/*
 * Esta clase abstacta describe el estado de cada muestra.
 */
public abstract class SampleState implements ISampleState {

	@Override
	public Vote getLevelVerification() {
		return Vote.VOTED;
	}

	/**
	 * Lanza una excepcion si un usuario ya voto en la muestra anteriormente
	 * @throws UserAlreadyVoteException
	 * @exception UserAlreadyVoteException
	 * @param Sample, Opinion
	 */
	public void checkIfUserAlreadyVote(Sample sample, Opinion opinion) throws UserAlreadyVoteException {
		if (userAlreadyVote(sample, opinion)) {
			throw new UserAlreadyVoteException("User " + opinion.getUser().getName() + " already voted");
		}
	}

	/**
	 * Lanza una excepcion si el usuario no es experto
	 * @throws UserIsNotExpertException
	 * @exception UserIsNotExpertException
	 * @param Opinion
	 */
	public void checkIfUserIsExpert(Opinion opinion) throws UserIsNotExpertException {
		opinion.getUser().checkIfCanVote();
	}

	/**
	 * Indica si un usuario ya voto en la muestra anteriormente
	 * @param Sample, Opinion
	 */
	public boolean userAlreadyVote(Sample sample, Opinion opinion) {
		return sample.userAlreadyVote(opinion.getUser());
	}

	/**
	 * Indica si un usuario es experto
	 * @param Opinion
	 */
	public boolean userIsExpert(Opinion opinion) {
		return opinion.getUser().hasExpertKnowledge();
	}
	
	public Map<OpinionType, Integer> mapOpinionsByOpinionType(Sample sample, List<OpinionType> opinions) {
		Map<OpinionType, Integer> mapOpinions = new TreeMap<>();
		opinions.forEach(opinion -> mapOpinions.put(opinion,
				Collections.frequency(opinions, opinion)));
		return mapOpinions;
	}
}
