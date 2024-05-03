package game.entities.snake;

public enum Direction {

	UP,
	LEFT,
	RIGHT,
	DOWN;
	
	public Direction getOppositeDirection() {
		switch(this) {
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		case UP:
			return DOWN;		
		}
		return null;
	}
	
}
