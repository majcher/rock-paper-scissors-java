package pl.mmajcherski.rps.domain.gesture.impl;

import java.util.Random;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.GestureRandomiser;

public class SimpleGestureRandomiser implements GestureRandomiser {

	private final Random random = new Random();
	private final Gesture[] gestures;
	
	public SimpleGestureRandomiser(Gesture... gestures) {
		this.gestures = gestures;
	}
	
	@Override
	public Gesture getRandomGesture() {
		return gestures[random.nextInt(gestures.length)];
	}
	
}
