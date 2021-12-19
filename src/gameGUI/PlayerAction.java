package gameGUI;

import gamelogic.Player;
import gamelogic.Field;
import gamelogic.Move;

public class PlayerAction extends Strategy {
	private Window master;
	
	public PlayerAction(Window master, Player player, int depth) {
		this.master = master;
	}
	
	@Override
	public void progress() {
	}

	@Override
	public void stop() {
	}
	
	public static int round(double x) {
		return (int)(x+0.5);
	}
	
	@Override
	public void click(int i, int j) {
		if (master.board.coordI == -1 && master.board.coordJ == -1) {
			if (master.game.field[i][j] == Field.empty) {
				master.board.coordJ = round(j);
				master.board.coordI = round(i);
			}
		} else if (i==master.board.coordI && j==master.board.coordJ) {
			master.board.coordI = -1;
			master.board.coordJ = -1;
		} else if (master.game.validMove(new Move(i, j, master.board.coordI, master.board.coordJ))) {
			if (master.moveMade(new Move(i, j, master.board.coordI, master.board.coordJ))) {
				master.board.coordI = -1;
				master.board.coordJ = -1;
			}
		}
	}
}
