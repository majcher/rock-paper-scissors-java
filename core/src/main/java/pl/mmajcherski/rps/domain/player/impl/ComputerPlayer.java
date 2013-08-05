package pl.mmajcherski.rps.domain.player.impl;

import static java.util.Objects.requireNonNull;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.PlayerGestureListener;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.GestureRandomiser;
import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;

public final class ComputerPlayer implements Player, GameEventsListener {

	private static final double MIN_GESTURE_DELAY_MULTIPLIER = 0.5;
	
	private final PlayerId playerId;
	private final GestureRandomiser gestureRandomiser;
	private final Random timeRandomiser = new Random();
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	private PlayerGestureListener playerGestureListener;
	
	private ComputerPlayer(Builder builder) {
		requireNonNull(builder.playerId, "No player ID given");
		requireNonNull(builder.gestureRandomiser, "No gesture randomiser given");
		
		this.playerId = builder.playerId;
		this.gestureRandomiser = builder.gestureRandomiser;
	}

	public static class Builder {
		private PlayerId playerId;
		private GestureRandomiser gestureRandomiser;
		
		public Builder withId(PlayerId playerId) {
			this.playerId = playerId;
			return this;
		}
		
		public Builder withGestureRandomiser(GestureRandomiser gestureRandomiser) {
			this.gestureRandomiser = gestureRandomiser;
			return this;
		}
		
		public ComputerPlayer build() {
			return new ComputerPlayer(this);
		}
	}

	@Override
	public PlayerId getId() {
		return playerId;
	}
	
	@Override
	public void join(GestureGame game) {
		this.playerGestureListener = game;
		
		game.add(this);
		game.registerEventsListener(this);
	}

	@Override
	public void onGamePlayStarted(GameConfiguration configuration) {
		double gestureDelayMultiplier = MIN_GESTURE_DELAY_MULTIPLIER + ((1 - MIN_GESTURE_DELAY_MULTIPLIER) * timeRandomiser.nextDouble());
		long gestureDelayInMs = (long) (gestureDelayMultiplier * configuration.getPlayDurationInMs());
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				playerGestureListener.onPlayerGesture(playerId, gestureRandomiser.getRandomGesture());
			}
		}, gestureDelayInMs, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void onPlayerGestureShown(PlayerId playerId, Gesture gesture) {
	}

	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore) {
	}

	@Override
	public void onGameOver(GameFinalScore gameScore) {
	}

}
