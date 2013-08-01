package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.impl.PlayerId;

public interface PlayerGestureControllable {

	void onPlayerGesture(PlayerId playerId, HandGesture gesture);

}
