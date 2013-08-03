package pl.mmajcherski.rps.domain.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GestureGameController;
import pl.mmajcherski.rps.domain.Players;

public class GestureGameConfiguration {

	private static final int PLAYERS_LIMIT = 2;
	
	private final long playCount;
	private final long playDurationInMs;
	private final Players players;
	private final Map<PlayerId, GestureGameController> gameControllers;
	private final List<GameEventsListener> gameEventsListeners;
	
	public static final class Builder {
		
		private int playCount;
		private long playDurationInMs;
		private final Players players = new Players();
		private final Map<PlayerId, GestureGameController> gameControllers = new HashMap<>();
		private final List<GameEventsListener> gameEventsListeners = new ArrayList<>();
		
		public Builder withHumanPlayer(PlayerId playerId) {
			checkGameCanAcceptPlayer(playerId);
			
			HumanPlayer player = HumanPlayer.withId(playerId);
			players.add(player);
			gameControllers.put(playerId, player);
			gameEventsListeners.add(player);
			return this;
		}
		
		public Builder withComputerPlayer(PlayerId playerId) {
			checkGameCanAcceptPlayer(playerId);
			
			ComputerPlayer computerPlayer = ComputerPlayer.withId(playerId);
			players.add(computerPlayer);
			gameEventsListeners.add(computerPlayer);
			return this;
		}
		
		public Builder withGamePlayCount(int gamePlayCount) {
			this.playCount = gamePlayCount;
			return this;
		}
		
		public Builder withGameDurationInMs(long gamePlayDurationInMs) {
			this.playDurationInMs = gamePlayDurationInMs;
			return this;
		}
		
		public Builder withGameEventsListener(GameEventsListener gameEventsListener) {
			this.gameEventsListeners.add(gameEventsListener);
			return this;
		}
		
		public GestureGameConfiguration build() {
			checkAllPlayersJoinedTheGame();
			
			return new GestureGameConfiguration(this);
		}
		
		private void checkGameCanAcceptPlayer(PlayerId playerId) {
			checkPlayersSizeLimitReached();
			checkThatGameDoesNotHavePlayer(playerId);
		}

		private void checkPlayersSizeLimitReached() {
			if (players.size() >= PLAYERS_LIMIT) {
				throw new IllegalStateException(String.format("Game has a limit of %d players", PLAYERS_LIMIT));
			}
		}
		
		private void checkThatGameDoesNotHavePlayer(PlayerId playerId) {
			if (players.contains(playerId)) {
				throw new IllegalArgumentException(String.format("Player with ID: %s has already joined the game", playerId));
			}
		}
		
		private void checkAllPlayersJoinedTheGame() {
			if (players.size() < PLAYERS_LIMIT) {
				throw new IllegalStateException("Not all players joined the game");
			}
		}
		
	}
	
	private GestureGameConfiguration(Builder builder) {
		this.players = builder.players;
		this.gameControllers = builder.gameControllers;
		this.playCount = builder.playCount;
		this.playDurationInMs = builder.playDurationInMs;
		this.gameEventsListeners = builder.gameEventsListeners;
	}
	
	public long getPlayCount() {
		return playCount;
	}
	
	public long getPlayDurationInMs() {
		return playDurationInMs;
	}
	
	public Players getPlayers() {
		return players;
	}
	
	public GestureGameController getGameControllerForPlayer(PlayerId playerId) {
		return gameControllers.get(playerId);
	}
	
	public List<GameEventsListener> getGameEventsListeners() {
		return Collections.unmodifiableList(gameEventsListeners);
	}
	
}
