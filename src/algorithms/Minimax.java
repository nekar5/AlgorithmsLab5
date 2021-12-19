package algorithms;

import javax.swing.SwingWorker;

import gameGUI.Window;
import gamelogic.Game;
import gamelogic.Player;
import gamelogic.Move;

public class Minimax  extends SwingWorker<Move, Object> {
	private Window master;
	private int depth;
	private Player player;
	
	public Minimax(Window master, int depth, Player player) {
		this.master = master;
		this.depth = depth;
		this.player = player;
	}
	
	@Override
	protected Move doInBackground() throws Exception {
		Game game = master.copyGame();
		RatedMove rm = minimax(0, game);
		assert (rm.move != null):"move is null TEST";
		return rm.move;
	}
	
	@Override
	public void done() {
		try {
			Move m = this.get();
			if (m != null) { master.moveMade(m); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RatedMove minimax(int d, Game game) {
		Player inProgress = null;
		switch (game.state()) {
		case FIRST_MOVES: inProgress = Player.first; break;
		case SECOND_MOVES: inProgress = Player.second; break;
		case SECOND_WIN:
			return new RatedMove(
					null,
					(player == Player.second ? Rate.WIN : Rate.DEFEAT));
		case FIRST_WIN:
			return new RatedMove(
					null,
					(player == Player.first ? Rate.WIN : Rate.DEFEAT));
		}
		assert (inProgress != null);

		if (d >= depth) {
			return new RatedMove(
					null,
					Rate.ratePosition(player, game));
		}

		Move best = null;
		int bestRating = 0;

		for (Move m : game.movesLeft()) {
			Game gameCopy = new Game(game);
			gameCopy.setField(m);
			int mRating = minimax(d+1, gameCopy).rating;
			if (best == null
				|| (inProgress == player && mRating > bestRating)//max
				|| (inProgress != player && mRating < bestRating)//min
				) {
				best = m;
				bestRating = mRating;
			}
		}

		assert (best != null);
		return new RatedMove(best, bestRating);
	}
}
