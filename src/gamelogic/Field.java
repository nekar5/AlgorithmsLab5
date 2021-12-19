package gamelogic;

public enum Field {
	first, second, empty;
	
	public String toString() {
		switch (this) {
			case empty: return "_";
			case first: return "X";
			case second: return "O";
			default: return "?";
			}
	}
}
