package pl.mmajcherski.rps.domain.impl;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.testng.annotations.Test;

public class PlayerIdTest {

	@Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Player must have non-null ID")
	public void shouldNotHaveNullIdValue() {
		// given
		String idValue = null;
		
		// when
		new PlayerId(idValue);
	}
	
	@Test
	public void shouldHaveEqualsHashCodeContractMet() {
		EqualsVerifier.forClass(PlayerId.class).usingGetClass().allFieldsShouldBeUsed().verify();
	}
}
