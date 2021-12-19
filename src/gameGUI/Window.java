package gameGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gamelogic.Game;
import gamelogic.Player;
import gamelogic.Move;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener {
	public Board board;
	public JLabel status;
	public Game game;
	public GameType gameType = GameType.pc;

	private Strategy strategyFirst;
	private Strategy strategySecond;
	
	private JMenuItem newGame = new JMenuItem("New game");
	private JMenuItem dim = new JMenuItem("Dimentions");
	
	private JMenuItem pp = new JMenuItem("Player vs. Player");
	
	private JMenuItem pc1 = new JMenuItem("Easy");
	private JMenuItem pc2 = new JMenuItem("Medium");
	private JMenuItem pc3 = new JMenuItem("Hard");
	
	private JMenuItem cp1 = new JMenuItem("Easy");
	private JMenuItem cp2 = new JMenuItem("Medium");
	private JMenuItem cp3 = new JMenuItem("Hard");
	
	private JMenuItem c1c1 = new JMenuItem("Easy vs. Easy");
	private JMenuItem c2c1 = new JMenuItem("Medium vs. Easy");
	private JMenuItem c3c1 = new JMenuItem("Hard vs. Easy");
	private JMenuItem c1c2 = new JMenuItem("Easy vs. Medium");
	private JMenuItem c2c2 = new JMenuItem("Medium vs. Medium");
	private JMenuItem c3c2 = new JMenuItem("Hard vs. Medium");
	private JMenuItem c1c3 = new JMenuItem("Easy vs. Hard");
	private JMenuItem c2c3 = new JMenuItem("Medium vs. Hard");
	private JMenuItem c3c3 = new JMenuItem("Hard vs. Hard");
	
	// nastavi barve
	private JMenuItem palette1 = new JMenuItem("Color palette 1");
	private JMenuItem palette2 = new JMenuItem("Color palette 2");
	private JMenuItem palette3 = new JMenuItem("Red&Orange");
	private JMenuItem palette4 = new JMenuItem("Green&Brown");
	private JMenuItem palette5 = new JMenuItem("Dark");
	private JMenuItem palette6 = new JMenuItem("Light Purple");
	
	private JMenuItem rules = new JMenuItem("Game rules");
	private JMenuItem exit = new JMenuItem("Exit");
	
	Image icon = Toolkit.getDefaultToolkit().getImage("Icon/Cram-icon.png");
	
	public Window() throws IOException {
		super();
		setTitle("Cram");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		this.setIconImage(icon);
	    
		board = new Board(this);
		GridBagConstraints board_layout = new GridBagConstraints();
		board_layout.gridx = 0;
		board_layout.gridy = 0;
		board_layout.fill = GridBagConstraints.BOTH;
		board_layout.weightx = 1.0;
		board_layout.weighty = 1.0;
		getContentPane().add(board, board_layout);
		
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		this.setLocationRelativeTo(null);
		this.setLocation(this.getX()-this.board.width/2, this.getY()-this.board.height/2);
		
		JMenuBar mb = new JMenuBar();
		
		newGame.addActionListener(this);
		mb.add(newGame);
		
		JMenu settings = new JMenu("Settings");
		
		pp.addActionListener(this);
		settings.add(pp);
		
		JMenu pc = new JMenu("Player vs. Computer");
		pc1.addActionListener(this);
		pc.add(pc1);
		pc2.addActionListener(this);
		pc.add(pc2);
		pc3.addActionListener(this);
		pc.add(pc3);
		settings.add(pc);
		
		JMenu cp = new JMenu("Computer vs. Player");
		cp1.addActionListener(this);
		cp.add(cp1);
		cp2.addActionListener(this);
		cp.add(cp2);
		cp3.addActionListener(this);
		cp.add(cp3);
		settings.add(cp);
		
		JMenu pp = new JMenu("Computer vs. Computer");
		c1c1.addActionListener(this);
		pp.add(c1c1);
		c2c1.addActionListener(this);
		pp.add(c2c1);
		c3c1.addActionListener(this);
		pp.add(c3c1);
		c1c2.addActionListener(this);
		pp.add(c1c2);
		c2c2.addActionListener(this);
		pp.add(c2c2);
		c3c2.addActionListener(this);
		pp.add(c3c2);
		c1c3.addActionListener(this);
		pp.add(c1c3);
		c2c3.addActionListener(this);
		pp.add(c2c3);
		c3c3.addActionListener(this);
		pp.add(c3c3);
		settings.add(pp);
		
		settings.addSeparator();
		dim.addActionListener(this);
		settings.add(dim);
		
		mb.add(settings);
		
		JMenu Design = new JMenu("Design");
		
		palette1.addActionListener(this);
		Design.add(palette1);
		palette2.addActionListener(this);
		Design.add(palette2);
		palette3.addActionListener(this);
		Design.add(palette3);
		palette4.addActionListener(this);
		Design.add(palette4);
		palette5.addActionListener(this);
		Design.add(palette5);
		palette6.addActionListener(this);
		Design.add(palette6);
		
		mb.add(Design);
		
		rules.addActionListener(this);
		mb.add(rules);
		
		exit.addActionListener(this);
		mb.add(exit);
		
		setJMenuBar(mb);
		
		newGame();
	}

	public void newGame() {
		if (strategyFirst != null) { strategyFirst.stop(); }
		if (strategySecond != null) { strategySecond.stop(); }
		game = new Game();
		if (gameType == GameType.pp) {
			strategyFirst = new PlayerAction(this, Player.first, gameType.depthFirst);
			strategySecond = new PlayerAction(this, Player.second, gameType.depthSecond);
		} else if (gameType == GameType.pc) {
			strategyFirst = new PlayerAction(this, Player.first, gameType.depthFirst);
			strategySecond = new Computer(this, Player.second, gameType.depthSecond);
		} else if (gameType == GameType.cp) {
			strategyFirst = new Computer(this, Player.first, gameType.depthFirst);
			strategySecond = new PlayerAction(this, Player.second, gameType.depthSecond);
		} else if (gameType == GameType.cc) {
			strategyFirst = new Computer(this, Player.first, gameType.depthFirst);
			strategySecond = new Computer(this, Player.second, gameType.depthSecond);
		}

		switch (game.state()) {
			case FIRST_MOVES: strategyFirst.progress(); break;
			case SECOND_MOVES: strategySecond.progress(); break;
			default: break;
		}
		board.coordI = -1;
		board.coordJ = -1;
		updateGUI();
}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			newGame();
		} else if (e.getSource() == dim) {
			String n = JOptionPane.showInputDialog("Height: ");
			String m = JOptionPane.showInputDialog("Width: ");
			Game.boardHeight = Integer.parseInt(n);
			Game.boardWidth = Integer.parseInt(m);
			newGame();
		} else if (e.getSource() == pp) {
			gameType = GameType.pp;
			newGame();
		} else if (e.getSource() == pc1) {
			gameType = GameType.pc;
			gameType.depthSecond = gameType.difficultyEasy;
			newGame();
		} else if (e.getSource() == pc2) {
			gameType = GameType.pc;
			gameType.depthSecond = gameType.difficultyMedium;
			newGame();
		} else if (e.getSource() == pc3) {
			gameType = GameType.pc;
			gameType.depthSecond = gameType.difficultyHard;
			newGame();
		} else if (e.getSource() == cp1) {
			gameType = GameType.cp;
			gameType.depthFirst = gameType.difficultyEasy;
			newGame();
		} else if (e.getSource() == cp2) {
			gameType = GameType.cp;
			gameType.depthFirst = gameType.difficultyMedium;
			newGame();
		} else if (e.getSource() == cp3) {
			gameType = GameType.cp;
			gameType.depthFirst = gameType.difficultyHard;
			newGame();
		} else if (e.getSource() == c1c1) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyEasy;
			gameType.depthSecond = gameType.difficultyEasy;
			newGame();
		} else if (e.getSource() == c1c2) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyEasy;
			gameType.depthSecond = gameType.difficultyMedium;
			newGame();
		} else if (e.getSource() == c1c3) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyEasy;
			gameType.depthSecond = gameType.difficultyHard;
			newGame();
		} else if (e.getSource() == c2c1) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyMedium;
			gameType.depthSecond = gameType.difficultyEasy;
			newGame();
		} else if (e.getSource() == c2c2) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyMedium;
			gameType.depthSecond = gameType.difficultyMedium;
			newGame();
		} else if (e.getSource() == c2c3) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyMedium;
			gameType.depthSecond = gameType.difficultyHard;
			newGame();
		} else if (e.getSource() == c3c1) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyHard;
			gameType.depthSecond = gameType.difficultyEasy;
			newGame();
		} else if (e.getSource() == c3c2) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyHard;
			gameType.depthSecond = gameType.difficultyMedium;
			newGame();
		} else if (e.getSource() == c3c3) {
			gameType = GameType.cc;
			gameType.depthFirst = gameType.difficultyHard;
			gameType.depthSecond = gameType.difficultyHard;
			newGame();
		} else if (e.getSource() == palette1) {
			board.colorEmpty = Color.lightGray;
			board.colorOne = Color.darkGray;
			board.colorTwo = Color.red;
			board.colorMarked = Color.orange;
			board.setBackground(Color.WHITE);
			updateGUI();
		} else if (e.getSource() == palette2) {
			board.colorEmpty = new Color(164,181,199);
			board.colorOne = new Color(224, 168, 109);
			board.colorTwo = new Color(96, 49, 29);
			board.colorMarked = new Color(253, 241, 229);
			board.setBackground(new Color(9,5,6));
			updateGUI();
		} else if (e.getSource() == palette3) {
			board.colorEmpty = Color.gray;
			board.colorOne = new Color(162, 17, 16);
			board.colorTwo = new Color(225, 57, 2);
			board.colorMarked = Color.lightGray;
			board.setBackground(Color.darkGray);
			updateGUI();
		} else if (e.getSource() == palette4) {
			board.colorEmpty = new Color(127, 57, 2);
			board.colorOne = new Color(27, 57, 2);
			board.colorTwo = new Color(9, 45, 7);
			board.colorMarked = new Color(117, 112, 168);
			board.setBackground(new Color(89, 138, 2));
			updateGUI();
		} else if (e.getSource() == palette5) {
			board.colorEmpty = Color.darkGray;
			board.colorOne = Color.gray;
			board.colorTwo = Color.lightGray;
			board.colorMarked = Color.yellow;
			board.setBackground(Color.BLACK);
			updateGUI();
		} else if (e.getSource() == palette6) {
			board.colorEmpty = Color.white;
			board.colorOne = new Color(97, 101, 143);
			board.colorTwo = new Color(41, 60, 114);
			board.colorMarked = new Color(153, 53, 115);
			board.setBackground(new Color(181, 182, 253));
			updateGUI();
		} else if (e.getSource() == rules) {
			
			JFrame ruleWindow = new JFrame();
			ruleWindow.setTitle("Rules");
			ruleWindow.setFont(new Font(status.getFont().getName(),
				    status.getFont().getStyle(),
				    14));
			final int windowWidth = 400;
			final int windowHeight = 300;
			ruleWindow.setPreferredSize(new Dimension(windowWidth, windowHeight));
			JLabel rulesText = new JLabel("<html>"
					+ "<h2 style='padding: 8pt;'>Cram game rules</h2>"
					+ "<p style='border:3px solid #ffa500; background-color: #eecc44; padding: 8pt; margin: 3pt 0 3pt 0; font-size:120%;'>"
					+ "Two players place dominos in board </br>"
					+ "(here domino = 1:2 field) </br>"
					+ "The winner is the one </br>"
					+ "who places a domino the last.</p>"
					+ "<p style='margin: 10pt; font-size:110%'>"
					+ "Click on two neighbouring fields to place a domino.</html>");
			ruleWindow.getContentPane().add(rulesText);
			ruleWindow.setIconImage(icon);
			
			ruleWindow.setLocationRelativeTo(this);
			ruleWindow.setLocation(ruleWindow.getX()-windowWidth/2, ruleWindow.getY()-windowHeight/2);
			
			ruleWindow.pack();
			ruleWindow.setVisible(true);
			
		} else if (e.getSource() == exit) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	

	public boolean moveMade(Move m) {
		if (game.setField(m)) {
			updateGUI();
			switch (game.state()) {
				case FIRST_MOVES: strategyFirst.progress(); break;
				case SECOND_MOVES: strategySecond.progress(); break;
				case FIRST_WIN: break;
				case SECOND_WIN: break;
			}
			return true;
		} else return false;
	}
	
	public void updateGUI() {
		if (game == null) {
			status.setText("Game not in progress");
		}
		else {
			switch(game.state()) {
			case FIRST_MOVES: {
				status.setForeground (board.colorOne);
				if (gameType == GameType.cp || gameType == GameType.cc) status.setText(gameType.names()[0]+" is thinking ...");
				else status.setText("Moves: "+gameType.names()[0]);
				break;}
			case SECOND_MOVES: {
				status.setForeground (board.colorTwo);
				if (gameType == GameType.pc || gameType == GameType.cc) status.setText(gameType.names()[1]+" is thinking ...");
				else status.setText("Moves: "+gameType.names()[1]);
				break;}
			case FIRST_WIN: status.setForeground (board.colorOne); status.setText("Winner: "+gameType.names()[0]); break;
			case SECOND_WIN: status.setForeground (board.colorTwo); status.setText("Winner: "+gameType.names()[1]); break;
			}
		}
		repaint();
	}
	
	public void clickField(int i, int j) {
		if (game != null) {
			switch (game.state()) {
			case SECOND_MOVES:
				strategySecond.click(i, j);
				break;
			case FIRST_MOVES:
				strategyFirst.click(i, j);
				break;
			default:
				break;
			}
		}		
	}
	
	public Game copyGame() {
		return new Game(game);
	}
}
