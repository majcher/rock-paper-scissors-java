package pl.mmajcherski.rps.domain.impl.gesture;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.mmajcherski.rps.domain.GamePlayStatus.LOOSE;
import static pl.mmajcherski.rps.domain.GamePlayStatus.TIE;
import static pl.mmajcherski.rps.domain.GamePlayStatus.WIN;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GamePlayStatus;

public class RockTest {

	@Test
	public void shouldWinWithScissors() {
		// given
		Rock rock = Rock.INSTANCE;
		Scissors scissors = Scissors.INSTANCE;
		
		// when
		GamePlayStatus status = rock.versus(scissors);
		
		// then
		assertThat(status).isEqualTo(WIN);
	}
	
	@Test
	public void shouldTieWithRock() {
		// given
		Rock rock = Rock.INSTANCE;
		
		// when
		GamePlayStatus status = rock.versus(rock);
		
		// then
		assertThat(status).isEqualTo(TIE);
	}
	
	@Test
	public void shouldLooseWithPaper() {
		// given
		Rock rock = Rock.INSTANCE;
		Paper paper = Paper.INSTANCE;
		
		// when
		GamePlayStatus status = rock.versus(paper);
		
		// then
		assertThat(status).isEqualTo(LOOSE);
	}
	
}
