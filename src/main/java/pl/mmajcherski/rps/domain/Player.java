package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.impl.PlayerId;

public interface Player {

	PlayerId getId();

	void join(Game game);

	void showGesture(HandGesture gesture);

	GamePlayStatus getGamePlayStatus();

}
