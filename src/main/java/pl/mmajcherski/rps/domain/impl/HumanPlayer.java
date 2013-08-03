package pl.mmajcherski.rps.domain.impl;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import pl.mmajcherski.rps.domain.GestureGameController;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.PlayerGestureListener;

public final class HumanPlayer implements Player, GestureGameController {
	
	private final PlayerId playerId;
	private PlayerGestureListener game;
	
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
		this.game = game;
		
		game.add(this);
	}

	@Override
	public void showGesture(HandGesture gesture) {
		checkPlayerHasJoinedAGame();
		
		game.onPlayerGesture(playerId, gesture);
	}
	
	private void checkPlayerHasJoinedAGame() {
		if (game == null) {
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
