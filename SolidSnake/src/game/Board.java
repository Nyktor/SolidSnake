package game;

import game.entities.Entity;

public class Board {
	
	protected int height, width;
	protected Entity[][] gb;
	
	public Board(int squaresHeight, int squaresWidth) {
		gb = new Entity[squaresHeight][squaresWidth];
		height = squaresHeight;
		width = squaresWidth;
	}

	public Entity getAt(int y, int x) {
		return gb[y][x];
	}

	public void setAt(Entity ent, int y, int x) {
		this.gb[y][x] = ent;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public boolean isEmpty(int y, int x) {
		return gb[y][x] == null;
	}
	
}
