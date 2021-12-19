package gameGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import gamelogic.Game;
import gamelogic.Field;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener {
	
	protected Window window;
	protected Color colorEmpty = Color.lightGray;
	protected Color colorOne = Color.darkGray;
	protected Color colorTwo = Color.red;
	protected Color colorMarked = Color.orange;
	protected Color colorWindow = Color.WHITE;
	protected int width = Game.boardWidth * 64;
	protected int height = Game.boardHeight * 64;
	
	protected int coordI = -1;
	protected int coordJ = -1;
	
	protected double space = 0.1;
	
	public Board(Window window) {
		this.window = window;
		this.setBackground(colorWindow);
		this.addMouseListener(this);
	}
	
	protected int boardSize() {
		return round(Math.min(window.board.getWidth()/Game.boardWidth, window.board.getHeight()/Game.boardHeight));
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public static int round(double x) {
		return (int)(x+0.5);
	}
	
	public void paintComponent(Graphics g) {
		if (window.game == null) return;
		super.paintComponent(g);
		for (int i=0; i<Game.boardHeight; i++) {
			for (int j=0; j<Game.boardWidth; j++) {
				if (window.game.field[i][j]==Field.first) {
					g.setColor(colorOne);
				} else if (window.game.field[i][j]==Field.second) {
					g.setColor(colorTwo);
				} else if (i==coordI && j==coordJ) {
					g.setColor(colorMarked);
				} else {
					g.setColor(colorEmpty);
				}
				g.fillRect(round((j+space/2)*boardSize()), round((i+space/2)*boardSize()), 
						round((1-space)*boardSize()),
						round((1-space)*boardSize()));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int inxI = -1;
		if (round((1-space)*50)>Math.abs(e.getY()*100/boardSize()-round(e.getY()/boardSize())*100-50)) {
			inxI = round(e.getY()/boardSize());
		}
		int idxJ = -1;
		if (round((1-space)*50)>Math.abs(e.getX()*100/boardSize()-round(e.getX()/boardSize())*100-50)) {
			idxJ = round(e.getX()/boardSize());
		}

		if (inxI >= 0 &&
				inxI < Game.boardHeight &&
				idxJ >= 0 &&
				idxJ < Game.boardWidth &&
				window.game.field[inxI][idxJ] == Field.empty)
					window.clickField(inxI, idxJ);
		window.updateGUI();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
