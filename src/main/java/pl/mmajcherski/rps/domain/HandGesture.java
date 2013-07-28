package pl.mmajcherski.rps.domain;

public interface HandGesture {

	GamePlayStatus compareToGesture(HandGesture gesture);
	
}
