package pl.mmajcherski.rps.ui;

import java.util.Arrays;
import java.util.List;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.impl.Paper;
import pl.mmajcherski.rps.domain.gesture.impl.Rock;
import pl.mmajcherski.rps.domain.gesture.impl.Scissors;
import pl.mmajcherski.rps.domain.player.Player;

public class GestureGameController {

	private static final int GAME_DURATION_MS = 2000;
	private static final int GAME_PAUSE_MS = 1000;
	
	private GameMode gameMode;
	private GestureGame game;
	
	public void initGame(GameMode gameMode) {
		this.gameMode = gameMode;
		
		GameConfiguration configuration = new GameConfiguration.Builder()
			.withGamePlayCount(10)
			.withGameDurationInMs(GAME_DURATION_MS)
			.withPauseDurationInMs(GAME_PAUSE_MS)
			.build();
		
		game = new GestureGame(configuration);
	}
	
	public GameMode getGameMode() {
		return gameMode;
	}
	
	public void registerListener(GameEventsListener gameEventsListener) {
		game.registerEventsListener(gameEventsListener);
	}
	
	public void addPlayer(Player player) {
		player.join(game);
	}
	
	public void startGame() {
		game.start();
	}
	
	public int getGameDurationMs() {
		return GAME_DURATION_MS;
	}

	public List<Gesture> getGestures() {
		return Arrays.asList(new Gesture[] {Rock.INSTANCE, Paper.INSTANCE, Scissors.INSTANCE});
	}
	
}
