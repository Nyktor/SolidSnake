package game;

import java.util.Random;

import game.entities.Apple;
import game.entities.snake.Node;
import game.entities.snake.Orientation;
import game.entities.snake.Snake;
import game.entities.snake.Tail;

public class Game extends Board {
	
	private boolean finished;
	private boolean won;
	private boolean eatenAppleThisTurn;
	private int eatenApples;

	private Snake snake;
	private Apple apple;
	
	public Game(int squaresHeight, int squaresWidth) {
		super(squaresHeight, squaresWidth);
		
		this.finished = false;
		this.won = false;
		this.eatenApples = 0;
		
		this.snake = new Snake(squaresHeight/2, squaresWidth/2);
		this.apple = new Apple();
		
		spawnApple(squaresHeight, squaresWidth);
	}
	
	public void moveSnake() {
		int previousX = snake.getX(), previousY = snake.getY();
		Orientation previousOri = snake.getOrientation();
		
		// First moves the head
		switch(snake.getCurrentDirection()) {
		case DOWN:
			snake.setY(previousY + 1);
			break;
		case LEFT:
			snake.setX(previousX - 1);
			break;
		case RIGHT:
			snake.setX(previousX + 1);
			break;
		case UP:
			snake.setY(previousY - 1);
			break;
		}
		
		if(hasCollided()){
			this.finished = true; //finishes
			return;				// stops moving the snake
		
		}else {
			// If we eat an apple, we just add one node: the rest stays the same
			if(getAt(snake.getY(), snake.getX()) instanceof Apple) {
				
				eatenAppleThisTurn = true;
				
				setAt(snake, snake.getY(), snake.getX());	// Moves the head and eats the apple
				
				grow(previousY, previousX);					// Grows +1
				
				eatenApples++;
				if(eatenApples == this.height * this.width) {
					this.won = true;
					this.finished = true;
				}
				
				spawnApple(getHeight(), getWidth()); 		// Spawns another apple
				
			// Moves the whole body
			}else{
				if (eatenAppleThisTurn) eatenAppleThisTurn = false;
				setAt(snake, snake.getY(), snake.getX()); 	// Move head
				Node next = snake.getNextNode();			// Gets next node
				int nextX, nextY;
				Orientation nextOri;
				while(!next.isTail()){	// Does this while the next one's not a tail				
					nextX = next.getX();					// Stores the next node's coords
					nextY = next.getY();
					nextOri = next.getOrientation();
					
					next.setX(previousX);					// Changes the next node's coords to the previous one's coords 
					next.setY(previousY);
					next.setOrientation(previousOri);
					
					setAt(next, previousY, previousX);		// Moves the node in the board
					
					previousX = nextX;						// Previous "next" coordinates become next's "previous" stored coords
					previousY = nextY;
					previousOri = nextOri;
					next = next.getNextNode();				// Next node becomes next node XD
					
				}
				
				// This is the tail
				nextX = next.getX();
				nextY = next.getY();
				nextOri = next.getOrientation();
				
				next.setX(previousX);
				next.setY(previousY);
				next.setOrientation(previousOri);
				
				setAt(next, previousY, previousX);
				
				// Where the tail was, set null
				setAt(null, nextY, nextX);
			}
		}
		
	}
	
	// Called when an apple is eaten
	public void grow(int previousY, int previousX) {
		Node next = snake.getNextNode(), newNode;
		
		newNode = new Node(previousY, previousX, next);  // makes that the new one points to the one that previously was the head's next
		newNode.setOrientation(next.getOrientation());
		
		snake.setNextNode(newNode); // changes the head's next node to the new one

		setAt(newNode, newNode.getY(), newNode.getX());
		snake.addLength();
	}
	
	public boolean hasCollided() {
		/* If snake collides with:
		 * 
 				left wall				right wall				ceiling						floor*/
		if(snake.getX() < 0 || snake.getX() >= getWidth() || snake.getY() < 0 || snake.getY() >= getWidth()) {
			return true;
			
		// If snake collides with itself;
		}else if(this.getAt(snake.getY(), snake.getX()) instanceof Node && !(this.getAt(snake.getY(), snake.getX()) instanceof Tail)) {
			return true;
		}else {
			return false;
		}
	}
	
	// Spawn an Apple in a random x and a random y.
	public void spawnApple(int height, int width) {
		int randX = 0, randY = 0;
		do {
			Random rand = new Random();
			randX = rand.nextInt(width);
			randY = rand.nextInt(height);
		}while(!isEmpty(randY, randX));
		apple.setX(randX);
		apple.setY(randY);
		apple.setSpawned(true);
		setAt(apple, randY, randX);
	}

	/* GETTERS AND SETTERS */

	public Snake getSnake() {
		return snake;
	}

	public boolean isFinished() {
		return finished;
	}

	public Apple getApple() {
		return apple;
	}

	public boolean hasEatenAppleThisTurn() {
		return eatenAppleThisTurn;
	}

	public int getEatenApples() {
		return eatenApples;
	}

	public boolean isWon() {
		return won;
	}

}
