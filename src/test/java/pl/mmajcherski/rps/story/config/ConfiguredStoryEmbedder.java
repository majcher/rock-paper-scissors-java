package pl.mmajcherski.rps.story.config;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;

import pl.mmajcherski.rps.story.converter.EnumCaseInsensitiveConverter;
import pl.mmajcherski.rps.story.converter.PlayerIdConverter;

public class ConfiguredStoryEmbedder extends Embedder {
	
	private Object[] stepsInstances;
	
	public ConfiguredStoryEmbedder(Object... stepsInstances) {
		this.stepsInstances = stepsInstances;
	}
	
	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration()
			.useStoryLoader(new LoadFromClasspath())
			.useParameterConverters(new ParameterConverters().addConverters(
					new PlayerIdConverter(), new EnumCaseInsensitiveConverter()))
			.useStoryReporterBuilder(new StoryReporterBuilder()
				.withFormats(Format.CONSOLE, Format.HTML_TEMPLATE)
			);
	}
	
	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), stepsInstances);
	}
	
}
