package pl.mmajcherski.rps.domain;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.player.PlayerId;

public class GameFinalScoreTest {

	private static final PlayerId ANY_PLAYER_ID = new PlayerId("1");
	
	private GameFinalScore gameScore;
	
	@BeforeMethod
	public void createGameScore() {
		gameScore = GameFinalScore.initialScore();
	}
	
	@Test
	public void shouldProvidePlayersScore() {
		// when
		int score = gameScore.of(ANY_PLAYER_ID);
		
		// then
		assertThat(score).isEqualTo(0);
	}
	
	@Test
	public void shouldReturnIncreasedPlayersScore() {
		// when
		gameScore = gameScore.increaseScoreForPlayer(ANY_PLAYER_ID);
		int score = gameScore.of(ANY_PLAYER_ID);
		
		// then
		assertThat(score).isEqualTo(1);
	}
	
	@Test
	public void shouldNotMutateOnIncrease() {
		// when
		gameScore.increaseScoreForPlayer(ANY_PLAYER_ID);
		int score = gameScore.of(ANY_PLAYER_ID);
		
		// then
		assertThat(score).isEqualTo(0);
	}
	
}
