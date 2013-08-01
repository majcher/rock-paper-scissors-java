package pl.mmajcherski.rps.domain.impl.gesture;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.rps.domain.GamePlayStatus.LOOSE;
import static pl.mmajcherski.rps.domain.GamePlayStatus.TIE;
import static pl.mmajcherski.rps.domain.GamePlayStatus.WIN;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GamePlayStatus;

public class ScissorsTest {

	@Test
	public void shouldWinWithPaper() {
		// given
		Scissors scissors = Scissors.INSTANCE;
		Paper paper = Paper.INSTANCE;
		
		// when
		GamePlayStatus status = scissors.versus(paper);
		
		// then
		assertThat(status).isEqualTo(WIN);
	}
	
	@Test
	public void shouldTieWithScissors() {
		// given
		Scissors scissors = Scissors.INSTANCE;
		
		// when
		GamePlayStatus status = scissors.versus(scissors);
		
		// then
		assertThat(status).isEqualTo(TIE);
	}
	
	@Test
	public void shouldLooseWithRock() {
		// given
		Scissors scissors = Scissors.INSTANCE;
		Rock rock = Rock.INSTANCE;
		
		// when
		GamePlayStatus status = scissors.versus(rock);
		
		// then
		assertThat(status).isEqualTo(LOOSE);
	}
	
}
