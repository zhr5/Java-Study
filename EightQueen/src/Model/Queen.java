package Model;

public class Queen {
	private boolean placed = false;
	private int y;
	
	public Queen(boolean placed,int y) {
		this.placed = placed;
		this.y=y;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}
	
}
