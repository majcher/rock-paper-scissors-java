package pl.mmajcherski.rps.domain.player.impl;

import static java.util.Objects.requireNonNull;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.PlayerGestureListener;
import pl.mmajcherski.rps.domain.gesture.GestureRandomiser;
import pl.mmajcherski.rps.domain.listener.OnGamePlayStartedListener;
import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;

public final class ComputerPlayer implements Player {

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
		
		game.addEventListener(new OnGamePlayStartedListener() {
			
			@Override
			public void onGamePlayStarted(GameConfiguration configuration) {
				long gamePlayDuration = configuration.getPlayDurationInMs();
				long gestureDelayInMs = calculateGestureDelay(gamePlayDuration);
				
				showRandomGestureWithDelay(gestureDelayInMs);
			}
			
		});
	}
	
	private long calculateGestureDelay(long gamePlayDuration) {
		double gestureDelayMultiplier = MIN_GESTURE_DELAY_MULTIPLIER + ((1 - MIN_GESTURE_DELAY_MULTIPLIER) * timeRandomiser.nextDouble());
		long gestureDelayInMs = (long) (gestureDelayMultiplier * gamePlayDuration);
		return gestureDelayInMs;
	}
	
	private void showRandomGestureWithDelay(final long gestureDelayInMs) {
		executor.schedule(new Runnable() {
			
			@Override
			public void run() {
				playerGestureListener.onPlayerGesture(playerId, gestureRandomiser.getRandomGesture());
			}
			
		}, gestureDelayInMs, TimeUnit.MILLISECONDS);
	}

}
