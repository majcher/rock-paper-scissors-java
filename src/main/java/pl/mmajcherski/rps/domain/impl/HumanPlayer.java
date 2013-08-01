package pl.mmajcherski.rps.domain.impl;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import pl.mmajcherski.rps.domain.GestureGameController;
import pl.mmajcherski.rps.domain.PlayerGestureControllable;
import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;

public class HumanPlayer implements Player, GestureGameController, GameEventsListener {
	
	private final PlayerId playerId;
	private PlayerGestureControllable game;
	
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
	public void showGesture(HandGesture gesture) {
		checkGameAlreadyStarted();
		
		game.onPlayerGesture(playerId, gesture);
	}
	
	@Override
	public void onGamePlayStarted(PlayerGestureControllable game) {
		this.game = game;
	}

	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameScore gameScore) {
		
	}

	@Override
	public void onGameOver(GameScore gameScore) {
		
	}
	
	private void checkGameAlreadyStarted() {
		if (game == null) {
			throw new IllegalStateException("Game not started");
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
