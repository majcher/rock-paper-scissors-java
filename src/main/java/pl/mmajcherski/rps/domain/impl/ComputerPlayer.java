package pl.mmajcherski.rps.domain.impl;

import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.PlayerGestureListener;

public final class ComputerPlayer implements Player, GameEventsListener {

	private ComputerPlayer() {
	}

	public static ComputerPlayer withId(PlayerId playerId) {
		return null;
	}

	@Override
	public PlayerId getId() {
		return null;
	}
	
	@Override
	public void join(GestureGame game) {
	}

	@Override
	public void onGamePlayStarted(PlayerGestureListener gameController) {
	}

	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameScore gameScore) {
	}

	@Override
	public void onGameOver(GameScore gameScore) {
	}

}
