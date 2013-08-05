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
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.listener.OnGamePlayStartedListener;
import pl.mmajcherski.rps.domain.listener.OnPlayerGestureShownListener;
import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.ui.common.Colors;
import pl.mmajcherski.rps.ui.common.GradientJPanel;
import pl.mmajcherski.rps.ui.common.TransparentJPanel;

public abstract class PlayerPanel extends GradientJPanel {

	private static final long serialVersionUID = 5339235466162426772L;

	private final JLabel gestureLabel = new JLabel();
	
	private final PlayerId playerId;
	private final GestureGameController gameController;
	private final JPanel customPanel;

	public PlayerPanel(final GestureGameController gameController) {
		this.gameController = gameController;
		this.playerId = new PlayerId(UUID.randomUUID().toString());

		setBorder(BorderFactory.createLineBorder(Colors.HEADER_BACKGROUND.getColor()));
		setPreferredSize(new Dimension(200, 200));
		setBackground(Colors.PLAYER_BACKGROUND.getColor());
		setLayout(new GridBagLayout());
		
		add(buildHeaderPanel(), getHeaderPanelAlignment());
		customPanel = buildCustomPanel();
		add(customPanel, getCustomPlayerPanelAlignment());
		add(buildGesturePanel(), getGesturePanelAlignment());
		
		registerGameEventListeners();
	}

	private JPanel buildHeaderPanel() {
		Font font = new Font(Font.DIALOG, Font.PLAIN, 14);
		
		JLabel label = new JLabel(getHeaderLabel(), JLabel.LEADING);
		label.setFont(font);
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		JPanel panel = new JPanel();
		panel.setBackground(Colors.HEADER_BACKGROUND.getColor());
		panel.add(label);
		
		return panel;
	}
	
	protected abstract String getHeaderLabel();
	
	private JPanel buildCustomPanel() {
		JPanel panel = new TransparentJPanel();
		panel.setPreferredSize(new Dimension(0, 35));
		return panel;
	}
	
	private JPanel buildGesturePanel() {
		JPanel panel = new TransparentJPanel();
		panel.add(gestureLabel);
		setUnknownGestureImage();
		
		return panel;
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
	
	protected PlayerId getPlayerId() {
		return playerId;
	}
	
	protected GestureGameController getGameController() {
		return gameController;
	}
	
	protected JPanel getCustomPanel() {
		return customPanel;
	}
	
	private void registerGameEventListeners() {
		gameController.addEventListener(new OnGamePlayStartedListener() {
			
			@Override
			public void onGamePlayStarted(GameConfiguration configuration) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setUnknownGestureImage();
					}
				});
			}
			
		});

		gameController.addEventListener(new OnPlayerGestureShownListener() {
			
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
			
		});
	}
	
	private GridBagConstraints getHeaderPanelAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_START;
		return c;
	}
	
	private GridBagConstraints getGesturePanelAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.8;
		return c;
	}
	
	private GridBagConstraints getCustomPlayerPanelAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_END;
		return c;
	}

}
