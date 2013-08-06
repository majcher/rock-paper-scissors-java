package pl.mmajcherski.rps.ui.component.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pl.mmajcherski.rps.ui.GameMode;
import pl.mmajcherski.rps.ui.component.GameMainFrame;

public class GameMenuBar extends JMenuBar {

	private static final long serialVersionUID = 4459116335982550470L;

	private final GameMainFrame gameMainFrame;
	
	public GameMenuBar(final GameMainFrame gameMainFrame) {
		this.gameMainFrame = gameMainFrame;
		
		JMenu gameMenu = new JMenu("Game");
		
		gameMenu.add(buildHumanVsComputerMenuItem());
		gameMenu.add(buildComputerVsComputerMenuItem());
		gameMenu.addSeparator();
		gameMenu.add(buildExitGameMenuItem());
		
		add(gameMenu);
	}

	private JMenuItem buildHumanVsComputerMenuItem() {
		return buildGameModeMenuItem(GameMode.HUMAN_COMPUTER);
	}
	
	private JMenuItem buildComputerVsComputerMenuItem() {
		return buildGameModeMenuItem(GameMode.COMPUTER_COMPUTER);
	}
	
	private JMenuItem buildGameModeMenuItem(final GameMode gameMode) {
		JMenuItem computerItem = new JMenuItem(gameMode.getFullName());
		computerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMainFrame.prepareGame(gameMode);
			}
		});
		return computerItem;
	}
	
	private JMenuItem buildExitGameMenuItem() {
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		return exitItem;
	}
	
}
