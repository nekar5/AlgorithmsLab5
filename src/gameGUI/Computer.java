package gameGUI;

import javax.swing.SwingWorker;

import algorithms.*;
import gamelogic.Player;
import gamelogic.Move;

public class Computer extends Strategy  {
	private Window master;
	private Player player;
	private SwingWorker<Move,Object> thought;
	private int choiceDepth;

	public Computer(Window master, Player player, int depth) {
		this.master = master;
		this.player = player;
		choiceDepth = depth;
	}
	
	@Override
	public void progress() {
		thought = new AlphaBeta(master, choiceDepth, player);
		//thought = new Minimax(master, choiceDepth, player);
		//thought = new RandomMove(master);
		thought.execute();
	}

	@Override
	public void stop() {
		if (thought != null) {
			thought.cancel(true);
		}
	}

	@Override
	public void click(int i, int j) {
	}
	
}
