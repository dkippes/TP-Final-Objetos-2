package a.la.caza.de.las.vinchucas.samples.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.samples.verification.level.Vote;

public class VerifySampleStateTest {
	private VerifySample verifySampleState;

	@BeforeEach
	void setUp() {
		verifySampleState = new VerifySample();
	}

	@Test
	void testCheckIfSampleCanBeUpdated() {
		verifySampleState.updatedState(null, null);
		assertEquals(Vote.VERIFIED, verifySampleState.getLevelVerification());
	}
}
