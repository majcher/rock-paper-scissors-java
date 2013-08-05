package pl.mmajcherski.rps.domain.gesture;

import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.gesture.Gesture;

public interface Gesture {

	String getName();
	
	GamePlayStatus versus(Gesture gesture);
	
}
