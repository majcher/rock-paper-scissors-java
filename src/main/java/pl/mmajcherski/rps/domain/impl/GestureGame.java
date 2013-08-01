package pl.mmajcherski.rps.domain.impl;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.PlayerGestureControllable;

public class GestureGame implements PlayerGestureControllable, Runnable {

	private final GestureGameConfiguration configuration;

	private final Map<PlayerId, HandGesture> playerGestures = new ConcurrentHashMap<>();
	private final GameScore gameScore;
	
	private final AtomicInteger currentPlay = new AtomicInteger(0);
	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	public GestureGame(GestureGameConfiguration configuration) {
		requireNonNull(configuration, "Game configuration not provided");
		
		this.configuration = configuration;
		this.gameScore = new GameScore(configuration.getPlayers().getAllIds());
	}
	
	public void start() {
		executor.execute(this);
		playerGestures.clear();
		
		fireGamePlayStartedEvent();
	}
	
	@Override
	public void onPlayerGesture(PlayerId playerId, HandGesture gesture) {
		playerGestures.put(playerId, gesture);
	}

	@Override
	public void run() {
		currentPlay.incrementAndGet();

		sleepForGamePlayDuration();
		
		rate();
		
		if (isGameOver()) {
			fireGameOverEvent();
			return;
		}
		
		start();
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
		
		for (Player player : configuration.getPlayers()) {
			GamePlayStatus gamePlayStatus = checkGamePlayStatus(player.getId());
			updatePlayerScoreWithGamePlayStatus(player.getId(), gamePlayStatus);
			gamePlayResult.addPlayerGamePlayStatus(player.getId(), gamePlayStatus);
		}
		
		fireGamePlayResultEvent(gamePlayResult);
	}

	private GamePlayStatus checkGamePlayStatus(PlayerId playerId) {
		Player opponent = configuration.getPlayers().getOpponentOf(playerId);
		
		HandGesture playerGesture = playerGestures.get(playerId);
		HandGesture opponentGesture = playerGestures.get(opponent.getId());
		
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
		for (GameEventsListener listener : configuration.getGameEventsListeners()) {
			listener.onGamePlayStarted(this);
		}
	}
	
	private void fireGamePlayResultEvent(GamePlayResult gamePlayResult) {
		for (GameEventsListener listener : configuration.getGameEventsListeners()) {
			listener.onGamePlayResult(gamePlayResult, gameScore);
		}
	}
	
	private void fireGameOverEvent() {
		for (GameEventsListener listener : configuration.getGameEventsListeners()) {
			listener.onGameOver(gameScore);
		}
	}
	
}
