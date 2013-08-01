package pl.mmajcherski.rps.domain.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class GestureGameConfigurationTest {
	
	@Test
	public void shouldProvideGamePlayers() {
		// given
		PlayerId player1 = new PlayerId("1");
		PlayerId player2 = new PlayerId("2");
		
		// when
		GestureGameConfiguration configuration = new GestureGameConfiguration.Builder()
			.withHumanPlayer(player1)
			.withHumanPlayer(player2)
			.build();
		
		assertThat(configuration.getPlayers()).hasSize(2);
	}
	
	@Test
	public void shouldRegisterHumanPlayersAsGameEventListeners() {
		// given
		PlayerId player1 = new PlayerId("1");
		PlayerId player2 = new PlayerId("2");
		
		// when
		GestureGameConfiguration configuration = new GestureGameConfiguration.Builder()
			.withHumanPlayer(player1)
			.withHumanPlayer(player2)
			.build();
		
		assertThat(configuration.getGameEventsListeners()).hasSize(2);
	}

	@Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Player must be given non-null ID")
	public void shouldNotAcceptNullAsPlayer() {
		// given
		PlayerId player = null;
		
		// when
		new GestureGameConfiguration.Builder()
			.withHumanPlayer(player)
			.build();
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Player with ID: 1 has already joined the game")
	public void shouldNotAcceptTwoPlayersWithSameIds() {
		// given
		PlayerId player = new PlayerId("1");
		
		// when
		new GestureGameConfiguration.Builder()
			.withHumanPlayer(player)
			.withHumanPlayer(player)
			.build();
	}
	
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Game has a limit of 2 players")
	public void shouldNotAcceptMoreThanTwoPlayers() {
		// given
		PlayerId player1 = new PlayerId("1");
		PlayerId player2 = new PlayerId("2");
		PlayerId player3 = new PlayerId("3");
		
		// when
		new GestureGameConfiguration.Builder()
			.withHumanPlayer(player1)
			.withHumanPlayer(player2)
			.withHumanPlayer(player3)
			.build();
	}
	
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Not all players joined the game")
	public void shouldNotBuildAGameWithLessThanTwoPlayers() {
		// given
		PlayerId player = new PlayerId("1");
		
		// when
		new GestureGameConfiguration.Builder()
			.withHumanPlayer(player)
			.build();
	}
	
}
