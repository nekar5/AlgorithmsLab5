package gamelogic;

import java.util.HashSet;
import java.util.Set;

public class Game {
	public static int boardHeight = 8;
	public static int boardWidth = 8;
	public Field[][] field;
	protected Player inProgress;
	
	public Game() {
		field = new Field[boardHeight][boardWidth];
		for(int i=0; i<boardHeight; i++) {
			for(int j=0; j<boardWidth; j++) {
				field[i][j] = Field.empty;
			}
		}
		inProgress = Player.first;
	}
	
	public Game(Game game) {
		field = new Field[boardHeight][boardWidth];
		for(int i=0; i<boardHeight; i++) {
			for(int j=0; j<boardWidth; j++) {
				field[i][j] = game.field[i][j];
			}
		}
		inProgress = game.inProgress;
	}
	
	public Set<Move> movesLeft() {
		Set<Move> left = new HashSet<Move>();
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth-1; j++) {
				if (field[i][j] == Field.empty && field[i][j+1] == Field.empty) {
					left.add(new Move(i, j, i, j+1));
				}
			}
		}
		for (int i = 0; i < boardHeight-1; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (field[i][j] == Field.empty && field[i+1][j] == Field.empty) {
					left.add(new Move(i, j, i+1, j));
				}
			}
		}
		return left;
	}
	
	public boolean movePossible() {
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth-1; j++) {
				if (field[i][j] == Field.empty && field[i][j+1] == Field.empty) {
					return true;
				}
			}
		}
		for (int i = 0; i < boardHeight-1; i++) {
			for (int j = 0; j < boardWidth; j++) {
				if (field[i][j] == Field.empty && field[i+1][j] == Field.empty) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean validMove(Move move) {
		if (move.getY2()<boardHeight && move.getX2()<boardWidth &&
				move.getY1()>=0 && move.getX1()>=0 &&
				field[move.getY1()][move.getX1()] == Field.empty && field[move.getY2()][move.getX2()] == Field.empty &&
				Math.abs(move.getY1()-move.getY2())+Math.abs(move.getX1()-move.getX2()) == 1
				) {
					return true;
		}
		return false;
	}
	
	public State state() {
		State s;
		if (movePossible()) {
			if (inProgress == Player.first) {
				s = State.FIRST_MOVES;
			} else {
				s = State.SECOND_MOVES;
			}
			return s;
		} else {
			if (inProgress == Player.first) {
				s = State.SECOND_WIN;
			} else {
				s = State.FIRST_WIN;
			}
			return s;
		}
	}

	public boolean setField(Move m) {
		if (validMove(m)) {
			field[m.getY1()][m.getX1()] = inProgress.getField();
			field[m.getY2()][m.getX2()] = inProgress.getField();
			inProgress = inProgress.opponent();
			return true;
		} else {
			System.out.println("Can not set!");
			return false;
		}
	}
}
