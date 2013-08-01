package pl.mmajcherski.rps.domain.impl;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.testng.annotations.Test;

public class PlayerIdTest {

	@Test
	public void shouldHaveEqualsHashCodeContractMet() {
		EqualsVerifier.forClass(PlayerId.class).usingGetClass().allFieldsShouldBeUsed().verify();
	}
}
