package pl.mmajcherski.rps.ui;

import javax.swing.JPanel;

import pl.mmajcherski.rps.domain.player.impl.ComputerPlayer;

public class ComputerPlayerPanel extends PlayerPanel {

	private static final long serialVersionUID = -4479805048347377250L;

	public ComputerPlayerPanel(GestureGameController controller) {
		super(controller);
		
		controller.addPlayer(ComputerPlayer.withId(getPlayerId()));
	}

	@Override
	protected void fillPlayersPanel(JPanel panel) {
		
	}

	@Override
	protected String getHeaderLabel() {
		return "COMPUTER";
	}

}
