package pl.mmajcherski.rps.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import pl.mmajcherski.rps.domain.GameConfiguration;
import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;
import pl.mmajcherski.rps.domain.gesture.Gesture;
import pl.mmajcherski.rps.domain.player.impl.HumanPlayer;
import pl.mmajcherski.rps.ui.common.BlueJButton;

public class HumanPlayerPanel extends PlayerPanel {

	private static final long serialVersionUID = -2687020447920494839L;
	
	private HumanPlayer humanPlayer;
	private List<JButton> gestureButtons;

	public HumanPlayerPanel(GestureGameController controller) {
		super(controller);
		
		humanPlayer = HumanPlayer.withId(getPlayerId());
		controller.addPlayer(humanPlayer);
	}
	
	@Override
	protected String getHeaderLabel() {
		return "HUMAN";
	}
	
	@Override
	protected void fillPlayersPanel(JPanel panel) {
		gestureButtons = new ArrayList<>();
		
		panel.setLayout(new FlowLayout());
		
		for (final Gesture gesture : controller.getGestures()) {
			JButton gestureButton = new BlueJButton(gesture.getName());
			gestureButton.setActionCommand(gesture.getName());
			gestureButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					humanPlayer.showGesture(gesture);
				}
			});
			gestureButton.setVisible(false);
			panel.add(gestureButton);
			gestureButtons.add(gestureButton);
		}
	}
	
	@Override
	public void onGamePlayStarted(GameConfiguration configuration) {
		super.onGamePlayStarted(configuration);
		for (JButton gestureButton : gestureButtons) {
			gestureButton.setVisible(true);
		}
	}
	
	@Override
	public void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore) {
		super.onGamePlayResult(gamePlayResult, gameScore);
		for (JButton gestureButton : gestureButtons) {
			gestureButton.setVisible(false);
		}
	}

}
