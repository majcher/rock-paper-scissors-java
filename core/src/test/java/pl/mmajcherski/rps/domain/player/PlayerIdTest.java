package pl.mmajcherski.rps.domain.player;

import static org.fest.assertions.api.Assertions.assertThat;
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
	public void shouldHaveIdValueSetUponConstruction() {
		// given
		String idValue = "Test ID";
		
		// when
		PlayerId playerId = new PlayerId(idValue);
		
		// then
		assertThat(playerId.getValue()).isEqualTo(idValue);
	}
	
	@Test
	public void shouldHaveEqualsHashCodeContractMet() {
		EqualsVerifier.forClass(PlayerId.class).usingGetClass().allFieldsShouldBeUsed().verify();
	}
}
