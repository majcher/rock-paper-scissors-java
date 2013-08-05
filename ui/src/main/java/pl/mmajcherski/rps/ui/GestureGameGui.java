package pl.mmajcherski.rps.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class GestureGameGui extends JFrame {

	private static final long serialVersionUID = -5544862609627776531L;

	private static final String GAME_NAME = "Rock, Paper, Scissors";
	
	private final GestureGameController gameController;
	private GameMode gameMode = GameMode.HUMAN_COMPUTER;
	private GestureGameMainPanel mainPanel;

	public GestureGameGui() {
		setTitle(GAME_NAME);
		setSize(700, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		gameController = new GestureGameController();
		
		JMenuBar menu = buildMenu();
		setJMenuBar(menu);

		setupGame();
	}

	private void setupGame() {
		gameController.initGame(gameMode);
		
		if (mainPanel != null) {
			getContentPane().remove(mainPanel);
		}
		mainPanel = new GestureGameMainPanel(gameController);
		getContentPane().add(mainPanel);
		
		getContentPane().revalidate();
		getContentPane().repaint();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GestureGameGui gameGui = new GestureGameGui();
				gameGui.setVisible(true);
			}
			
		});
	}
	
	private JMenuBar buildMenu() {
		JMenuItem humanItem = new JMenuItem("Human vs. Computer");
		humanItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMode = GameMode.HUMAN_COMPUTER;
				setupGame();
			}
		});
		
		JMenuItem computerItem = new JMenuItem("Computer vs. Computer");
		computerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMode = GameMode.COMPUTER_COMPUTER;
				setupGame();
			}
		});
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenu gameMenu = new JMenu("Game");
		gameMenu.add(humanItem);
		gameMenu.add(computerItem);
		gameMenu.addSeparator();
		gameMenu.add(exitItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gameMenu);
		
		return menuBar;
	}
}
