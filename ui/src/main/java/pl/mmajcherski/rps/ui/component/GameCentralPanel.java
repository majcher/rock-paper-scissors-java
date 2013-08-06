package pl.mmajcherski.rps.ui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.mmajcherski.rps.ui.GestureGameController;
import pl.mmajcherski.rps.ui.common.TransparentJPanel;

public class GameCentralPanel extends JPanel {

	private static final long serialVersionUID = 8758805484090403177L;

	private final GestureGameController gameController;
	
	public GameCentralPanel(GestureGameController gameController) {
		this.gameController = gameController;
		
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		
		add(buildHeader(), getHeaderAlignment());
		add(buildScorePanel(), getScorePanelAlignment());
		add(buildControlPanel(), getControlPanelAlignment());
	}

	private JPanel buildHeader() {
		Font font = new Font(Font.DIALOG, Font.PLAIN, 16);
		
		JLabel label = new JLabel("SCORE", JLabel.LEADING);
		label.setFont(font);
		label.setForeground(Color.BLACK);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		JPanel panel = new TransparentJPanel();
		panel.add(label);
		
		return panel;
	}

	private JPanel buildScorePanel() {
		JPanel panel = new TransparentJPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		panel.add(new GameScorePanel(gameController));
		panel.add(new GamePlayProgressPanel(gameController));

		return panel;
	}
	
	private JPanel buildControlPanel() {
		return new GameControlPanel(gameController);
	}
	
	private GridBagConstraints getHeaderAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_START;
		return c;
	}
	
	private GridBagConstraints getScorePanelAlignment() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.8;
		return c;
	}
	
	private GridBagConstraints getControlPanelAlignment() {
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
