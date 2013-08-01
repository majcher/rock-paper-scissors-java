package pl.mmajcherski.rps.domain.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GameScoreTest {

	private static final PlayerId PLAYER_ID_1 = new PlayerId("1");
	private static final PlayerId PLAYER_ID_2 = new PlayerId("2");
	
	private GameScore gameScore;
	
	@BeforeMethod
	public void createGameScore() {
		Set<PlayerId> playerIds = new HashSet<>();
		playerIds.add(PLAYER_ID_1);
		playerIds.add(PLAYER_ID_2);
		
		gameScore = new GameScore(playerIds);
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
