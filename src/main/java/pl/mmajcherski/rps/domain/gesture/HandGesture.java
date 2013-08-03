package pl.mmajcherski.rps.domain.gesture;

import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.gesture.HandGesture;

public interface HandGesture {

	GamePlayStatus versus(HandGesture gesture);
	
}
