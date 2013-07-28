package pl.mmajcherski.rps.domain.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.Player;

public class RockPaperScissorsGameTest {

	private RockPaperScissorsGame game;
	
	@BeforeMethod
	public void createGame() {
		game = new RockPaperScissorsGame();
	}
	
	@Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Player cannot be null")
	public void shouldNotAcceptNullAsPlayer() {
		// given
		Player player = null;
		
		// when
		game.accept(player);
	}
	
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Game has a limit of 2 players")
	public void shouldNotAcceptMoreThanTwoPlayers() {
		// given
		Player player1 = HumanPlayer.withId(new PlayerId("1"));
		Player player2 = HumanPlayer.withId(new PlayerId("2"));
		Player player3 = HumanPlayer.withId(new PlayerId("3"));
		
		// when
		game.accept(player1);
		game.accept(player2);
		game.accept(player3);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Player with ID: 1 has already joined the game")
	public void shouldNotAcceptTwoPlayersWithSameIds() {
		// given
		PlayerId playerId = new PlayerId("1");
		Player player1 = HumanPlayer.withId(playerId);
		Player player2 = HumanPlayer.withId(playerId);
		
		// when
		game.accept(player1);
		game.accept(player2);
	}
	
	@Test
	public void shouldReturnPlayerById() {
		// given
		PlayerId playerId = new PlayerId("1");
		Player player = HumanPlayer.withId(playerId);
		game.accept(player);
		
		// when
		Player gamePlayer = game.getPlayerById(playerId);
		
		// then
		assertThat(gamePlayer).isEqualTo(player);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "No player with ID: 1 in this game")
	public void shouldThrowAnExceptionOnAttemptToGetNonExistingPlayer() {
		// given
		PlayerId playerId = new PlayerId("1");
		
		// when
		game.getPlayerById(playerId);
	}
	
}
