package pl.mmajcherski.rps.domain.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.impl.gesture.Rock;

public class HumanPlayerTest {
	
	@Mock
	private GestureGameConfiguration game;

	@BeforeMethod
	private void initializeMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Player must be given non-null ID")
	public void shouldNotHaveIdEqualToNull() {
		// given
		PlayerId playerId = null;
		
		// when
		HumanPlayer.withId(playerId);
	}

	@Test
	public void shouldHaveIdSetAfterConstruction() {
		// given
		PlayerId playerId = new PlayerId("1");
		
		// when
		Player player = HumanPlayer.withId(playerId);
		
		// then
		assertThat(player.getId()).isEqualTo(playerId);
	}
	
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Player must join game first")
	public void shouldNotBeAbleToShowGestureBeforeJoiningTheGame() {
		// given
		PlayerId playerId = new PlayerId("1");
		HumanPlayer player = HumanPlayer.withId(playerId);
		
		// when
		player.showGesture(Rock.INSTANCE);
	}
	
	@Test
	public void shouldBeAbleToShowGestureAfterJoiningTheGame() {
		// given
		GestureGame game = mock(GestureGame.class);
		PlayerId playerId = new PlayerId("1");
		HumanPlayer player = HumanPlayer.withId(playerId);
		player.join(game);
		
		// when
		player.showGesture(Rock.INSTANCE);
	}
	
	@Test
	public void shouldTriggerGestureShownEventOnControlledGame() {
		// given
		GestureGame game = mock(GestureGame.class);
		PlayerId playerId = new PlayerId("1");
		HumanPlayer player = HumanPlayer.withId(playerId);
		player.join(game);
		
		// when
		player.showGesture(Rock.INSTANCE);
		
		// then
		verify(game).onPlayerGesture(playerId, Rock.INSTANCE);
	}
	
	@Test
	public void shouldHaveEqualsHashCodeContractMet() {
		EqualsVerifier.forClass(HumanPlayer.class).usingGetClass().verify();
	}
	
}
