package algorithms;

import gameGUI.Board;
import gamelogic.Game;
import gamelogic.Player;
import gamelogic.Field;

public class Rate {
	public static final int WIN = (1 << 20);
	public static final int DEFEAT = -WIN;
	
	protected static int[] minMaxMoves(Game game) {
		int[][][] mM = new int[Game.boardHeight+3][Game.boardWidth+3][2];
		for (int i=0; i<Game.boardHeight+3; i++) {
			for (int j=0; j<Game.boardWidth+3; j++) {
				mM[i][j] = new int[] {0, 0};
				if (i>=3 && j>=3) {
					mM[i][j][1] = mM[i-1][j][1] +
							mM[i][j-1][1] - mM[i-1][j-1][1];
					if (i==3 && j>3) {
						if (mM[i][j-1][1] - mM[i][j-2][1] == mM[i-1][j-1][1] - mM[i-1][j-2][1] &&
								game.field[i-3][j-3] == Field.empty && game.field[i-3][j-4] == Field.empty) {
							mM[i][j][1] = mM[i][j][1]+1;
						}
					} else if (i>3 && j==3) {
						if (mM[i-1][j][1] - mM[i-2][j][1] == mM[i-1][j-1][1] - mM[i-2][j-1][1] &&
								game.field[i-3][j-3] == Field.empty && game.field[i-4][j-3] == Field.empty) {
							mM[i][j][1] = mM[i][j][1]+1;
						}
					} else if (j>3 && i>3) {
						if ((mM[i][j-1][1] - mM[i][j-2][1] == mM[i-1][j-1][1] - mM[i-1][j-2][1] &&
								game.field[i-3][j-3] == Field.empty && game.field[i-3][j-4] == Field.empty)||
								(mM[i-1][j][1] - mM[i-2][j][1] == mM[i-1][j-1][1] - mM[i-2][j-1][1] &&
								game.field[i-3][j-3] == Field.empty && game.field[i-4][j-3] == Field.empty)) {
							mM[i][j][1] = mM[i][j][1]+1;
						}
					}
				}
			}
		}
		
		for (int i=0; i<Game.boardHeight; i++) {
			for (int j=0; j<Game.boardWidth; j++) {
				if (game.field[i][j] == Field.empty) {
					int emptyNeighbours = 0;
					if (i > 0 && game.field[i-1][j] == Field.empty) emptyNeighbours ++;
					if (i < Game.boardHeight-1 && game.field[i+1][j] == Field.empty) emptyNeighbours ++;
					if (j > 0 && game.field[i][j-1] == Field.empty) emptyNeighbours ++;
					if (j < Game.boardWidth-1 && game.field[i][j+1] == Field.empty) emptyNeighbours ++;
					if (emptyNeighbours == 1) mM[Game.boardHeight+2][Game.boardWidth+2][0] += 3;
					else if (emptyNeighbours > 1) mM[Game.boardHeight+2][Game.boardWidth+2][0] += 2;
				}
			}
		}
		mM[Game.boardHeight+2][Game.boardWidth+2][0] = mM[Game.boardHeight+2][Game.boardWidth+2][0]/6;

		return mM[Game.boardHeight+2][Game.boardWidth+2];
	}
	
	public static int ratePosition(Player player, Game game) {
		switch (game.state()) {
		case FIRST_WIN:
			return (player == Player.first ? WIN : DEFEAT);
		case SECOND_WIN:
			return (player == Player.second ? WIN : DEFEAT);
		case FIRST_MOVES:
		case SECOND_MOVES:
			int ratingFirst = 0;
			int ratingSecond = 0;
			int[] mM = minMaxMoves(game);

			if (mM[1]-mM[0]==0) {
				if (mM[1]*50/2-Board.round(mM[1]/2)*50==0) {
					ratingFirst = DEFEAT/2;
					ratingSecond = WIN/2;
				} else {
					ratingFirst = WIN/2;
					ratingSecond = DEFEAT/2;
				}
			} else {
				ratingFirst = (mM[1]-mM[0])*mM[1];
				ratingSecond = (mM[1]-mM[0]-1)*mM[1];
			}
			
			return (player == Player.second ? (ratingSecond - ratingFirst/2) : (ratingFirst - ratingSecond/2));
		}
		assert false;
		return 53;
	}
}
