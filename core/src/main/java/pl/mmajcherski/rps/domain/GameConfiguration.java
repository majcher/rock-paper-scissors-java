package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.impl.Paper;
import pl.mmajcherski.rps.domain.gesture.impl.Rock;
import pl.mmajcherski.rps.domain.gesture.impl.Scissors;

public class GameConfiguration {

	private static final int PLAYERS_LIMIT = 2;
	
	private final long playCount;
	private final long playDurationInMs;
	private final long pauseDurationInMs;
	private final Gesture[] availableGestures;
	
	public static final class Builder {
		
		protected static final int DEFAULT_PLAY_COUNT = 1;
		protected static final long DEFAULT_PLAY_DURATION_MS = 3000;
		protected static final long DEFAULT_PAUSE_DURATION_MS = 0;
		protected static final Gesture[] DEFAULT_AVAILABLE_GESTURES = new Gesture[] {Rock.INSTANCE, Paper.INSTANCE, Scissors.INSTANCE};
		
		private int playCount = DEFAULT_PLAY_COUNT;
		private long playDurationInMs = DEFAULT_PLAY_DURATION_MS;
		private long pauseDurationInMs = DEFAULT_PAUSE_DURATION_MS;
		private Gesture[] availableGestures = DEFAULT_AVAILABLE_GESTURES;
		
		public Builder withGamePlayCount(int gamePlayCount) {
			this.playCount = gamePlayCount;
			return this;
		}
		
		public Builder withGameDurationInMs(long gamePlayDurationInMs) {
			this.playDurationInMs = gamePlayDurationInMs;
			return this;
		}
		
		public Builder withPauseDurationInMs(long pauseDurationInMs) {
			this.pauseDurationInMs  = pauseDurationInMs;
			return this;
		}
		
		public Builder withAvailableGestures(Gesture... gestures) {
			this.availableGestures = gestures;
			return this;
		}
		
		public GameConfiguration build() {
			return new GameConfiguration(this);
		}
		
	}
	
	private GameConfiguration(Builder builder) {
		this.playCount = builder.playCount;
		this.playDurationInMs = builder.playDurationInMs;
		this.pauseDurationInMs = builder.pauseDurationInMs;
		this.availableGestures = builder.availableGestures;
	}
	
	public int getPlayersLimit() {
		return PLAYERS_LIMIT;
	}
	
	public long getPlayCount() {
		return playCount;
	}
	
	public long getPlayDurationInMs() {
		return playDurationInMs;
	}

	public long getPauseDuration() {
		return pauseDurationInMs;
	}
	
	public Gesture[] getAvailableGestures() {
		return availableGestures;
	}
	
}
