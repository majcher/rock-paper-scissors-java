package pl.mmajcherski.rps.domain;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.domain.player.Players;

public class GestureGame implements PlayerGestureListener, Runnable {

	private final GameConfiguration configuration;

	private final Players players = new Players();
	private final Map<PlayerId, Gesture> playerGestures = new ConcurrentHashMap<>();
	private final GameFinalScore gameScore;
	
	private final List<GameEventsListener> gameEventsListeners = new ArrayList<>();
	
	private final AtomicInteger currentPlay = new AtomicInteger(0);
	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	public GestureGame(GameConfiguration configuration) {
		requireNonNull(configuration, "Game configuration not provided");
		
		this.configuration = configuration;
		this.gameScore = new GameFinalScore(players);
	}
	
	public void add(Player player) {
		requireNonNull(player, "Player must not be null");
		checkGameCanAcceptPlayer(player);
		
		players.add(player);
	}
	
	public void registerEventsListener(GameEventsListener gameEventsListener) {
		this.gameEventsListeners.add(gameEventsListener);
	}
	
	public void start() {
		checkAllPlayersJoinedTheGame();
		
		for (int i=0; i<configuration.getPlayCount(); i++) {
			executor.execute(this);
		}
	}
	
	@Override
	public void run() {
		playerGestures.clear();
		
		currentPlay.incrementAndGet();
		
		fireGamePlayStartedEvent();

		sleepForGamePlayDuration();
		
		rate();
		
		if (isGameOver()) {
			fireGameOverEvent();
			return;
		}
	}
	
	@Override
	public void onPlayerGesture(PlayerId playerId, Gesture gesture) {
		playerGestures.put(playerId, gesture);
	}

	private void sleepForGamePlayDuration() {
		try {
			Thread.sleep(configuration.getPlayDurationInMs());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	private void rate() {
		GamePlayResult gamePlayResult = new GamePlayResult();
		
		for (Player player : players) {
			GamePlayStatus gamePlayStatus = checkGamePlayStatus(player.getId());
			updatePlayerScoreWithGamePlayStatus(player.getId(), gamePlayStatus);
			gamePlayResult.addPlayerGamePlayStatus(player.getId(), gamePlayStatus);
		}
		
		fireGamePlayResultEvent(gamePlayResult);
	}

	private GamePlayStatus checkGamePlayStatus(PlayerId playerId) {
		Player opponent = players.getOpponentOf(playerId);
		
		Gesture playerGesture = playerGestures.get(playerId);
		Gesture opponentGesture = playerGestures.get(opponent.getId());
		
		// handle not shown gestures - faults
		
		return playerGesture.versus(opponentGesture);
	}
	
	private void updatePlayerScoreWithGamePlayStatus(PlayerId playerId, GamePlayStatus gamePlayStatus) {
		if (gamePlayStatus == GamePlayStatus.WIN) {
			gameScore.increaseScoreForPlayer(playerId);
		}
	}
	
	private boolean isGameOver() {
		return currentPlay.get() >= configuration.getPlayCount();
	}
	
	private void fireGamePlayStartedEvent() {
		for (GameEventsListener listener : gameEventsListeners) {
			listener.onGamePlayStarted(this);
		}
	}
	
	private void fireGamePlayResultEvent(GamePlayResult gamePlayResult) {
		for (GameEventsListener listener : gameEventsListeners) {
			listener.onGamePlayResult(gamePlayResult, gameScore);
		}
	}
	
	private void fireGameOverEvent() {
		for (GameEventsListener listener : gameEventsListeners) {
			listener.onGameOver(gameScore);
		}
	}
	
	private void checkGameCanAcceptPlayer(Player player) {
		checkPlayersSizeLimitReached();
		checkThatGameDoesNotHavePlayer(player.getId());
	}
	
	private void checkPlayersSizeLimitReached() {
		if (players.size() >= configuration.getPlayersLimit()) {
			throw new IllegalStateException(String.format("Game has a limit of %d players", configuration.getPlayersLimit()));
		}
	}
	
	private void checkThatGameDoesNotHavePlayer(PlayerId playerId) {
		if (players.contains(playerId)) {
			throw new IllegalArgumentException(String.format("Player with ID: %s has already joined the game", playerId));
		}
	}
	
	private void checkAllPlayersJoinedTheGame() {
		if (players.size() < configuration.getPlayersLimit()) {
			throw new IllegalStateException("Not all players joined the game");
		}
	}
	
}
