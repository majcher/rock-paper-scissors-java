package pl.mmajcherski.rps.domain;

public interface GameEventsListener {

	void onGamePlayStarted(PlayerGestureListener gameController);

	void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore);

	void onGameOver(GameFinalScore gameScore);

}
