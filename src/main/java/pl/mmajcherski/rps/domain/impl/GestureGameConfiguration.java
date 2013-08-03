package pl.mmajcherski.rps.domain.impl;

public class GestureGameConfiguration {

	private static final int PLAYERS_LIMIT = 2;
	
	private final long playCount;
	private final long playDurationInMs;
	
	public static final class Builder {
		
		protected static final int DEFAULT_PLAY_COUNT = 1;
		protected static final long DEFAULT_PLAY_DURATION_MS = 3000;
		
		private int playCount = DEFAULT_PLAY_COUNT;
		private long playDurationInMs = DEFAULT_PLAY_DURATION_MS;
		
		public Builder withGamePlayCount(int gamePlayCount) {
			this.playCount = gamePlayCount;
			return this;
		}
		
		public Builder withGameDurationInMs(long gamePlayDurationInMs) {
			this.playDurationInMs = gamePlayDurationInMs;
			return this;
		}
		
		public GestureGameConfiguration build() {
			return new GestureGameConfiguration(this);
		}
		
	}
	
	private GestureGameConfiguration(Builder builder) {
		this.playCount = builder.playCount;
		this.playDurationInMs = builder.playDurationInMs;
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
	
}
