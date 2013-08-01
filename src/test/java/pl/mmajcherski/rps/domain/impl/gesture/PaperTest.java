package pl.mmajcherski.rps.domain.impl.gesture;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.rps.domain.GamePlayStatus.LOOSE;
import static pl.mmajcherski.rps.domain.GamePlayStatus.TIE;
import static pl.mmajcherski.rps.domain.GamePlayStatus.WIN;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GamePlayStatus;

public class PaperTest {

	@Test
	public void shouldWinWithRock() {
		// given
		Paper paper = Paper.INSTANCE;
		Rock rock = Rock.INSTANCE;
		
		// when
		GamePlayStatus status = paper.versus(rock);
		
		// then
		assertThat(status).isEqualTo(WIN);
	}
	
	@Test
	public void shouldTieWithPaper() {
		// given
		Paper paper = Paper.INSTANCE;
		
		// when
		GamePlayStatus status = paper.versus(paper);
		
		// then
		assertThat(status).isEqualTo(TIE);
	}
	
	@Test
	public void shouldLooseWithScissors() {
		// given
		Paper paper = Paper.INSTANCE;
		Scissors scissors = Scissors.INSTANCE;
		
		// when
		GamePlayStatus status = paper.versus(scissors);
		
		// then
		assertThat(status).isEqualTo(LOOSE);
	}
	
}
