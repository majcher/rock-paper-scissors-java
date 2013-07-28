package pl.mmajcherski.rps.story.converter;

import java.lang.reflect.Type;

import org.jbehave.core.steps.ParameterConverters.EnumConverter;

public class EnumCaseInsensitiveConverter extends EnumConverter {

	@Override
	public Object convertValue(String value, Type type) {
		return super.convertValue(value.toUpperCase(), type);
	}

}
