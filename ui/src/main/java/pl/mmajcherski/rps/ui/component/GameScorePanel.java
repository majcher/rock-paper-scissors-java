package pl.mmajcherski.rps.ui.component;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.listener.OnGamePlayResultListener;
import pl.mmajcherski.rps.domain.listener.OnGameStartedListener;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.ui.GestureGameController;
import pl.mmajcherski.rps.ui.GestureGameController.PlayerSide;
import pl.mmajcherski.rps.ui.common.TransparentJPanel;

public class GameScorePanel extends TransparentJPanel {

	private static final long serialVersionUID = -6757998992665311592L;
	
	private final GestureGameController gameController;
	private final JLabel leftPlayerScore;
	private final JLabel rightPlayerScore;

	public GameScorePanel(final GestureGameController gameController) {
		this.gameController = gameController;
		
		Font font = new Font(Font.DIALOG, Font.PLAIN, 30);
		
		JPanel scorePanel = new TransparentJPanel();
		
		leftPlayerScore = new JLabel();
		leftPlayerScore.setFont(font);
		scorePanel.add(leftPlayerScore);
		
		JLabel delimiter = new JLabel(":");
		delimiter.setFont(font);
		scorePanel.add(delimiter);
		
		rightPlayerScore = new JLabel();
		rightPlayerScore.setFont(font);
		
		resetScorePanel();
		
		scorePanel.add(rightPlayerScore);
		
		add(scorePanel);
		
		registerListeners();
	}
	
	private void resetScorePanel() {
		leftPlayerScore.setText("0");
		rightPlayerScore.setText("0");
	}
	
	private void updateScorePanel(GameFinalScore gameScore) {
		PlayerId leftPlayerId = gameController.getPlayer(PlayerSide.LEFT).getId();
		PlayerId rightPlayerId = gameController.getPlayer(PlayerSide.RIGHT).getId();
		
		leftPlayerScore.setText(String.valueOf(gameScore.of(leftPlayerId)));
		rightPlayerScore.setText(String.valueOf(gameScore.of(rightPlayerId)));
	}
	
	private void registerListeners() {
		gameController.addEventListener(new OnGameStartedListener() {
			
			@Override
			public void onGameStarted(final GameConfiguration configuration) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						resetScorePanel();
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
						updateScorePanel(gameScore);
					}
				});
			}
			
		});
	}
}
