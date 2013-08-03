package pl.mmajcherski.rps.story.step;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.GestureGameConfiguration;
import pl.mmajcherski.rps.domain.PlayerGestureListener;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.domain.player.impl.HumanPlayer;

public class PlayRockPaperScissorsGameStorySteps implements GameEventsListener {

	private static final long GAME_PLAY_PERIOD_IN_MS = 50;
	
	private HumanPlayer player;
	private HumanPlayer opponent;
	
	private BlockingQueue<Boolean> gameStartQueue = new LinkedBlockingQueue<>();
	private BlockingQueue<GamePlayResult> gamePlayResultQueue = new LinkedBlockingQueue<>();
	private BlockingQueue<GameFinalScore> gameScoreQueue = new LinkedBlockingQueue<>();
	
	@Given("a RPS game setup for $gamePlayCount play with 2 players: $playerId and $opponentId")
	@Alias("a RPS game setup for $gamePlayCount plays with 2 players: $playerId and $opponentId")
	public void startAGame(int gamePlayCount, PlayerId playerId, PlayerId opponentId) {
		GestureGameConfiguration configuration = new GestureGameConfiguration.Builder()
				.withGameDurationInMs(GAME_PLAY_PERIOD_IN_MS)
				.withGamePlayCount(gamePlayCount)
				.build();
		
		GestureGame game = new GestureGame(configuration);
		game.registerEventsListener(this);
		
		HumanPlayer player = HumanPlayer.withId(playerId);
		player.join(game);
		
		HumanPlayer opponent = HumanPlayer.withId(opponentId);
		opponent.join(game);
		
		game.start();
		
		this.player = player;
		this.opponent = opponent;
	}
	
	@When("$playerId shows $gestureName gesture")
	public void playerShowsGesture(PlayerId playerId, Gesture gesture) throws InterruptedException {
		gameStartQueue.take();
		
		getPlayerForId(playerId).showGesture(gesture);
	}
	
	@When("both players show <gesture> gesture")
	public void bothPlayersShowSameGesture(@Named("gesture") Gesture gesture) throws InterruptedException {
		playerShowsGesture(player.getId(), gesture);
		playerShowsGesture(opponent.getId(), gesture);
	}
	
	@Then("$winnerId wins the play")
	public void playerWinsThePlay(PlayerId winnerId) throws InterruptedException {
		GamePlayResult gamePlayResult = gamePlayResultQueue.take();
		
		assertThat(gamePlayResult.getGamePlayStatusOf(winnerId)).isEqualTo(GamePlayStatus.WIN);
	}
	
	@Then("there is no play winner")
	public void nobodyWinsThePlay() throws InterruptedException {
		GamePlayResult gamePlayResult = gamePlayResultQueue.take();
		
		GamePlayStatus playerGamePlayStatus = gamePlayResult.getGamePlayStatusOf(player.getId());
		GamePlayStatus opponentGamePlayStatus = gamePlayResult.getGamePlayStatusOf(opponent.getId());
		
		assertThat(playerGamePlayStatus).isEqualTo(GamePlayStatus.TIE);
		assertThat(opponentGamePlayStatus).isEqualTo(GamePlayStatus.TIE);
	}
	
	@Then("the game score is $expectedPlayerScore:$expectedOpponentScore")
	@Alias("there is a tie $expectedPlayerScore:$expectedOpponentScore")
	public void gameScoreIs(int expectedPlayerScore, int expectedOpponentScore) throws InterruptedException {
		GameFinalScore gameScore = gameScoreQueue.take();
		
		int playerScore = gameScore.of(player.getId());
		int opponentScore = gameScore.of(opponent.getId());
		
		assertThat(playerScore).isEqualTo(expectedPlayerScore);
		assertThat(opponentScore).isEqualTo(expectedOpponentScore);
	}
	
	@Override
	public void onGamePlayStarted(PlayerGestureListener gameController) {
		try {
			gameStartQueue.put(Boolean.TRUE);
			gameStartQueue.put(Boolean.TRUE);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore) {
		try {
			gamePlayResultQueue.put(gamePlayResult);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void onGameOver(GameFinalScore gameScore) {
		try {
			gameScoreQueue.put(gameScore);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	private HumanPlayer getPlayerForId(PlayerId playerId) {
		return player.getId().equals(playerId) ? player : opponent;
	}
	
}
