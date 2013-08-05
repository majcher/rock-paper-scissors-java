package pl.mmajcherski.rps.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.ui.common.Colors;
import pl.mmajcherski.rps.ui.common.TransparentJPanel;

public class GameScorePanel extends JPanel implements ActionListener, GameEventsListener {

	private static final long serialVersionUID = 8758805484090403177L;

	private static final String START_CMD = "start";
	private static final int PROGRESS_BAR_STEPS = 100;

	private final GestureGameController gameController;
	private final PlayerId leftPlayerId;
	private final PlayerId rightPlayerId;
	
	private JProgressBar progressBar;
	private Timer progressBarTimer;
	
	private JButton startGameButton;
	
	private JLabel leftPlayerScore;
	private JLabel rightPlayerScore;
	
	public GameScorePanel(GestureGameController gameController, PlayerId leftPlayerId, PlayerId rightPlayerId) {
		this.gameController = gameController;
		this.leftPlayerId = leftPlayerId;
		this.rightPlayerId = rightPlayerId;
		
		gameController.registerListener(this);
		
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		
		buildHeader();
		buildScorePanel();
		buildControllPanel();
	}
	
	private void buildHeader() {
		Font font = new Font(Font.DIALOG, Font.PLAIN, 16);
		
		JLabel label = new JLabel("SCORE", JLabel.LEADING);
		label.setFont(font);
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		JPanel panel = new TransparentJPanel();
		panel.add(label);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_START;
		
		add(panel, c);
	}

	private void buildScorePanel() {
		Font font = new Font(Font.DIALOG, Font.PLAIN, 30);
		
		JPanel panel = new TransparentJPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JPanel scorePanel = new TransparentJPanel();
		
		leftPlayerScore = new JLabel();
		leftPlayerScore.setText("0");
		leftPlayerScore.setFont(font);
		scorePanel.add(leftPlayerScore);
		
		JLabel delimiter = new JLabel(":");
		delimiter.setFont(font);
		scorePanel.add(delimiter);
		
		rightPlayerScore = new JLabel();
		rightPlayerScore.setText("0");
		rightPlayerScore.setFont(font);
		scorePanel.add(rightPlayerScore);
		
		JPanel progressBarPanel = new TransparentJPanel();
		progressBar = new JProgressBar(0, PROGRESS_BAR_STEPS);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		int progressBarStepInMs = (int) (gameController.getGameDurationMs() / PROGRESS_BAR_STEPS);
		
		progressBarTimer = new Timer(progressBarStepInMs, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(progressBar.getValue() + 1);
			}
		});

		progressBarPanel.add(progressBar);
		
		panel.add(scorePanel);
		panel.add(progressBarPanel);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.8;

		add(panel, c);
	}
	
	private void buildControllPanel() {
		JPanel controllPanel = new TransparentJPanel();
		
		startGameButton = new JButton("Start game");
		startGameButton.setForeground(Color.WHITE);
		startGameButton.setBackground(Colors.BUTTON_GREEN.getColor());
		startGameButton.setHorizontalTextPosition(JButton.CENTER);
		startGameButton.setVerticalTextPosition(JButton.CENTER);
		startGameButton.setActionCommand(START_CMD);
		startGameButton.addActionListener(this);
		
		controllPanel.add(startGameButton);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_END;
		
		add(controllPanel, c);
	}
	
	public void resetScorePanel() {
		leftPlayerScore.setText("0");
		rightPlayerScore.setText("0");
	}
	
	public void updateScorePanel(GameFinalScore gameScore) {
		leftPlayerScore.setText(String.valueOf(gameScore.of(leftPlayerId)));
		rightPlayerScore.setText(String.valueOf(gameScore.of(rightPlayerId)));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case START_CMD:
			resetScorePanel();
			startGameButton.setEnabled(false);
			gameController.startGame();
			break;
		default:
			throw new IllegalStateException();
		}
	}
	
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
	
	@Override
	public void onPlayerGestureShown(final PlayerId playerId, final Gesture gesture) {
	}

	@Override
	public void onGamePlayResult(final GamePlayResult gamePlayResult, final GameFinalScore gameScore) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				updateScorePanel(gameScore);
				progressBarTimer.stop();
				progressBar.setValue(PROGRESS_BAR_STEPS);
			}
		});
	}

	@Override
	public void onGameOver(final GameFinalScore gameScore) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				startGameButton.setEnabled(true);
			}
		});
	}
	
}
