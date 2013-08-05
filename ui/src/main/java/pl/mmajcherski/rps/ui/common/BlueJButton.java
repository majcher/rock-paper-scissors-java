package pl.mmajcherski.rps.ui.common;

import java.awt.Color;

import javax.swing.JButton;

public class BlueJButton extends JButton{

	private static final long serialVersionUID = -7414374557014149731L;

	public BlueJButton(String label) {
		super(label);
		
		setBackground(Colors.BUTTON_BLUE.getColor());
		setForeground(Color.WHITE);
	}
	
}
