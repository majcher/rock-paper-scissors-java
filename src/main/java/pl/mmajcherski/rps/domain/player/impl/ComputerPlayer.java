package pl.mmajcherski.rps.domain.player.impl;

import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.GameScore;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.PlayerGestureListener;
import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;

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
