package pl.mmajcherski.rps;

import pl.mmajcherski.rps.impl.PlayerId;

public interface Player {

	PlayerId getId();

	void join(Game game);

	void showGesture(HandGesture gesture);

	GamePlayStatus getGamePlayStatus();

}
