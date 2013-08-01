package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.impl.GamePlayResult;
import pl.mmajcherski.rps.domain.impl.GameScore;

public interface GameEventsListener {

	void onGamePlayStarted(PlayerGestureControllable gameController);

	void onGamePlayResult(GamePlayResult gamePlayResult, GameScore gameScore);

	void onGameOver(GameScore gameScore);

}
