package game.entities.snake;

public class Snake extends Node{
	
	private int length;
	private Direction currentDirection;
	private Tail tail;
	
	public Snake(int initialY, int initialX) {
		super(initialY, initialX, new Tail(initialY, initialX+1));
		this.length = 1;
		this.currentDirection = Direction.LEFT;
		this.tail = (Tail) this.getNextNode();
		this.setOrientation(Orientation.WEST);
	}

	public int getLength() {
		return length;
	}
	
	public void addLength() {
		this.length++;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}
	
	public void setDirection(Direction mov) {
		if(mov == this.getCurrentDirection().getOppositeDirection()) return;
		this.currentDirection = mov;
		this.setOrientation(mov);
	}

	public Tail getTail() {
		return tail;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}

}
