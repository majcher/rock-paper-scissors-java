package pl.mmajcherski.rps.ui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.listener.OnGamePlayResultListener;
import pl.mmajcherski.rps.domain.listener.OnGamePlayStartedListener;
import pl.mmajcherski.rps.ui.GestureGameController;
import pl.mmajcherski.rps.ui.common.TransparentJPanel;

public class GamePlayProgressPanel extends TransparentJPanel {
	
	private static final long serialVersionUID = 2618317527526150923L;
	
	private static final int PROGRESS_BAR_STEPS = 100;

	private final GestureGameController gameController;
	
	private JProgressBar progressBar;
	private Timer progressBarTimer;
	
	public GamePlayProgressPanel(final GestureGameController gameController) {
		this.gameController = gameController;
		
		progressBar = initializeGamePlayProgressBar();
		progressBarTimer = initializeProgressBarTimer(gameController.getGameDurationMs());

		add(progressBar);
		
		registerGameEventsListeners();
	}

	private JProgressBar initializeGamePlayProgressBar() {
		final JProgressBar progressBar = new JProgressBar(0, PROGRESS_BAR_STEPS);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		return progressBar;
	}
	
	private Timer initializeProgressBarTimer(final long gameDurationInMs) {
		int progressBarStepInMs = (int) (gameDurationInMs / PROGRESS_BAR_STEPS);
		final Timer progressBarTimer = new Timer(progressBarStepInMs, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(progressBar.getValue() + 1);
			}
		});
		
		return progressBarTimer;
	}
	
	private void registerGameEventsListeners() {
		gameController.addEventListener(new OnGamePlayStartedListener() {
			
			@Override
			public void onGamePlayStarted(final GameConfiguration configuration) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						progressBar.setValue(0);
						progressBarTimer.start();
					}
				});
			}
			
		});
		
		gameController.addEventListener(new OnGamePlayResultListener() {
			
			@Override
			public void onGamePlayResult(final GamePlayResult gamePlayResult, final GameFinalScore gameScore) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						progressBarTimer.stop();
						progressBar.setValue(PROGRESS_BAR_STEPS);
					}
				});
			}

		});
	}

}
