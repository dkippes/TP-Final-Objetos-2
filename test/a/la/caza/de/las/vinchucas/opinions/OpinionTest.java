package a.la.caza.de.las.vinchucas.opinions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.users.User;

public class OpinionTest {

	@BeforeEach 
	void setUp() {
		
	}
	@Test 
	void testCreationOfNothingOpinion() {
	  User tomas  = new User("Tomas") ;
	  Opinion op1 = new Nothing(tomas);
	  assertEquals(op1.getClass().getSimpleName(), "Nothing");
	}
	
}
