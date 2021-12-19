package algorithms;

import javax.swing.SwingWorker;

import gameGUI.Window;
import gamelogic.Game;
import gamelogic.Move;

public class RandomMove extends SwingWorker<Move, Object> {
	private Window master;
		
	public RandomMove(Window master) {
		this.master = master;
	}
	
	@Override
	protected Move doInBackground() throws Exception {
		Game game = master.copyGame();
		if (this.isCancelled()) {
			System.out.println("Interrupted!");
			return null;
		}
		
		Move m = game.movesLeft().iterator().next();
		return m;
	}
	
	@Override
	public void done() {
		try {
			Move m = this.get();
			if (m != null) { master.moveMade(m); }
		} catch (Exception e) {
		}
	}
}
