package pl.mmajcherski.rps.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import pl.mmajcherski.rps.domain.player.PlayerId;

public class GestureGameMainPanel extends JPanel {

	private static final long serialVersionUID = 7826206452904434095L;
	
	private GestureGameController controller;

	public GestureGameMainPanel(GestureGameController controller) {
		this.controller = controller;
		
		buildPanel();
	}

	private void buildPanel() {
		setLayout(new GridBagLayout());
		
		PlayerPanel leftPlayerPanel = buildLeftPanel();
		PlayerPanel rightPlayerPanel = buildRightPanel();
		GameScorePanel scorePanel = buildCentralPanel(leftPlayerPanel.getPlayerId(), rightPlayerPanel.getPlayerId());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.35;
		c.weighty = 1;
		
		add(leftPlayerPanel, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.3;
		c.weighty = 1;
		
		add(scorePanel, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.35;
		c.weighty = 1;
		
		add(rightPlayerPanel, c);
	}

	private PlayerPanel buildLeftPanel() {
		switch (controller.getGameMode()) {
		case HUMAN_COMPUTER:
			return new HumanPlayerPanel(controller);
		case COMPUTER_COMPUTER:
			return new ComputerPlayerPanel(controller);
		default:
			throw new IllegalStateException();
		}
	}
	
	private GameScorePanel buildCentralPanel(PlayerId leftPlayerId, PlayerId rightPlayerId) {
		return new GameScorePanel(controller, leftPlayerId, rightPlayerId);
	}
	
	private PlayerPanel buildRightPanel() {
		return new ComputerPlayerPanel(controller);
	}
	
}
