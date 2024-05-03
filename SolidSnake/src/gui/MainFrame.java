package gui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	// Variables
	JButton newGameButton;
	JButton highestScoresButton;
	JButton exitButton;
	JButton returnButton = new JButton("Return to menu");
	
	JPanel menu;
	GamePanel game;
	
	BufferedImage logo;
	
	
	/* MAIN CONSTRUCTOR */
	public MainFrame() throws InterruptedException {
		
		// Creates the main frame
		this.setTitle("Solid Snake");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		//this.menu = menu();
		//this.add(menu, BorderLayout.CENTER);
		//this.remove(menu);
		startGame();
		
		this.add(game, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		addKeyListener(game);
		game.start();
		removeKeyListener(game);
	}
	
	/*public JPanel menu() {
		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout(4,1));
		
		JLabel logoLabel = new JLabel();
		try {
			logo = ImageIO.read(new File("src/icons/hornySnakeLogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logoLabel.setIcon(new ImageIcon(logo));
		menu.add(logoLabel);
		
		newGameButton = new JButton("New game");
		newGameButton.addActionListener(this);
		highestScoresButton = new JButton("Highest Scores");
		highestScoresButton.addActionListener(this);
		exitButton = new JButton("Exit game");
		exitButton.addActionListener(this);
		
		menu.add(newGameButton);
		menu.add(highestScoresButton);
		menu.add(exitButton);
		
		return menu;
	}*/
	
	public void startGame() throws InterruptedException {
		this.game = new GamePanel();
		this.add(game, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		try {
			addKeyListener(game);
			game.start();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		removeKeyListener(game);
		/*if(game.game.isFinished()) {
			System.out.println("You lost");
		}*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGameButton) {
			
			try {
				startGame();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}else if(e.getSource() == exitButton) {
			this.dispose();
		}
		
	}
}
