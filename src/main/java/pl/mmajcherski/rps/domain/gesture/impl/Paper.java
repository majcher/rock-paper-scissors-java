package pl.mmajcherski.rps.domain.gesture.impl;

import java.util.HashSet;
import java.util.Set;

import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.gesture.Gesture;

public enum Paper implements Gesture {

	INSTANCE;

	private static final Set<Class<? extends Gesture>> LOOSES_WITH = new HashSet<>();
	static {
		LOOSES_WITH.add(Scissors.class);
	}

	@Override
	public GamePlayStatus versus(final Gesture gesture) {
		if (gesture == this) {
			return GamePlayStatus.TIE;
		}

		return (LOOSES_WITH.contains(gesture.getClass())) ? GamePlayStatus.LOOSE : GamePlayStatus.WIN;
	}
	
}
