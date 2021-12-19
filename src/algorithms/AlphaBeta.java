package algorithms;

import javax.swing.SwingWorker;

import gameGUI.Window;
import gamelogic.Game;
import gamelogic.Player;
import gamelogic.Move;

public class AlphaBeta  extends SwingWorker<Move, Object> {

	private Window master;
	private int alpha = Rate.DEFEAT-1;
	private int beta = Rate.WIN+1;

	private int depth;
	private Player player;
	
	public AlphaBeta(Window master, int depth, Player player) {
		this.master = master;
		this.depth = depth;
		this.player = player;
	}
	
	@Override
	protected Move doInBackground() throws Exception {
		Game game = master.copyGame();
		RatedMove rm = alphaBeta(0, alpha, beta, game);
		assert (rm.move != null);
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

	private RatedMove alphaBeta(int d, int alpha, int beta, Game game) {
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
		
		if (inProgress == player) {
			int r = Rate.DEFEAT;
			for (Move m : game.movesLeft()) {
				Game gameCopy = new Game(game);
				gameCopy.setField(m);

				int mRating = alphaBeta(d+1, alpha, beta, gameCopy).rating;
				if (mRating >= r) {
					best = m;
					r = mRating;
				}
				alpha = Math.max(r, alpha);
				if (beta <= alpha) {
					break;
				}
			}
			return new RatedMove(best, r);
		} else {
			int r = Rate.WIN;
			for (Move m : game.movesLeft()) {
				Game gameCopy = new Game(game);
				gameCopy.setField(m);
				int mRating = alphaBeta(d+1, alpha, beta, gameCopy).rating;
				if (mRating <= r) {
					best = m;
					r = mRating;
				}
				beta = Math.min(r, beta);
				if (beta <= alpha) {
					break;
				}
			}
			return new RatedMove(best, r);
		}
	}
	
}
