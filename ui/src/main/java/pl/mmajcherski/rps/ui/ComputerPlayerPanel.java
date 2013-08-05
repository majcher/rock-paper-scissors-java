package pl.mmajcherski.rps.ui;

import javax.swing.JPanel;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.impl.SimpleGestureRandomiser;
import pl.mmajcherski.rps.domain.player.impl.ComputerPlayer;

public class ComputerPlayerPanel extends PlayerPanel {

	private static final long serialVersionUID = -4479805048347377250L;

	public ComputerPlayerPanel(GestureGameController controller) {
		super(controller);
		
		Gesture[] gestures = controller.getAvailableGestures();
		ComputerPlayer computerPlayer = new ComputerPlayer.Builder()
			.withId(getPlayerId())
			.withGestureRandomiser(new SimpleGestureRandomiser(gestures))
			.build();
		
		controller.addPlayer(computerPlayer);
	}

	@Override
	protected void fillPlayersPanel(JPanel panel) {
		
	}

	@Override
	protected String getHeaderLabel() {
		return "COMPUTER";
	}

}
