package pl.mmajcherski.rps;

public interface Player {

	void showGesture(HandGesture gesture);

	GamePlayStatus getGamePlayStatus();

}
