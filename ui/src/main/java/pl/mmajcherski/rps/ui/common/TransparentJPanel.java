package pl.mmajcherski.rps.ui.common;

import java.awt.Color;

import javax.swing.JPanel;

public class TransparentJPanel extends JPanel {

	private static final long serialVersionUID = -4879065568311267323L;

	public TransparentJPanel() {
		super();
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
	}
}
