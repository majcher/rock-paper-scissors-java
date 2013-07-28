package pl.mmajcherski.rps.domain.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.Player;

public class HumanPlayerTest {

	@Test
	public void shouldHaveIdSetAfterConstruction() {
		// given
		PlayerId playerId = new PlayerId("1");
		
		// when
		Player player = HumanPlayer.withId(playerId);
		
		// then
		assertThat(player.getId()).isEqualTo(playerId);
	}
	
}
