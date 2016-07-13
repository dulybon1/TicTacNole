/*
 * Bonheur, Duly
 * Project 1
 * COP3252
 * 02/07/2016
 * 
 * This file TicTacWindow.java implements a version of the game
 * tictactoe that allows multi-player games as it does one player
 * against the computer(not a very smart one).
 * 
 */


import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Random;


public class TicTacWindow extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, newGameButton, settingsButton; 
	private int turn = 0;
	private int numberOfPlays = 0;
	private JButton currentButton;
	private String message = "CREATED BY: DULY BONHEUR";
	private static int multiplayer;
	private int taken[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private int takenIndex = 0;
	
	//icons for the buttons
	private Icon o = new ImageIcon(getClass().getResource("o.png"));
	private Icon x = new ImageIcon(getClass().getResource("x.png"));
	private Icon w = new ImageIcon(getClass().getResource("w.png"));
	private Icon newGame = new ImageIcon(getClass().getResource("newGame.png"));
	private Icon settings = new ImageIcon(getClass().getResource("settings.png"));
	private Icon r2d2 = new ImageIcon(getClass().getResource("r2d2.png"));
	
	
	private JPanel buttonPanel1;
	private JPanel buttonPanel2;
	private JPanel buttonPanel3;
	
	
	private String messageForO = "IT'S YOUR TRUN O";
	private String messageForX = "IT'S YOUR TRUN X";

	JLabel whosTurn = new JLabel(messageForO);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new TicTacWindow();
		multiplayer = JOptionPane.showConfirmDialog(null, "MULTIPLAYER?", " ",JOptionPane.YES_NO_OPTION);

	}
	
	public TicTacWindow()
	{
		super("TIC_TAC_NOLE");
		this.setSize(500,600);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		
		int xPos = d.width/2 - this.getWidth()/2;
		int yPos = d.height/2 - this.getHeight()/2;
		
		this.setLocation(xPos, yPos);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("TIC_TAC_NOLE");
		
		//start the game with blank icons and turn = 0
		startGame();
		
		//create a panel
		JPanel p = new JPanel();
		p.setAlignmentX(CENTER_ALIGNMENT);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		
		//define the labels in panel
		JLabel l = new JLabel("FLORIDA STATE UNIVERSITY");
		
		//label for header
		JPanel labelPanel = new JPanel();
		labelPanel.setSize(500, 100);
		labelPanel.add(l);
		
		//set alignment
		whosTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel f = new JLabel(message);
		f.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//add label to panel
		p.add(labelPanel);
		
		buildGridWithFlow();
		
		p.add(buttonPanel1);
		p.add(buttonPanel2);
		p.add(buttonPanel3);
		
		//add who's turn label to panel
		p.add(whosTurn);
		
		//create flowlayout panel for the buttons
		JPanel buttonPanel = new JPanel();
		
		//create the two buttons
		newGameButton = new JButton(newGame);
		settingsButton = new JButton(settings);
		
		buttonPanel.add(newGameButton);
		buttonPanel.add(Box.createHorizontalStrut(200));
		buttonPanel.add(settingsButton);
		
		p.add(buttonPanel);
		
		//add footer
		p.add(f);
		
		//put panel in the window
		this.add(p);
		
		PlayHandler handler = new PlayHandler();
		setHandler(handler);
		
		NewGameHandler ngHandler = new NewGameHandler();
		newGameButton.addActionListener(ngHandler);
		
		SettingsHandler setHandler = new SettingsHandler();
		settingsButton.addActionListener(setHandler);
		
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	//increment the turn variable
	public void incrementTurn()
	{
		if(turn == 1)
		{
			turn = 0;
		}
		else
		{
			turn++;
		}
	}
	
	//game is won, disable all buttons
	public void disableAllButtons()
	{
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
		b8.setEnabled(false);
		b9.setEnabled(false);
	}
	
	//add the buttons to the grid
	

	private void buildGridWithFlow()
	{
		buttonPanel1 = new JPanel();
		buttonPanel1.setSize(500, 100);
		
		buttonPanel1.add(b1);
		buttonPanel1.add(b2);
		buttonPanel1.add(b3);
		
		buttonPanel2 = new JPanel();
		buttonPanel2.setSize(500, 100);
		
		//add buttons to the panel
		buttonPanel2.add(b4);
		buttonPanel2.add(b5);
		buttonPanel2.add(b6);
		
		buttonPanel3 = new JPanel();
		buttonPanel3.setSize(500, 100);
		
		buttonPanel3.add(b7);
		buttonPanel3.add(b8);
		buttonPanel3.add(b9);
		
	}

	//inner class for the handler
	
	private class NewGameHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			startNewGame();
			
			//System.out.println("start a new game");
			
		}
		
	}
	
	private class SettingsHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			multiplayer = JOptionPane.showConfirmDialog(null, "MULTIPLAYER?", " ",JOptionPane.YES_NO_OPTION);
			startNewGame();
		}
		
	}

	private class PlayHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			currentButton = (JButton) e.getSource();
			if(multiplayer == 0)
			{
				if(turn == 0)
				{
					playForO(currentButton);
					incrementTurn();
					currentButton.setEnabled(false);
					whosTurn.setText(messageForX);
				
				
					//check for who won
					horizontalCheck();
					verticalCheck();
					diagonalCheck();
				}
				else
				{
					playForX(currentButton);
					incrementTurn();
					currentButton.setEnabled(false);
					whosTurn.setText(messageForO);
				
					//check for who won
					horizontalCheck();
					verticalCheck();
					diagonalCheck();
				}
			}
			else
			{
				//we are playing with the robot
				whosTurn.setText(messageForO);
				
				playForO(currentButton);
				currentButton.setEnabled(false);
				
				JButton buttons[] = {b1, b2, b3, b4, b5, b6, b7, b8, b9};
				int buttonIndex = findButtonIndex(currentButton, buttons);
				
				System.out.println("currentButton: " + buttonIndex);
				
				//update taken and takenIndex
				taken[takenIndex] = buttonIndex;
				takenIndex++;
				
				
				Random generator = new Random();
				int chosen = generator.nextInt(9);
				
				if(takenIndex <= 8)
				{
					while(isChoiceInTaken(chosen))
					{
						chosen = generator.nextInt(9);
						System.out.println("chosen: " + chosen);
						System.out.println("is choice in taken: " + isChoiceInTaken(chosen));
					}
					
					playForRobot(buttons[chosen]);
					buttons[chosen].setEnabled(false);
					
					//update taken and takenIndex
					taken[takenIndex] = chosen;
					takenIndex++;
				}
				
				horizontalRobotCheck();
				verticalRobotCheck();
				diagonalRobotCheck();
				
			}
			
			if(numberOfPlays ==9 && (whosTurn.getText() != "O HAS WON THE GAME!!" || whosTurn.getText() != "X HAS WON THE GAME!!" 
					                                                              || whosTurn.getText() != "R2D2 HAS WON THE GAME!!"))
			{
				whosTurn.setText("NO WIN!! PLAY NEW GAME");
			}
			
			
			
		}

		
		
	}
	
	//clear the taken array
	public void clearTaken()
	{
		for(int i = 0; i < taken.length;i++)
		{
			taken[i] = -1;
		}
		takenIndex = 0;
	}
	
	//which button was pressed
	public int findButtonIndex(JButton b, JButton ar[])
	{
		int index = -1;
		for(int i = 0; i < ar.length; i++)
		{
			if(ar[i] == b)
			{
				index = i;
			}
		}
		return index;
	}
	
	//look for the choice in taken
	public boolean isChoiceInTaken(int choice)
	{
		boolean itIs = false;
		for(int i = 0; i < taken.length; i++)
		{
			if(taken[i] == choice)
			{
				itIs = true;
			}
		}
		return itIs;
	}
	
	//check the rows for winners
 	public void horizontalCheck()
	{
		//b1, b2, b3
		//b4, b5, b6
		//b7, b8, b9
		if(b1.getName() == b2.getName() && b1.getName() == b3.getName() && b1.getName() != null)
		{
			//we have a winner
			if(b1.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b4.getName() == b5.getName() && b4.getName() == b6.getName() && b4.getName() != null)
		{
			//we have a winner
			if(b4.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b7.getName() == b8.getName() && b7.getName() == b9.getName() && b7.getName() != null)
		{
			//we have a winner
			if(b7.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
				
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
	}
 	
 	public void horizontalRobotCheck()
	{
		//b1, b2, b3
		//b4, b5, b6
		//b7, b8, b9
		if(b1.getName() == b2.getName() && b1.getName() == b3.getName() && b1.getName() != null)
		{
			//we have a winner
			if(b1.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b4.getName() == b5.getName() && b4.getName() == b6.getName() && b4.getName() != null)
		{
			//we have a winner
			if(b4.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b7.getName() == b8.getName() && b7.getName() == b9.getName() && b7.getName() != null)
		{
			//we have a winner
			if(b7.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
				
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
	}
	
	//check diagonally for winners 
	public void diagonalCheck()
	{
		//1, 5, 9
		//7, 5, 3
		if(b1.getName() == b5.getName() && b1.getName() == b9.getName() && b1.getName() != null)
		{
			//we have a winner
			if(b1.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
				
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b3.getName() == b5.getName() && b3.getName() == b7.getName() && b3.getName() != null)
		{
			//we have a winner
			if(b3.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
	}
	
	public void diagonalRobotCheck()
	{
		//1, 5, 9
		//7, 5, 3
		if(b1.getName() == b5.getName() && b1.getName() == b9.getName() && b1.getName() != null)
		{
			//we have a winner
			if(b1.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
				
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b3.getName() == b5.getName() && b3.getName() == b7.getName() && b3.getName() != null)
		{
			//we have a winner
			if(b3.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
	}
	
	//check the columns for winners
	public void verticalCheck()
	{
		//b1, b4, b7
		//b2, b5, b8
		//b3, b6, b9
		if(b1.getName() == b4.getName() && b1.getName() == b7.getName() && b1.getName() != null)
		{
			//we have a winner
			if(b1.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b2.getName() == b5.getName() && b2.getName() == b8.getName() && b2.getName() != null)
		{
			//we have a winner
			if(b2.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b3.getName() == b6.getName() && b3.getName() == b9.getName() && b3.getName() != null)
		{
			//we have a winner
			if(b3.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
				
			}
			else
			{
				whosTurn.setText("X HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		
	}
	
	public void verticalRobotCheck()
	{
		//b1, b4, b7
		//b2, b5, b8
		//b3, b6, b9
		if(b1.getName() == b4.getName() && b1.getName() == b7.getName() && b1.getName() != null)
		{
			//we have a winner
			if(b1.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b2.getName() == b5.getName() && b2.getName() == b8.getName() && b2.getName() != null)
		{
			//we have a winner
			if(b2.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		if(b3.getName() == b6.getName() && b3.getName() == b9.getName() && b3.getName() != null)
		{
			//we have a winner
			if(b3.getName() == "ooo")
			{
				whosTurn.setText("O HAS WON THE GAME!!");
				
			}
			else
			{
				whosTurn.setText("R2D2 HAS WON THE GAME!!");
			}
			disableAllButtons();
		}
		
	}
	
	//what happens when player X plays
 	public void playForX(JButton b) 
	{
		b.setIcon(x);
		b.setName("xxx");
		numberOfPlays++;
	}

	//what happens when player O plays
	public void playForO(JButton b)
	{
		b.setIcon(o);
		b.setName("ooo");
		numberOfPlays++;
		
	}
	
	//What happens when robot plays
	public void playForRobot(JButton b)
	{
		b.setIcon(r2d2);
		b.setName("r2d2");
		numberOfPlays++;
		
	}
	
	//Initialize the game
	public void startGame()
	{
		b1 = new JButton(w);
		b2 = new JButton(w);
		b3 = new JButton(w);
		b4 = new JButton(w);
		b5 = new JButton(w);
		b6 = new JButton(w);
		b7 = new JButton(w);
		b8 = new JButton(w);
		b9 = new JButton(w);
		
		b1.setSize(100, 100);
		b2.setSize(100, 100);
		b3.setSize(100, 100);
		b4.setSize(100, 100);
		b5.setSize(100, 100);
		b6.setSize(100, 100);
		b7.setSize(100, 100);
		b8.setSize(100, 100);
		b9.setSize(100, 100);
		turn = 0;
	}
	
	//start new game
	public void startNewGame()
	{
		enableAllButtons();
		resetAllButtons();
		clearTaken();
		whosTurn.setText(messageForO);
		turn =0;
	}

	//turn all buttons white and set their names to null
	public void resetAllButtons()
	{
		b1.setIcon(w);
		b2.setIcon(w);
		b3.setIcon(w);
		b4.setIcon(w);
		b5.setIcon(w);
		b6.setIcon(w);
		b7.setIcon(w);
		b8.setIcon(w);
		b9.setIcon(w);
		
		b1.setName(null);
		b2.setName(null);
		b3.setName(null);
		b4.setName(null);
		b5.setName(null);
		b6.setName(null);
		b7.setName(null);
		b8.setName(null);
		b9.setName(null);
		
		
	}
	
	//make all buttons interactive
	public void enableAllButtons()
	{
		b1.setEnabled(true);
		b2.setEnabled(true);
		b3.setEnabled(true);
		b4.setEnabled(true);
		b5.setEnabled(true);
		b6.setEnabled(true);
		b7.setEnabled(true);
		b8.setEnabled(true);
		b9.setEnabled(true);
	}
	
	//assign handler to buttons
	public void setHandler(PlayHandler handler)
	{
		b1.addActionListener(handler);
		b2.addActionListener(handler);
		b3.addActionListener(handler);
		b4.addActionListener(handler);
		b5.addActionListener(handler);
		b6.addActionListener(handler);
		b7.addActionListener(handler);
		b8.addActionListener(handler);
		b9.addActionListener(handler);
	}

}
