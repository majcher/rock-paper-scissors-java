package pl.mmajcherski.rps.story.converter;

import java.lang.reflect.Type;

import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.impl.gesture.Paper;
import pl.mmajcherski.rps.domain.impl.gesture.Rock;
import pl.mmajcherski.rps.domain.impl.gesture.Scissors;

public class HandGestureConverter implements ParameterConverter {

	private static final String ROCK = "ROCK";
	private static final String PAPER = "PAPER";
	private static final String SCISSORS = "SCISSORS";

	@Override
	public boolean accept(Type type) {
		return HandGesture.class.isAssignableFrom((Class<?>) type);
	}

	@Override
	public Object convertValue(String value, Type type) {
		switch (value.toUpperCase()) {
		case ROCK:
			return Rock.INSTANCE;
		case PAPER:
			return Paper.INSTANCE;
		case SCISSORS:
			return Scissors.INSTANCE;
		default:
			throw new IllegalArgumentException("Gesture not supported");
		}
	}

}
