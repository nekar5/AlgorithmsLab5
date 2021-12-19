package algorithms;

import gamelogic.Move;

public class RatedMove {
	Move move;
	int rating;
	
	public RatedMove(Move move, int rating) {
		super();
		this.move = move;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "RatedMove [move=" + move + ", rating=" + rating + "]";
	}
	
}
