package gamelogic;

public class Move {
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public Move(int y1, int x1, int y2, int x2) {
		if (x1<x2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		} else if (x1>x2) {
			this.x1 = x2;
			this.y1 = y2;
			this.x2 = x1;
			this.y2 = y1;
		} else if (y1<y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		} else {
			this.x1 = x2;
			this.y1 = y2;
			this.x2 = x1;
			this.y2 = y1;
		}
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}
	
	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}
}
