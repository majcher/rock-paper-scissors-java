package pl.mmajcherski.rps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameEventsListener;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.ui.common.Colors;
import pl.mmajcherski.rps.ui.common.GradientJPanel;
import pl.mmajcherski.rps.ui.common.TransparentJPanel;

public abstract class PlayerPanel extends GradientJPanel implements GameEventsListener {

	private static final long serialVersionUID = 5339235466162426772L;

	private final JLabel gestureLabel = new JLabel();
	
	protected PlayerId playerId;
	protected final GestureGameController controller;

	public PlayerPanel(GestureGameController controller) {
		this.controller = controller;
		this.playerId = new PlayerId(UUID.randomUUID().toString());

		setBorder(BorderFactory.createLineBorder(Colors.HEADER_BACKGROUND.getColor()));
		
		setPreferredSize(new Dimension(200, 200));
		setBackground(Colors.PLAYER_BACKGROUND.getColor());
		
		buildLayout();
		buildHeader();
		buildCustomPanel();
		buildPanel();
		
		controller.registerListener(this);
	}
	
	private void buildLayout() {
		setLayout(new GridBagLayout());
	}

	private void buildHeader() {
		Font font = new Font(Font.DIALOG, Font.PLAIN, 14);
		
		JLabel label = new JLabel(getHeaderLabel(), JLabel.LEADING);
		label.setFont(font);
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		JPanel panel = new JPanel();
		panel.setBackground(Colors.HEADER_BACKGROUND.getColor());
		panel.add(label);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_START;
		
		add(panel, c);
	}
	
	protected abstract String getHeaderLabel();
	
	private void buildCustomPanel() {
		JPanel panel = new TransparentJPanel();
		panel.setPreferredSize(new Dimension(0, 35));
		fillPlayersPanel(panel);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_END;
		
		add(panel, c);
	}
	
	protected abstract void fillPlayersPanel(JPanel panel);
	
	private void buildPanel() {
		JPanel panel = new TransparentJPanel();
		panel.add(gestureLabel);
		setUnknownGestureImage();
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.8;
		
		add(panel, c);
	}
	
	public void setImageForGesture(Gesture gesture) {
		String fileName = String.format("%s.png", gesture.getName().toLowerCase());
		setVisibleImage(fileName);
	}
	
	public void setUnknownGestureImage() {
		setVisibleImage("help.png");
	}
	
	private void setVisibleImage(String fileName) {
		URL imageUrl = getClass().getClassLoader().getResource(fileName);
		gestureLabel.setIcon(new ImageIcon(imageUrl));
	}
	
	@Override
	public void onGamePlayStarted(GameConfiguration configuration) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setUnknownGestureImage();
			}
		});
	}

	@Override
	public void onPlayerGestureShown(final PlayerId playerId, final Gesture gesture) {
		if (playerId.equals(getPlayerId())) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setImageForGesture(gesture);
				}
			});
		}
	}
	
	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore) {
	}

	@Override
	public void onGameOver(GameFinalScore gameScore) {
	}
	
	protected PlayerId getPlayerId() {
		return playerId;
	}

}
