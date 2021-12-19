package gameGUI;

public abstract class Strategy {
	public abstract void progress();
	public abstract void stop();
	public abstract void click(int i, int j);
}
