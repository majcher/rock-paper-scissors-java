package pl.mmajcherski.rps.domain.impl;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.Player;

public class GestureGameTest {

	private GestureGameConfiguration configuration = new GestureGameConfiguration.Builder().build();
	
	@Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Player must not be null")
	public void shouldNotAcceptNullAsPlayer() {
		// given
		Player player = null;
		
		// when
		GestureGame game = new GestureGame(configuration);
		game.add(player);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Player with ID: 1 has already joined the game")
	public void shouldNotAcceptTwoPlayersWithSameIds() {
		// given
		PlayerId playerId = new PlayerId("1");
		
		// when
		GestureGame game = new GestureGame(configuration);
		game.add(HumanPlayer.withId(playerId));
		game.add(HumanPlayer.withId(playerId));
	}
	
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Game has a limit of 2 players")
	public void shouldNotAcceptMoreThanTwoPlayers() {
		// given
		PlayerId playerId1 = new PlayerId("1");
		PlayerId playerId2 = new PlayerId("2");
		PlayerId playerId3 = new PlayerId("3");
		
		// when
		GestureGame game = new GestureGame(configuration);
		game.add(HumanPlayer.withId(playerId1));
		game.add(HumanPlayer.withId(playerId2));
		game.add(HumanPlayer.withId(playerId3));
	}
	
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Not all players joined the game")
	public void shouldNotStartTheGameWithoutAllPlayers() {
		// given
		PlayerId playerId1 = new PlayerId("1");
		GestureGame game = new GestureGame(configuration);
		game.add(HumanPlayer.withId(playerId1));

		// when
		game.start();
	}

	
}
