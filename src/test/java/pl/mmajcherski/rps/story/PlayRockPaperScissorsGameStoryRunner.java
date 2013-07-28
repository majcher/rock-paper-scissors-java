package pl.mmajcherski.rps.story;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.story.step.PlayRockPaperScissorsGameStorySteps;

public class PlayRockPaperScissorsGameStoryRunner extends UserStoriesRunner {
	
	@Test
	public void shouldFulfilAllAcceptanceCriteriaOfPlayRockPaperScissorsGameStory() {
		runStory("play_rock_paper_scissors_game.story", new PlayRockPaperScissorsGameStorySteps());
	}

}
