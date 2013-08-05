package pl.mmajcherski.rps.domain.player.impl;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.GestureGame;
import pl.mmajcherski.rps.domain.PlayerGestureListener;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.GestureRandomiser;
import pl.mmajcherski.rps.domain.gesture.impl.Paper;
import pl.mmajcherski.rps.domain.gesture.impl.Rock;
import pl.mmajcherski.rps.domain.gesture.impl.Scissors;
import pl.mmajcherski.rps.domain.gesture.impl.SimpleGestureRandomiser;
import pl.mmajcherski.rps.domain.player.Player;
import pl.mmajcherski.rps.domain.player.PlayerId;

public final class ComputerPlayer implements Player, GameEventsListener {

	private final PlayerId playerId;
	private final Random timeRandomiser = new Random();
	private final GestureRandomiser gestureRandomiser;
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	private PlayerGestureListener playerGestureListener;
	
	private ComputerPlayer(PlayerId playerId) {
		this.playerId = playerId;
		this.gestureRandomiser = new SimpleGestureRandomiser(Rock.INSTANCE, Paper.INSTANCE, Scissors.INSTANCE);
	}

	public static ComputerPlayer withId(PlayerId playerId) {
		return new ComputerPlayer(playerId);
	}

	@Override
	public PlayerId getId() {
		return playerId;
	}
	
	@Override
	public void join(GestureGame game) {
		this.playerGestureListener = game;
		
		game.add(this);
		game.registerEventsListener(this);
	}

	@Override
	public void onGamePlayStarted(GameConfiguration configuration) {
		double timeToShowGestureRandomMultiplier = 0.5 + 0.5 * timeRandomiser.nextDouble();
		long timeToShowGestureInMs = (long) (timeToShowGestureRandomMultiplier * configuration.getPlayDurationInMs());
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				playerGestureListener.onPlayerGesture(playerId, gestureRandomiser.getRandomGesture());
			}
		}, timeToShowGestureInMs, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void onPlayerGestureShown(PlayerId playerId, Gesture gesture) {
	}

	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore) {
	}

	@Override
	public void onGameOver(GameFinalScore gameScore) {
	}

}
