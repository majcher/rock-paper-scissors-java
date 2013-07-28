package pl.mmajcherski.rps.domain.impl;

import java.util.Objects;

import pl.mmajcherski.rps.domain.Game;
import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;

public class HumanPlayer implements Player {
	
	private final PlayerId playerId;
	
	private HumanPlayer(PlayerId playerId) {
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
		// TODO Auto-generated method stub
	}

	@Override
	public void showGesture(HandGesture gesture) {
		// TODO Auto-generated method stub
	}

	@Override
	public GamePlayStatus getGamePlayStatus() {
		// TODO Auto-generated method stub
		return null;
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
