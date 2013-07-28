package pl.mmajcherski.rps.domain.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.impl.gesture.Rock;
import pl.mmajcherski.rps.domain.impl.gesture.Scissors;

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
	public void shouldReturnExistingPlayerById() {
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
	
	@Test
	public void shouldCompareGesturesBetweenPlayers() {
		// given
		PlayerId playerId1 = new PlayerId("1");
		Player player1 = mock(Player.class);
		when(player1.getId()).thenReturn(playerId1);
		game.accept(player1);
		
		PlayerId playerId2 = new PlayerId("2");
		Player player2 = mock(Player.class);
		when(player2.getId()).thenReturn(playerId2);
		game.accept(player2);
		
		when(player1.getGestureShown()).thenReturn(Rock.INSTANCE);
		when(player2.getGestureShown()).thenReturn(Scissors.INSTANCE);
		
		// when
		GamePlayStatus gamePlayStatus = game.getGamePlayStatusFor(playerId1);
		
		// then
		GamePlayStatus expectedStatus = Rock.INSTANCE.compareToGesture(Scissors.INSTANCE);
		assertThat(gamePlayStatus).isEqualsToByComparingFields(expectedStatus);
	}
	
}
