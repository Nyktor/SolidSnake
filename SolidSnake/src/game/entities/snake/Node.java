package game.entities.snake;

import game.entities.Entity;

public class Node extends Entity{
	
	private Node nextNode;
	private Orientation orientation;

	public Node(int y, int x) {
		this.setX(x);
		this.setY(y);
		this.setNextNode(null);
	}
	public Node(int y, int x, Node nextNode) {
		this.setX(x);
		this.setY(y);
		this.setNextNode(nextNode);
	}

	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}

	public boolean hasNextNode() {
		return this.nextNode != null;
	}
	

	public Node getNextNode() {
		return nextNode;
	}
	
	public boolean isTail() {
		return this instanceof Tail;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	public void setOrientation(Direction direction) {
		this.orientation = switch(direction) {
			case UP -> {
				yield Orientation.NORTH;
			}
			case LEFT -> {
				yield Orientation.WEST;
			}
			case RIGHT -> {
				yield Orientation.EAST;
			}
			case DOWN -> {
				yield Orientation.SOUTH;
			}
		};
	}

}
