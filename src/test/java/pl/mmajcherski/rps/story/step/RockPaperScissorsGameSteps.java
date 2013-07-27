package pl.mmajcherski.rps.story.step;

import java.util.HashMap;
import java.util.Map;

import org.fest.assertions.api.Assertions;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.mmajcherski.rps.Game;
import pl.mmajcherski.rps.GamePlayStatus;
import pl.mmajcherski.rps.HandGesture;
import pl.mmajcherski.rps.Player;
import pl.mmajcherski.rps.impl.RockPaperScissorsGame;

public class RockPaperScissorsGameSteps {

	private Game game;
	@Mock private Player player;
	@Mock private Player opponent;
	
	private Map<String, Player> playersMap = new HashMap<String, Player>();
	
	@Given("a RPS game with 2 players: $playerName and $opponentName")
	public void theGameIsRunning(String playerName, String opponentName) {
		MockitoAnnotations.initMocks(this);
		playersMap.put(playerName, player);
		playersMap.put(opponentName, opponent);
		
		game = RockPaperScissorsGame.withPlayers(player, opponent);
	}
	
	@When("$playerName shows $gestureName gesture")
	public void playerShowsGesture(String playerName, String gestureName) {
		Player activePlayer = playersMap.get(playerName);
		HandGesture gesture = HandGesture.valueOf(gestureName.toUpperCase());
		activePlayer.showGesture(gesture);
	}
	
	@Then("$playerName wins the game")
	public void playerWinsTheGame(String playerName) {
		Player activePlayer = playersMap.get(playerName);
		Mockito.when(activePlayer.getGamePlayStatus()).thenReturn(GamePlayStatus.WIN);
		Assertions.assertThat(activePlayer.getGamePlayStatus()).isEqualTo(GamePlayStatus.WIN);
	}
	
}
