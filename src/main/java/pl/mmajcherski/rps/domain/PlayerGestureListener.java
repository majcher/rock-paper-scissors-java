package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.PlayerId;

public interface PlayerGestureListener {

	void onPlayerGesture(PlayerId playerId, Gesture gesture);

}
