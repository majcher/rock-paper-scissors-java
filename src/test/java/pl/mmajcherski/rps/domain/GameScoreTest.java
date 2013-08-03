package pl.mmajcherski.rps.domain;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GameScore;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.domain.player.Players;
import pl.mmajcherski.rps.domain.player.impl.HumanPlayer;

public class GameScoreTest {

	private static final PlayerId PLAYER_ID_1 = new PlayerId("1");
	private static final PlayerId PLAYER_ID_2 = new PlayerId("2");
	
	private GameScore gameScore;
	
	@BeforeMethod
	public void createGameScore() {
		Players players = new Players();
		players.add(HumanPlayer.withId(PLAYER_ID_1));
		players.add(HumanPlayer.withId(PLAYER_ID_2));
		
		gameScore = new GameScore(players);
	}
	
	@Test
	public void shouldProvideScoreOfExistingPlayer() {
		// when
		int score = gameScore.of(PLAYER_ID_1);
		
		// then
		assertThat(score).isEqualTo(0);
	}
	
	@Test
	public void shouldIncreaseScoreOfExistingPlayer() {
		// when
		gameScore.increaseScoreForPlayer(PLAYER_ID_1);
		int score = gameScore.of(PLAYER_ID_1);
		
		// then
		assertThat(score).isEqualTo(1);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "No such player in the game")
	public void shouldThrowExceptionOnAttemptToGetScoreOfNonExistingPlayer() {
		// when
		gameScore.of(new PlayerId("123"));
	}
	
}
