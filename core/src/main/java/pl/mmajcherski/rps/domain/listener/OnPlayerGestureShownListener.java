package pl.mmajcherski.rps.domain.listener;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.PlayerId;

public interface OnPlayerGestureShownListener {

	void onPlayerGestureShown(PlayerId playerId, Gesture gesture);

}
