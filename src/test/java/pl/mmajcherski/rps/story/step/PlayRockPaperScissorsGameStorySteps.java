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
import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.GestureGameController;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.PlayerGestureListener;
import pl.mmajcherski.rps.domain.impl.GamePlayResult;
import pl.mmajcherski.rps.domain.impl.GameScore;
import pl.mmajcherski.rps.domain.impl.GestureGame;
import pl.mmajcherski.rps.domain.impl.GestureGameConfiguration;
import pl.mmajcherski.rps.domain.impl.PlayerId;

public class PlayRockPaperScissorsGameStorySteps implements GameEventsListener {

	private static final long GAME_PLAY_PERIOD_IN_MS = 50;
	
	private PlayerId playerId;
	private PlayerId opponentId;
	
	private GestureGameConfiguration configuration;
	
	private BlockingQueue<GamePlayResult> gamePlayResultQueue = new LinkedBlockingQueue<>();
	private BlockingQueue<GameScore> gameScoreQueue = new LinkedBlockingQueue<>();
	
	@Given("a RPS game setup for $gamePlayCount play with 2 players: $playerId and $opponentId")
	@Alias("a RPS game setup for $gamePlayCount plays with 2 players: $playerId and $opponentId")
	public void startAGame(int gamePlayCount, PlayerId playerId, PlayerId opponentId) {
		this.playerId = playerId;
		this.opponentId = opponentId;
		
		configuration = new GestureGameConfiguration.Builder()
				.withHumanPlayer(playerId)
				.withHumanPlayer(opponentId)
				.withGameDurationInMs(GAME_PLAY_PERIOD_IN_MS)
				.withGamePlayCount(gamePlayCount)
				.withGameEventsListener(this)
				.build();
		
		new GestureGame(configuration).start();
	}
	
	@When("$playerId shows $gestureName gesture")
	public void playerShowsGesture(PlayerId playerId, HandGesture gesture) {
		GestureGameController controller = configuration.getGameControllerForPlayer(playerId);
		controller.showGesture(gesture);
	}
	
	@When("both players show <gesture> gesture")
	public void bothPlayersShowSameGesture(@Named("gesture") HandGesture gesture) {
		playerShowsGesture(playerId, gesture);
		playerShowsGesture(opponentId, gesture);
	}
	
	@Then("$winnerId wins the play")
	public void playerWinsThePlay(PlayerId winnerId) throws InterruptedException {
		Player looser = configuration.getPlayers().getOpponentOf(winnerId);
		
		GamePlayResult gamePlayResult = gamePlayResultQueue.take();
		
		GamePlayStatus winnerGamePlayStatus = gamePlayResult.getGamePlayStatusOf(winnerId);
		GamePlayStatus looserGamePlayStatus = gamePlayResult.getGamePlayStatusOf(looser.getId());
		
		assertThat(winnerGamePlayStatus).isEqualTo(GamePlayStatus.WIN);
		assertThat(looserGamePlayStatus).isEqualTo(GamePlayStatus.LOOSE);
	}
	
	@Then("there is no play winner")
	public void nobodyWinsThePlay() throws InterruptedException {
		GamePlayResult gamePlayResult = gamePlayResultQueue.take();
		
		GamePlayStatus playerGamePlayStatus = gamePlayResult.getGamePlayStatusOf(playerId);
		GamePlayStatus opponentGamePlayStatus = gamePlayResult.getGamePlayStatusOf(opponentId);
		
		assertThat(playerGamePlayStatus).isEqualTo(GamePlayStatus.TIE);
		assertThat(opponentGamePlayStatus).isEqualTo(GamePlayStatus.TIE);
	}
	
	@Then("the game score is $expectedPlayerScore:$expectedOpponentScore")
	@Alias("there is a tie $expectedPlayerScore:$expectedOpponentScore")
	public void gameScoreIs(int expectedPlayerScore, int expectedOpponentScore) throws InterruptedException {
		GameScore gameScore = gameScoreQueue.take();
		int playerScore = gameScore.of(playerId);
		int opponentScore = gameScore.of(opponentId);
		
		assertThat(playerScore).isEqualTo(expectedPlayerScore);
		assertThat(opponentScore).isEqualTo(expectedOpponentScore);
	}
	
	@Override
	public void onGamePlayStarted(PlayerGestureListener gameController) {
	}

	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameScore gameScore) {
		try {
			gamePlayResultQueue.put(gamePlayResult);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void onGameOver(GameScore gameScore) {
		try {
			gameScoreQueue.put(gameScore);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
}
