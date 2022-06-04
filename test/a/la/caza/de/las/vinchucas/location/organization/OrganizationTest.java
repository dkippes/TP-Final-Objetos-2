package a.la.caza.de.las.vinchucas.location.organization;

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

public class OrganizationTest {
	private Organization organizationHealth, organizationEducation, organizationCultural, organizationAssistance;
	private Location location;
	private CoverageArea coverageArea;
	private Sample sample;

	@BeforeEach
	void setUp() {
		location = mock(Location.class);
		coverageArea = mock(CoverageArea.class);
		sample = mock(Sample.class);
		organizationHealth = new Organization(location, OrganizationType.HEALTH, 4);
		organizationEducation = new Organization(location, OrganizationType.EDUCATION, 4);
		organizationCultural = new Organization(location, OrganizationType.CULTURAL, 4);
		organizationAssistance = new Organization(location, OrganizationType.ASSISTANCE, 4);
	}

	@Test
	void testOrganization() {
		assertAll(() -> assertEquals(organizationHealth.getLocation(), location),
				() -> assertEquals(organizationHealth.getWorkingPeople(), 4),
				() -> assertEquals(organizationHealth.getOrganizationType(), OrganizationType.HEALTH),
				() -> assertEquals(organizationEducation.getOrganizationType(), OrganizationType.EDUCATION),
				() -> assertEquals(organizationCultural.getOrganizationType(), OrganizationType.CULTURAL),
				() -> assertEquals(organizationAssistance.getOrganizationType(), OrganizationType.ASSISTANCE));
	}

	@Test
	void testUploadNewSampleFunctionality() {
		ExternalFunctionality uploadNewSampleFunctionality = mock(ExternalFunctionality.class);
		organizationHealth.setUploadSampleFunctionality(uploadNewSampleFunctionality);
		organizationHealth.uploadNewSample(coverageArea, sample);
		verify(uploadNewSampleFunctionality, times(1)).newEvent(organizationHealth, coverageArea, sample);
	}

	@Test
	void testValidateSampleFunctionality() {
		ExternalFunctionality validateSampleFunctionality = mock(ExternalFunctionality.class);
		organizationHealth.setValidateSampleFunctionality(validateSampleFunctionality);
		organizationHealth.validateSample(coverageArea, sample);
		verify(validateSampleFunctionality, times(1)).newEvent(organizationHealth, coverageArea, sample);
	}
}
