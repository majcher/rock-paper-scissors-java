package pl.mmajcherski.rps.story.converter;

import java.lang.reflect.Type;

import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import pl.mmajcherski.rps.domain.player.PlayerId;

public class PlayerIdConverter implements ParameterConverter {

	@Override
	public boolean accept(Type type) {
		return PlayerId.class.isAssignableFrom((Class<?>) type);
	}

	@Override
	public Object convertValue(String value, Type type) {
		return new PlayerId(value);
	}

}
