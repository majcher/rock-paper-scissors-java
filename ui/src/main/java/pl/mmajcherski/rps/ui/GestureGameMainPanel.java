package pl.mmajcherski.rps.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import pl.mmajcherski.rps.ui.GestureGameController.PlayerSide;

public class GestureGameMainPanel extends JPanel {

	private static final long serialVersionUID = 7826206452904434095L;
	
	private final GestureGameController controller;

	public GestureGameMainPanel(final GestureGameController controller) {
		this.controller = controller;
		
		setLayout(new GridBagLayout());
		
		PlayerPanel firstPlayerPanel = buildFirstPlayerPanel();
		add(firstPlayerPanel, getFirstPlayerPanelAlignment());

		PlayerPanel secondPlayerPanel = buildSecondPlayerPanel();
		add(secondPlayerPanel, getSecondPlayerPanelAlignment());

		GameCentralPanel scorePanel = buildGameCentralPanel();
		add(scorePanel, getScorePanelAlignment());
	}
	
	private PlayerPanel buildFirstPlayerPanel() {
		switch (controller.getGameMode()) {
		case HUMAN_COMPUTER:
			return new HumanPlayerPanel(controller, PlayerSide.LEFT);
		case COMPUTER_COMPUTER:
			return new ComputerPlayerPanel(controller, PlayerSide.LEFT);
		default:
			throw new IllegalStateException();
		}
	}
	
	private PlayerPanel buildSecondPlayerPanel() {
		return new ComputerPlayerPanel(controller, PlayerSide.RIGHT);
	}
	
	private GameCentralPanel buildGameCentralPanel() {
		return new GameCentralPanel(controller);
	}

	private GridBagConstraints getSecondPlayerPanelAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.35;
		c.weighty = 1;
		return c;
	}

	private GridBagConstraints getScorePanelAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.3;
		c.weighty = 1;
		return c;
	}

	private GridBagConstraints getFirstPlayerPanelAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.35;
		c.weighty = 1;
		return c;
	}
	
}
