package pl.mmajcherski.rps.story.step;

import org.fest.assertions.api.Assertions;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import pl.mmajcherski.rps.Game;
import pl.mmajcherski.rps.GamePlayStatus;
import pl.mmajcherski.rps.HandGesture;
import pl.mmajcherski.rps.Player;
import pl.mmajcherski.rps.impl.HumanPlayer;
import pl.mmajcherski.rps.impl.PlayerId;
import pl.mmajcherski.rps.impl.RockPaperScissorsGame;

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
		activePlayer.showGesture(gesture);
	}
	
	@Then("$playerId wins the game")
	public void playerWinsTheGame(PlayerId playerId) {
		Player activePlayer = game.getPlayerById(playerId);
		Assertions.assertThat(activePlayer.getGamePlayStatus()).isEqualTo(GamePlayStatus.WIN);
	}
	
	@Then("$playerId loses the game")
	public void playerLosesTheGame(PlayerId playerId) {
		Player activePlayer = game.getPlayerById(playerId);
		Assertions.assertThat(activePlayer.getGamePlayStatus()).isEqualTo(GamePlayStatus.LOOSE);
	}
	
}
