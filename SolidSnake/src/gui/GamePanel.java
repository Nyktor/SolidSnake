package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Game;
import game.entities.snake.Direction;
import game.entities.snake.Node;

public class GamePanel extends JPanel implements KeyListener{

	private static final long serialVersionUID = -7927156597267134363L;

	//static final int HEIGHT = 800;
	//static final int WIDTH = 800;
	static final int SQUARES_HEIGHT = 30;
	static final int SQUARES_WIDTH = 30;
	//static final int SCORE_PANEL_HEIGHT = 200;

	//static final int BOARD_SIZE = SQUARES_HEIGHT * SQUARES_WIDTH;
	//static final int SQUARE_PIXELS_HEIGHT = HEIGHT/BOARD_SIZE;
	//static final int SQUARE_PIXELS_WIDTH = WIDTH/BOARD_SIZE;
	//static final int TOTAL_HEIGHT = HEIGHT + SCORE_PANEL_HEIGHT;
	
	static final byte SQUARES_PER_SECOND = 9;
	
	// Other variables
	boolean pressed;
	int points;
	private byte seconds, minutes;
	
	// Icons
	BufferedImage grass;
	BufferedImage headDOWN;
	BufferedImage headUP;
	BufferedImage headLEFT;
	BufferedImage headRIGHT;
	BufferedImage bodyHORIZONTAL;
	BufferedImage bodyVERTICAL;
	BufferedImage apple;
	
	// Object variables
	Game game = new Game(SQUARES_HEIGHT, SQUARES_WIDTH);
	JLabel timeLabel = new JLabel();
	JLabel pointsLabel = new JLabel();
	JLabel[][] gridLabels = new JLabel[SQUARES_HEIGHT][SQUARES_WIDTH];
	JPanel board = new JPanel();
	JPanel score = new JPanel();
	
	public GamePanel() throws InterruptedException {

		loadIcons();
		this.setLayout(new BorderLayout());
		
		// This is the gameboard layout
		board.setLayout(new GridLayout(SQUARES_HEIGHT, SQUARES_WIDTH));
		
		// This is the top score bar
		timeLabel.setText("Time: 0:00");
		pointsLabel.setText("Points: "+points);
		
		timeLabel.setFont(new Font("Consolas", Font.BOLD, 20));
		pointsLabel.setFont(new Font("Consolas", Font.BOLD, 20));
		
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		timeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		pointsLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		
		score.setLayout(new GridLayout(1, 2));
		score.add(timeLabel);
		score.add(pointsLabel);
		this.add(score, BorderLayout.NORTH);

		//board.setVisible(true);
		this.add(board, BorderLayout.CENTER);
		
		
		
		// The actual game
		printGrass();
		points = 0;
		seconds = 0;
		minutes = 0;
		
	}
	
	public void start() throws InterruptedException {

		TimerTask tk = new TimerTask(){
			
			@Override
			public void run() {
				if(seconds < 59) {
					seconds++;
				}else {
					minutes++;
					seconds = 0;
				}
				reloadScore();
			}
		};
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(tk, 0, 1000);
		
		while(!game.isFinished()) {
			
			game.moveSnake();
			if(!game.isFinished()) {
				points++;
				if(game.hasEatenAppleThisTurn()) points += 100;
				printGame();
			}
			
			Thread.sleep(spsToMillis());
			pressed = false;
		}
		timer.cancel();
		
	}

	// Prints everything and reloads the frame
	public void printGame() {
		printSnakeHead();
		printApple();

		Node next = game.getSnake().getNextNode();
		while(!next.isTail()) {
			switch(next.getOrientation()) {
			case EAST, WEST:
				setIcon(next.getY(), next.getX(), bodyHORIZONTAL);
				break;
			case NORTH, SOUTH:
				setIcon(next.getY(), next.getX(), bodyVERTICAL);
				break;
			}
			next = next.getNextNode();
		}
		
		printTail();
		reloadScore();
	}
	
	/* Print methods*/
	public void printSnakeHead() {
		switch(game.getSnake().getOrientation()) {
		case EAST:
			setIcon(game.getSnake().getY(), game.getSnake().getX(), headRIGHT);
			break;
		case NORTH:
			setIcon(game.getSnake().getY(), game.getSnake().getX(), headUP);
			break;
		case SOUTH:
			setIcon(game.getSnake().getY(), game.getSnake().getX(), headDOWN);
			break;
		case WEST:
			setIcon(game.getSnake().getY(), game.getSnake().getX(), headLEFT);
			break;
		default:
			break;
		
		}
	}
	public void printTail() {
		setIcon(game.getSnake().getTail().getY(), game.getSnake().getTail().getX(), grass);
	}
	public void printApple() {
		setIcon(game.getApple().getY(), game.getApple().getX(), apple);
	}
	public void printGrass(){ 						// Only called at the beginning of the game
		for(int i = 0; i < SQUARES_HEIGHT; i++) {
			for(int j = 0; j < SQUARES_WIDTH; j++) {
				gridLabels[i][j] = new JLabel();
				gridLabels[i][j].setIcon(new ImageIcon(grass));
				board.add(gridLabels[i][j]);
			}
		}
	}
	
	public void reloadScore() {
		timeLabel.setText("Time: "+minutes+":"+(seconds < 10 ? "0"+seconds : seconds));
		pointsLabel.setText("Points: "+points);
	}
	
	
	// Self-explanatory, done at the beginning
	public void loadIcons() {
		try{
			grass = ImageIO.read(new File("src/icons/Grass.png"));
			headUP = ImageIO.read(new File("src/icons/HeadUP.png"));
			headLEFT = ImageIO.read(new File("src/icons/HeadLEFT.png"));
			headRIGHT = ImageIO.read(new File("src/icons/HeadRIGHT.png"));
			headDOWN = ImageIO.read(new File("src/icons/HeadDOWN.png"));
			bodyHORIZONTAL = ImageIO.read(new File("src/icons/BodyHORIZONTAL.png"));
			bodyVERTICAL = ImageIO.read(new File("src/icons/BodyVERTICAL.png"));
			apple = ImageIO.read(new File("src/icons/Apple.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Generic method to set an icon somewhere
	public void setIcon(int y, int x, BufferedImage img) {
		gridLabels[y][x].setIcon(new ImageIcon(img));
	}
	
	// Converts Squares per Second to miliseconds
	public static long spsToMillis() {
		long miliseconds = 1000/SQUARES_PER_SECOND;
		return miliseconds;
	}
	
	
	/*************************KEY LISTENER EVENTS************************/
	@Override
	public void keyPressed(KeyEvent e) {
		if(!game.isFinished() && !pressed) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_UP -> {
					game.getSnake().setDirection(Direction.UP);
					pressed = true;
				}
				case KeyEvent.VK_LEFT -> {
					game.getSnake().setDirection(Direction.LEFT);
					pressed = true;
				}
				case KeyEvent.VK_RIGHT -> {
					game.getSnake().setDirection(Direction.RIGHT);
					pressed = true;
				}
				case KeyEvent.VK_DOWN -> {
					game.getSnake().setDirection(Direction.DOWN);
					pressed = true;
				}
			}
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	
}
