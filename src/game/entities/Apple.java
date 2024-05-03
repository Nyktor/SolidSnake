package game.entities;

public class Apple extends Entity{
	
	private boolean spawned;
	
	public Apple() {
		this.spawned = false;
	}

	public boolean isSpawned() {
		return spawned;
	}

	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}

}
