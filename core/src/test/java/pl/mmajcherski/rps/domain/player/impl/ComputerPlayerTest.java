package pl.mmajcherski.rps.domain.player.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.PlayerId;

public class ComputerPlayerTest {

	@Mock
	private GestureGame game;
	private ComputerPlayer player;
	
	@BeforeMethod
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeMethod
	public void createPlayer() {
		player = ComputerPlayer.withId(new PlayerId("Test computer player"));
	}
	
	@Test
	public void shouldShowGestureInGamePlayPeriod() throws InterruptedException {
		// given
		GameConfiguration configuration = new GameConfiguration.Builder()
			.withGameDurationInMs(100)
			.build();
		
		player.join(game);
		
		// when
		player.onGamePlayStarted(configuration);
		Thread.sleep(100);
		
		// then
		verify(game).onPlayerGesture(eq(player.getId()), any(Gesture.class));
	}
	
	@Test(invocationCount = 10)
	public void shouldNotShowGestureBeforeHalfOfGamePlayPeriod() throws InterruptedException {
		// given
		GameConfiguration configuration = new GameConfiguration.Builder()
			.withGameDurationInMs(100)
			.build();
		
		player.join(game);
		
		// when
		player.onGamePlayStarted(configuration);
		Thread.sleep(25);
		
		// then
		verify(game, never()).onPlayerGesture(eq(player.getId()), any(Gesture.class));
	}
}
