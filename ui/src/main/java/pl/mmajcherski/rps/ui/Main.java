package pl.mmajcherski.rps.ui;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GameMainFrame gameGui = new GameMainFrame();
				gameGui.setVisible(true);
			}
			
		});
	}
	
}