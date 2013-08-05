package pl.mmajcherski.rps.ui.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.listener.OnGameOverListener;
import pl.mmajcherski.rps.ui.GestureGameController;
import pl.mmajcherski.rps.ui.common.Colors;
import pl.mmajcherski.rps.ui.common.TransparentJPanel;

public class GameControlPanel extends TransparentJPanel {

	private static final long serialVersionUID = -344547901511819005L;

	private static final String START_GAME_BUTTON_LABEL = "Start game";
	
	private final JButton startGameButton;
	private final GestureGameController gameController;
	
	public GameControlPanel(final GestureGameController gameController) {
		this.gameController = gameController;
		
		startGameButton = new JButton(START_GAME_BUTTON_LABEL);
		startGameButton.setForeground(Color.WHITE);
		startGameButton.setBackground(Colors.BUTTON_GREEN.getColor());
		startGameButton.setHorizontalTextPosition(JButton.CENTER);
		startGameButton.setVerticalTextPosition(JButton.CENTER);
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGameButton.setEnabled(false);
				gameController.startGame();
			}
		});
		
		add(startGameButton);
		
		registerListeners();
	}
	
	private void registerListeners() {
		gameController.addEventListener(new OnGameOverListener() {
			
			@Override
			public void onGameOver(final GameFinalScore gameScore) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						startGameButton.setEnabled(true);
					}
				});
			}
			
		});
	}
	
}
