package pl.mmajcherski.rps.domain.player.impl;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.PlayerGestureListener;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;

public final class HumanPlayer implements Player {
	
	private final PlayerId playerId;
	private PlayerGestureListener playerGestureListener;
	
	private HumanPlayer(final PlayerId playerId) {
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
	public void join(GestureGame game) {
		this.playerGestureListener = game;
		
		game.add(this);
	}

	public void showGesture(Gesture gesture) {
		checkPlayerHasJoinedAGame();
		
		playerGestureListener.onPlayerGesture(playerId, gesture);
	}
	
	private void checkPlayerHasJoinedAGame() {
		if (playerGestureListener == null) {
			throw new IllegalStateException("Player must join game first");
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
