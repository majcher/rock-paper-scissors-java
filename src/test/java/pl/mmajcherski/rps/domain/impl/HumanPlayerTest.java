package pl.mmajcherski.rps.domain.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.fest.assertions.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.Game;
import pl.mmajcherski.rps.domain.Player;
import pl.mmajcherski.rps.domain.impl.gesture.Rock;

public class HumanPlayerTest {
	
	@Mock
	private Game game;
	
	
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
	
	@Test
	public void shouldSendAcceptRequestJoiningAGame() {
		// given
		PlayerId playerId = new PlayerId("1");
		Player player = HumanPlayer.withId(playerId);
		
		// when
		player.join(game);
		
		// then
		verify(game).accept(player);
	}
	
	@Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Cannot join game which is null")
	public void shouldThrowAnExceptionWhenJoiningANullGame() {
		// given
		Game game = null;
		PlayerId playerId = new PlayerId("1");
		Player player = HumanPlayer.withId(playerId);
		
		// when
		player.join(game);
	}
	
	@Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Player must first join a game")
	public void shouldNotBeAbleToSendReadyEventToAGameWhenPlayerHasNotYetJoinedIt() {
		// given
		PlayerId playerId = new PlayerId("1");
		Player player = HumanPlayer.withId(playerId);
		
		// when
		player.readyToPlay();
	}
	
	@Test
	public void shouldSendReadyEventToTheGameWhenPlayerIsReadyToPlay() {
		// given
		PlayerId playerId = new PlayerId("1");
		Player player = HumanPlayer.withId(playerId);
		player.join(game);
		
		// when
		player.readyToPlay();
		
		// then
		verify(game).onPlayerReadyToPlay(playerId);
	}
	
	@Test
	public void shouldProvideGestureShown() {
		// given
		PlayerId playerId = new PlayerId("1");
		Player player = HumanPlayer.withId(playerId);
		player.join(game);
		player.readyToPlay();
		
		// when
		player.showGesture(Rock.INSTANCE);
		
		// then
		assertThat(player.getGestureShown()).isEqualTo(Rock.INSTANCE);
	}
	
}
