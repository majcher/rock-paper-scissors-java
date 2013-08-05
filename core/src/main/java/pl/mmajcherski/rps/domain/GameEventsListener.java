package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.PlayerId;

public interface GameEventsListener {

	void onGamePlayStarted(GameConfiguration configuration);

	void onPlayerGestureShown(PlayerId playerId, Gesture gesture);

	void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore);

	void onGameOver(GameFinalScore gameScore);

}
