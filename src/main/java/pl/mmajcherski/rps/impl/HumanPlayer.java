package pl.mmajcherski.rps.impl;

import java.util.Objects;

import pl.mmajcherski.rps.Game;
import pl.mmajcherski.rps.GamePlayStatus;
import pl.mmajcherski.rps.HandGesture;
import pl.mmajcherski.rps.Player;

public class HumanPlayer implements Player {
	
	private PlayerId playerId;
	
	private HumanPlayer(PlayerId playerId) {
		this.playerId = playerId;
	}
	
	public static HumanPlayer withId(PlayerId playerId) {
		return new HumanPlayer(playerId);
	}

	@Override
	public PlayerId getId() {
		return null;
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
