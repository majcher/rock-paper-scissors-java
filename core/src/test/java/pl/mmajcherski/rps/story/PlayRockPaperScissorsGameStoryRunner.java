package pl.mmajcherski.rps.story;

import java.util.Collections;

import org.jbehave.core.embedder.Embedder;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.story.config.ConfiguredStoryEmbedder;
import pl.mmajcherski.rps.story.step.PlayRockPaperScissorsGameStorySteps;

public class PlayRockPaperScissorsGameStoryRunner {
	
	@Test
	public void shouldFulfilAllAcceptanceCriteriaOfSinglePlayOfRockPaperScissorsGameStory() {
		runStory("play_rock_paper_scissors_game.story", new PlayRockPaperScissorsGameStorySteps());
	}
	
	protected void runStory(String storyName, Object storySteps) {
		final String storyPath = getThisPackagePath() + "/" + storyName;
		final Embedder embedder = new ConfiguredStoryEmbedder(storySteps);
		embedder.runStoriesAsPaths(Collections.singletonList(storyPath));
	}

	private String getThisPackagePath() {
		return getClass().getPackage().getName().replace('.', '/');
	}
	
}
