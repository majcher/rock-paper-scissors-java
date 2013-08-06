package pl.mmajcherski.rps.ui.component;

import javax.swing.JFrame;

import pl.mmajcherski.rps.ui.GameMode;
import pl.mmajcherski.rps.ui.GestureGameController;
import pl.mmajcherski.rps.ui.menu.GameMenuBar;

public class GameMainFrame extends JFrame {

	private static final long serialVersionUID = -5544862609627776531L;

	private static final String GAME_NAME = "Rock, Paper, Scissors";
	private static final GameMode DEFAULT_GAME_MODE = GameMode.HUMAN_COMPUTER;
	
	private GestureGameController gameController;
	private GestureGameMainPanel mainPanel;

	public GameMainFrame() {
		setTitle(GAME_NAME);
		setSize(700, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setJMenuBar(new GameMenuBar(this));

		prepareGame(DEFAULT_GAME_MODE);
	}
	
	public void prepareGame(GameMode gameMode) {
		stopPreviousGameIfExist();
		setupNewGameInMode(gameMode);
		updateGameView();
	}

	private void stopPreviousGameIfExist() {
		if (gameController != null) {
			gameController.stopGame();
		}
	}
	
	private void setupNewGameInMode(GameMode gameMode) {
		gameController = new GestureGameController();
		gameController.setupGameInMode(gameMode);
	}
	
	private void updateGameView() {
		if (mainPanel != null) {
			getContentPane().remove(mainPanel);
		}
		mainPanel = new GestureGameMainPanel(gameController);
		
		getContentPane().add(mainPanel);
		
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	
}
