package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.impl.PlayerId;

public interface PlayerGestureListener {

	void onPlayerGesture(PlayerId playerId, HandGesture gesture);

}
