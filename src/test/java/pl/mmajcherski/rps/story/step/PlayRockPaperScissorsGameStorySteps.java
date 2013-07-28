package pl.mmajcherski.rps.story.step;

import static org.fest.assertions.api.Assertions.assertThat;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import pl.mmajcherski.rps.domain.Game;
import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.impl.HumanPlayer;
import pl.mmajcherski.rps.domain.impl.PlayerId;
import pl.mmajcherski.rps.domain.impl.RockPaperScissorsGame;

public class PlayRockPaperScissorsGameStorySteps {

	private Game game;
	private Player player;
	private Player opponent;
	
	@Given("a RPS game with 2 players: $playerId and $opponentId")
	public void theGameIsRunning(PlayerId playerId, PlayerId opponentId) {
		game = new RockPaperScissorsGame();
		
		player = HumanPlayer.withId(playerId);
		opponent = HumanPlayer.withId(opponentId);
		
		player.join(game);
		opponent.join(game);
	}
	
	@When("$playerId shows $gestureName gesture")
	public void playerShowsGesture(PlayerId playerId, HandGesture gesture) {
		Player activePlayer = game.getPlayerById(playerId);
		activePlayer.readyToPlay();
		activePlayer.showGesture(gesture);
	}
	
	@When("both players shows <gesture> gesture")
	public void playerShowsGesture(@Named("gesture") HandGesture gesture) {
		for (Player activePlayer : new Player[] {player, opponent}) {
			activePlayer.readyToPlay();
			activePlayer.showGesture(gesture);
		}
	}
	
	@Then("$playerId wins the game")
	public void playerWinsTheGame(PlayerId playerId) {
		Player activePlayer = game.getPlayerById(playerId);
		GamePlayStatus gamePlayStatus = activePlayer.getGamePlayStatus();
		assertThat(gamePlayStatus).isEqualTo(GamePlayStatus.WIN);
	}
	
	@Then("$playerId looses the game")
	public void playerLosesTheGame(PlayerId playerId) {
		Player activePlayer = game.getPlayerById(playerId);
		GamePlayStatus gamePlayStatus = activePlayer.getGamePlayStatus();
		assertThat(gamePlayStatus).isEqualTo(GamePlayStatus.LOOSE);
	}
	
	@Then("there is a tie")
	public void thereIsATie() {
		for (Player activePlayer : new Player[] {player, opponent}) {
			GamePlayStatus gamePlayStatus = activePlayer.getGamePlayStatus();
			assertThat(gamePlayStatus).isEqualTo(GamePlayStatus.TIE);
		}
	}
	
}
