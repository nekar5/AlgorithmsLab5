package gamelogic;

public enum Player {
	first, second;
	
	public Player opponent() {
		return (this == first ? second : first);
	}
	
	public Field getField() {
		return (this == first ? Field.first : Field.second);
	}
}
