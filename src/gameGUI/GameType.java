package gameGUI;

import java.util.HashMap;
import java.util.Map;

public enum GameType {
	pp, cp, pc, cc;
	
	public int depthFirst = 3;
	public int depthSecond = 3;
	
	int difficultyEasy = 1;
	int difficultyMedium = 3;
	int difficultyHard = 5;
	
	protected Map<Integer, String> opponents = new HashMap<Integer, String>();
	{
		opponents.put(difficultyEasy, "EasyBot");
		opponents.put(difficultyMedium, "MediumBot");
		opponents.put(difficultyHard, "HardBot");
	}
	
	protected String[] names() {
		String[] players = new String[2];
		players[0] = "FIRST";
		players[1] = "SECOND";
		if (this == GameType.cp || this == GameType.cc) {
			if (opponents.containsKey(depthFirst)) players[0] = opponents.get(depthFirst);
			else players[0] = "Bot"+depthFirst;
		}
		if (this == GameType.pc || this == GameType.cc) {
			if (opponents.containsKey(depthSecond)) players[1] = opponents.get(depthSecond);
			else players[1] = "Bot"+depthSecond;
		}
		return players;
	}
}
