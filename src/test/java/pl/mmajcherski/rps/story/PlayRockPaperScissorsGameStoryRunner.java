package pl.mmajcherski.rps.story;

import java.util.Collections;

import org.jbehave.core.embedder.Embedder;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.story.config.ConfiguredStoryEmbedder;
import pl.mmajcherski.rps.story.step.RockPaperScissorsGameSteps;

public class PlayRockPaperScissorsGameStoryRunner {
	
	private static final String STORIES_PATH = "stories";
	
	@Test
	public void executePlayRockPaperScissorsGameStory() {
		// given
		final String storyPath = STORIES_PATH + "/play_rock_paper_scissors_game.story";
		final RockPaperScissorsGameSteps storySteps = new RockPaperScissorsGameSteps();
		final Embedder embedder = new ConfiguredStoryEmbedder(storySteps);
		
		// when
		embedder.runStoriesAsPaths(Collections.singletonList(storyPath));
	}

}
