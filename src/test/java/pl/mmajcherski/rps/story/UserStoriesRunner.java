package pl.mmajcherski.rps.story;

import java.util.Collections;

import org.jbehave.core.embedder.Embedder;

import pl.mmajcherski.rps.story.config.ConfiguredStoryEmbedder;

public abstract class UserStoriesRunner {

	protected void runStory(String storyName, Object storySteps) {
		final String storyPath = getThisPackagePath() + "/" + storyName;
		final Embedder embedder = new ConfiguredStoryEmbedder(storySteps);
		embedder.runStoriesAsPaths(Collections.singletonList(storyPath));
	}

	private String getThisPackagePath() {
		return getClass().getPackage().getName().replace('.', '/');
	}

}
