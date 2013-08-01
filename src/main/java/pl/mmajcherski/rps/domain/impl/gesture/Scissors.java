package pl.mmajcherski.rps.domain.impl.gesture;

import java.util.HashSet;
import java.util.Set;

import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.HandGesture;

public enum Scissors implements HandGesture {

	INSTANCE;

	private static final Set<Class<? extends HandGesture>> LOOSES_WITH = new HashSet<>();
	static {
		LOOSES_WITH.add(Rock.class);
	}

	@Override
	public GamePlayStatus versus(final HandGesture gesture) {
		if (gesture == this) {
			return GamePlayStatus.TIE;
		}

		return (LOOSES_WITH.contains(gesture.getClass())) ? GamePlayStatus.LOOSE : GamePlayStatus.WIN;
	}

}
