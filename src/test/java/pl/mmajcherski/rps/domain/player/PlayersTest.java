package pl.mmajcherski.rps.domain.player;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.domain.player.Players;
import pl.mmajcherski.rps.domain.player.impl.HumanPlayer;

public class PlayersTest {
	
	private Players players;
	
	@BeforeMethod
	public void createPlayers() {
		players = new Players();
	}

	@Test
	public void shouldReturnExistingPlayerById() {
		// given
		PlayerId playerId = new PlayerId("1");
		Player player = HumanPlayer.withId(playerId);
		players.add(player);
		
		// when
		Player gamePlayer = players.getPlayerById(playerId);
		
		// then
		assertThat(gamePlayer).isEqualTo(player);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "No player with ID: 1 in this game")
	public void shouldThrowAnExceptionOnAttemptToGetNonExistingPlayer() {
		// given
		PlayerId playerId = new PlayerId("1");
		
		// when
		players.getPlayerById(playerId);
	}
	
}
