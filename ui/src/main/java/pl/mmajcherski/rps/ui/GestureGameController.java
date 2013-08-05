package pl.mmajcherski.rps.ui;

import java.util.HashMap;
import java.util.Map;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.impl.Paper;
import pl.mmajcherski.rps.domain.gesture.impl.Rock;
import pl.mmajcherski.rps.domain.gesture.impl.Scissors;
import pl.mmajcherski.rps.domain.listener.OnGameOverListener;
import pl.mmajcherski.rps.domain.listener.OnGamePlayResultListener;
import pl.mmajcherski.rps.domain.listener.OnGamePlayStartedListener;
import pl.mmajcherski.rps.domain.listener.OnGameStartedListener;
import pl.mmajcherski.rps.domain.listener.OnPlayerGestureShownListener;
import pl.mmajcherski.rps.domain.player.Player;

public class GestureGameController {

	public enum PlayerSide {
		LEFT,
		RIGHT
	}
	
	private static final int GAME_DURATION_MS = 2000;
	private static final int GAME_PAUSE_MS = 1000;
	
	private GameMode gameMode;
	private GestureGame game;
	private GameConfiguration configuration;
	private Map<PlayerSide, Player> players = new HashMap<>();
	
	public void setupGameInMode(GameMode gameMode) {
		this.gameMode = gameMode;
		
		configuration = new GameConfiguration.Builder()
			.withGamePlayCount(10)
			.withGameDurationInMs(GAME_DURATION_MS)
			.withPauseDurationInMs(GAME_PAUSE_MS)
			.withAvailableGestures(Rock.INSTANCE, Paper.INSTANCE, Scissors.INSTANCE)
			.build();
		
		game = new GestureGame(configuration);
	}
	
	public GameMode getGameMode() {
		return gameMode;
	}
	
	public void addPlayer(Player player, PlayerSide side) {
		player.join(game);
		players.put(side, player);
	}
	
	public void startGame() {
		game.start();
	}
	
	public long getGameDurationMs() {
		return configuration.getPlayDurationInMs();
	}

	public Gesture[] getAvailableGestures() {
		return configuration.getAvailableGestures();
	}
	
	public Player getPlayer(PlayerSide playerSide) {
		return players.get(playerSide);
	}
	
	public void addEventListener(OnGameStartedListener gameStartedListener) {
		game.addEventListener(gameStartedListener);
	}
	
	public void addEventListener(OnGamePlayStartedListener gamePlayStartedListener) {
		game.addEventListener(gamePlayStartedListener);
	}
	
	public void addEventListener(OnGamePlayResultListener gamePlayResultListener) {
		game.addEventListener(gamePlayResultListener);
	}
	
	public void addEventListener(OnGameOverListener gameOverListener) {
		game.addEventListener(gameOverListener);
	}
	
	public void addEventListener(OnPlayerGestureShownListener gameEventsListener) {
		game.addEventListener(gameEventsListener);
	}
	
}
