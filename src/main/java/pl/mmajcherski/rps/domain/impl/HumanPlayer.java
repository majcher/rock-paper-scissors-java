package pl.mmajcherski.rps.domain.impl;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import pl.mmajcherski.rps.domain.Game;
import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;

public class HumanPlayer implements Player {
	
	private final PlayerId playerId;
	private Game game;
	private HandGesture gestureShown;
	
	private HumanPlayer(PlayerId playerId) {
		requireNonNull(playerId, "Player must be given non-null ID");
		
		this.playerId = playerId;
	}
	
	public static HumanPlayer withId(PlayerId playerId) {
		return new HumanPlayer(playerId);
	}

	@Override
	public PlayerId getId() {
		return playerId;
	}

	@Override
	public void join(Game game) {
		requireNonNull(game, "Cannot join game which is null");
		
		this.game = game;
		
		game.accept(this);
	}
	
	@Override
	public void readyToPlay() {
		requireJoinedGame();
		
		game.onPlayerReadyToPlay(playerId);
	}

	@Override
	public void showGesture(HandGesture gesture) {
		gestureShown = gesture;
	}
	
	@Override
	public HandGesture getGestureShown() {
		return gestureShown;
	}
	
	@Override
	public GamePlayStatus getGamePlayStatus() {
		requireJoinedGame();
		
		return game.getGamePlayStatusFor(playerId);
	}
	
	private void requireJoinedGame() {
		if (game == null) {
			throw new IllegalStateException("Player must first join a game");
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(playerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		HumanPlayer other = (HumanPlayer) obj;
		return Objects.equals(this.playerId, other.playerId);
	}

	@Override
	public String toString() {
		return "HumanPlayer [playerId=" + playerId + "]";
	}

}
