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
import pl.mmajcherski.rps.domain.listener.OnGamePlayResultListener;
import pl.mmajcherski.rps.domain.listener.OnGamePlayStartedListener;
import pl.mmajcherski.rps.domain.player.impl.HumanPlayer;
import pl.mmajcherski.rps.ui.GestureGameController.PlayerSide;
import pl.mmajcherski.rps.ui.common.BlueJButton;

public class HumanPlayerPanel extends PlayerPanel {

	private static final long serialVersionUID = -2687020447920494839L;
	
	private static final String HUMAN_LABEL = "HUMAN";

	private final HumanPlayer humanPlayer;
	private List<JButton> gestureButtons = new ArrayList<>();

	public HumanPlayerPanel(final GestureGameController controller, PlayerSide playerSide) {
		super(controller);
		
		humanPlayer = HumanPlayer.withId(getPlayerId());
		
		getGameController().addPlayer(humanPlayer, playerSide);
		
		JPanel customPlayerPanel = getCustomPanel();
		fillPlayersPanel(customPlayerPanel);
		
		registerGameEventListeners();
	}
	
	@Override
	protected String getHeaderLabel() {
		return HUMAN_LABEL;
	}
	
	protected void fillPlayersPanel(JPanel panel) {
		panel.setLayout(new FlowLayout());
		
		for (final Gesture gesture : getGameController().getAvailableGestures()) {
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
	
	private void registerGameEventListeners() {
		getGameController().addEventListener(new OnGamePlayStartedListener() {
			
			@Override
			public void onGamePlayStarted(GameConfiguration configuration) {
				for (JButton gestureButton : gestureButtons) {
					gestureButton.setVisible(true);
				}
			}
			
		});
			
		getGameController().addEventListener(new OnGamePlayResultListener() {
			
			@Override
			public void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore) {
				for (JButton gestureButton : gestureButtons) {
					gestureButton.setVisible(false);
				}
			}

		});
	}

}
