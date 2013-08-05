package pl.mmajcherski.rps.domain;

public class GameConfiguration {

	private static final int PLAYERS_LIMIT = 2;
	
	private final long playCount;
	private final long playDurationInMs;
	private final long pauseDurationInMs;
	
	public static final class Builder {
		
		protected static final int DEFAULT_PLAY_COUNT = 1;
		protected static final long DEFAULT_PLAY_DURATION_MS = 3000;
		protected static final long DEFAULT_PAUSE_DURATION_MS = 0;
		
		private int playCount = DEFAULT_PLAY_COUNT;
		private long playDurationInMs = DEFAULT_PLAY_DURATION_MS;
		private long pauseDurationInMs = DEFAULT_PAUSE_DURATION_MS;
		
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
		
		public GameConfiguration build() {
			return new GameConfiguration(this);
		}
		
	}
	
	private GameConfiguration(Builder builder) {
		this.playCount = builder.playCount;
		this.playDurationInMs = builder.playDurationInMs;
		this.pauseDurationInMs = builder.pauseDurationInMs;
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
	
}
