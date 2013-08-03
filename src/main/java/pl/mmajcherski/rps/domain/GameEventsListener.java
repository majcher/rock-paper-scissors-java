package pl.mmajcherski.rps.domain;

public interface GameEventsListener {

	void onGamePlayStarted(PlayerGestureListener gameController);

	void onGamePlayResult(GamePlayResult gamePlayResult, GameScore gameScore);

	void onGameOver(GameScore gameScore);

}
