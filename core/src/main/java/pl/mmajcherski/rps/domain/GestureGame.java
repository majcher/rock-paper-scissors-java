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
import pl.mmajcherski.rps.domain.listener.OnGameOverListener;
import pl.mmajcherski.rps.domain.listener.OnGamePlayResultListener;
import pl.mmajcherski.rps.domain.listener.OnGamePlayStartedListener;
import pl.mmajcherski.rps.domain.listener.OnGameStartedListener;
import pl.mmajcherski.rps.domain.listener.OnPlayerGestureShownListener;
import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.domain.player.Players;

public class GestureGame implements PlayerGestureListener, Runnable {

	private final GameConfiguration configuration;

	private final Players players = new Players();
	private final Map<PlayerId, Gesture> playerGestures = new ConcurrentHashMap<>();
	private final AtomicInteger currentPlay = new AtomicInteger(0);
	private GameFinalScore gameScore;
	private ExecutorService executor;
	
	private List<OnGameStartedListener> onGameStartedListeners = new ArrayList<>();
	private List<OnGamePlayStartedListener> onGamePlayStartedListeners = new ArrayList<>();
	private List<OnGamePlayResultListener> onGamePlayResultListeners = new ArrayList<>();
	private List<OnGameOverListener> onGameOverListeners = new ArrayList<>();
	private List<OnPlayerGestureShownListener> onPlayerGestureShownListeners = new ArrayList<>();
	
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
	
	public void start() {
		checkAllPlayersJoinedTheGame();
		
		resetGame();
		
		for (int i=0; i<configuration.getPlayCount(); i++) {
			executor.execute(this);
		}
		
		fireGameStartedEvent();
	}
	
	public void stop() {
		if (executor != null) {
			executor.shutdownNow();
		}
	}
	
	private void resetGame() {
		currentPlay.set(0);
		gameScore = new GameFinalScore(players);
		executor = Executors.newSingleThreadExecutor();
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
		
		sleepForPauseDuration();
	}
	
	@Override
	public void onPlayerGesture(PlayerId playerId, Gesture gesture) {
		playerGestures.put(playerId, gesture);
		
		firePlayerGestureShownEvent(playerId, gesture);
	}

	private void sleepForGamePlayDuration() {
		sleepFor(configuration.getPlayDurationInMs());
	}
	
	private void sleepForPauseDuration() {
		sleepFor(configuration.getPauseDuration());
	}
	
	private void sleepFor(long durationInMs) {
		try {
			Thread.sleep(durationInMs);
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
		
		if (playerGesture == null && opponentGesture == null) {
			return GamePlayStatus.TIE;
		}
		
		if (playerGesture == null) {
			return GamePlayStatus.LOOSE;
		}
		
		if (opponentGesture == null) {
			return GamePlayStatus.WIN;
		}
		
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
	
	public void addEventListener(OnGameStartedListener onGameStartedListener) {
		this.onGameStartedListeners.add(onGameStartedListener);
	}
	
	public void addEventListener(OnGamePlayStartedListener onGamePlayStartedListener) {
		this.onGamePlayStartedListeners.add(onGamePlayStartedListener);
	}
	
	public void addEventListener(OnGamePlayResultListener onGamePlayResultListener) {
		this.onGamePlayResultListeners.add(onGamePlayResultListener);
	}
	
	public void addEventListener(OnGameOverListener onGameOverListener) {
		this.onGameOverListeners.add(onGameOverListener);
	}
	
	public void addEventListener(OnPlayerGestureShownListener onPlayerGestureShownListener) {
		this.onPlayerGestureShownListeners.add(onPlayerGestureShownListener);
	}
	
	private void fireGameStartedEvent() {
		for (OnGameStartedListener listener : onGameStartedListeners) {
			listener.onGameStarted(configuration);
		}
	}
	
	private void fireGamePlayStartedEvent() {
		for (OnGamePlayStartedListener listener : onGamePlayStartedListeners) {
			listener.onGamePlayStarted(configuration);
		}
	}
	
	private void firePlayerGestureShownEvent(PlayerId playerId, Gesture gesture) {
		for (OnPlayerGestureShownListener listener : onPlayerGestureShownListeners) {
			listener.onPlayerGestureShown(playerId, gesture);
		}
	}
	
	private void fireGamePlayResultEvent(GamePlayResult gamePlayResult) {
		for (OnGamePlayResultListener listener : onGamePlayResultListeners) {
			listener.onGamePlayResult(gamePlayResult, gameScore);
		}
	}
	
	private void fireGameOverEvent() {
		for (OnGameOverListener listener : onGameOverListeners) {
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
