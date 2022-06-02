package a.la.caza.de.las.vinchucas.location.ngo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a.la.caza.de.las.vinchucas.coverage.area.CoverageArea;
import a.la.caza.de.las.vinchucas.coverage.area.functionality.ExternalFunctionality;
import a.la.caza.de.las.vinchucas.location.Location;
import a.la.caza.de.las.vinchucas.samples.Sample;

public class NgoTest {
	private Ngo ngoHealth, ngoEducation, ngoCultural, ngoAssistance;
	private Location location;
	private CoverageArea coverageArea;
	private Sample sample;

	@BeforeEach
	void setUp() {
		location = mock(Location.class);
		coverageArea = mock(CoverageArea.class);
		sample = mock(Sample.class);
		ngoHealth = new Ngo(location, NgoType.HEALTH, 4);
		ngoEducation = new Ngo(location, NgoType.EDUCATION, 4);
		ngoCultural = new Ngo(location, NgoType.CULTURAL, 4);
		ngoAssistance = new Ngo(location, NgoType.ASSISTANCE, 4);
	}

	@Test
	void testNgo() {
		assertAll(() -> assertEquals(ngoHealth.getLocation(), location),
				() -> assertEquals(ngoHealth.getWorkingPeople(), 4),
				() -> assertEquals(ngoHealth.getNgoType(), NgoType.HEALTH),
				() -> assertEquals(ngoEducation.getNgoType(), NgoType.EDUCATION),
				() -> assertEquals(ngoCultural.getNgoType(), NgoType.CULTURAL),
				() -> assertEquals(ngoAssistance.getNgoType(), NgoType.ASSISTANCE),
				() -> assertEquals(ngoAssistance.getNgoTypeString(), "Assistance"));
	}

	@Test
	void testUploadNewSampleFunctionality() {
		ExternalFunctionality uploadNewSampleFunctionality = mock(ExternalFunctionality.class);
		ngoHealth.setUploadSampleFunctionality(uploadNewSampleFunctionality);
		ngoHealth.uploadNewSample(coverageArea, sample);
		verify(uploadNewSampleFunctionality, times(1)).newEvent(ngoHealth, coverageArea, sample);
	}

	@Test
	void testValidateSampleFunctionality() {
		ExternalFunctionality validateSampleFunctionality = mock(ExternalFunctionality.class);
		ngoHealth.setValidateSampleFunctionality(validateSampleFunctionality);
		ngoHealth.validateSample(coverageArea, sample);
		verify(validateSampleFunctionality, times(1)).newEvent(ngoHealth, coverageArea, sample);
	}
}
