package pl.mmajcherski.rps.ui.component;

import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.gesture.impl.SimpleGestureRandomiser;
import pl.mmajcherski.rps.domain.player.impl.ComputerPlayer;
import pl.mmajcherski.rps.ui.GestureGameController;
import pl.mmajcherski.rps.ui.GestureGameController.PlayerSide;

public class ComputerPlayerPanel extends PlayerPanel {

	private static final long serialVersionUID = -4479805048347377250L;

	private static final String COMPUTER_LABEL = "COMPUTER";

	public ComputerPlayerPanel(GestureGameController controller, PlayerSide playerSide) {
		super(controller);
		
		Gesture[] gestures = controller.getAvailableGestures();
		ComputerPlayer computerPlayer = new ComputerPlayer.Builder()
			.withId(getPlayerId())
			.withGestureRandomiser(new SimpleGestureRandomiser(gestures))
			.build();
		
		controller.addPlayer(computerPlayer, playerSide);
	}

	@Override
	protected String getHeaderLabel() {
		return COMPUTER_LABEL;
	}

}
