package pl.mmajcherski.rps.domain.player.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.impl.Paper;
import pl.mmajcherski.rps.domain.gesture.impl.Rock;
import pl.mmajcherski.rps.domain.gesture.impl.Scissors;
import pl.mmajcherski.rps.domain.gesture.impl.SimpleGestureRandomiser;
import pl.mmajcherski.rps.domain.listener.OnGamePlayStartedListener;
import pl.mmajcherski.rps.domain.player.PlayerId;

public class ComputerPlayerTest {

	private static final int TEST_GAME_DURATION = 1000;
	
	@Mock
	private GestureGame game;
	@Captor
	private ArgumentCaptor<OnGamePlayStartedListener> onGamePlayStartedListenerCaptor;
	
	private GameConfiguration configuration;
	private ComputerPlayer player;
	
	@BeforeMethod
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeMethod
	public void createPlayer() {
		configuration = new GameConfiguration.Builder()
			.withGameDurationInMs(TEST_GAME_DURATION)
			.build();
		
		player = new ComputerPlayer.Builder()
			.withId(new PlayerId("Test computer player"))
			.withGestureRandomiser(new SimpleGestureRandomiser(Rock.INSTANCE, Paper.INSTANCE, Scissors.INSTANCE))
			.build();
	}
	
	@Test
	public void shouldShowGestureInGamePlayPeriod() throws InterruptedException {
		// given
		player.join(game);
		
		// when
		verify(game).addEventListener(onGamePlayStartedListenerCaptor.capture());
		onGamePlayStartedListenerCaptor.getValue().onGamePlayStarted(configuration);
		Thread.sleep(TEST_GAME_DURATION);
		
		// then
		verify(game).onPlayerGesture(eq(player.getId()), any(Gesture.class));
	}
	
	@Test(invocationCount = 10)
	public void shouldNotShowGestureBeforeHalfOfGamePlayPeriod() throws InterruptedException {
		// given
		GameConfiguration configuration = new GameConfiguration.Builder()
			.withGameDurationInMs(TEST_GAME_DURATION)
			.build();
		
		player.join(game);
		
		// when
		verify(game).addEventListener(onGamePlayStartedListenerCaptor.capture());
		onGamePlayStartedListenerCaptor.getValue().onGamePlayStarted(configuration);
		Thread.sleep(TEST_GAME_DURATION / 4);
		
		// then
		verify(game, never()).onPlayerGesture(eq(player.getId()), any(Gesture.class));
	}
	
	@Test(invocationCount = 10)
	public void shouldShowOnlyGesturesUsedToCreatePlayer() throws InterruptedException {
		ComputerPlayer playerWithRockGestureOnly = new ComputerPlayer.Builder()
			.withId(new PlayerId("Test computer player"))
			.withGestureRandomiser(new SimpleGestureRandomiser(Rock.INSTANCE))
			.build();
		
		playerWithRockGestureOnly.join(game);
		
		// when
		verify(game).addEventListener(onGamePlayStartedListenerCaptor.capture());
		onGamePlayStartedListenerCaptor.getValue().onGamePlayStarted(configuration);
		Thread.sleep(TEST_GAME_DURATION);
		
		// then
		verify(game).onPlayerGesture(eq(player.getId()), eq(Rock.INSTANCE));
	}
}
