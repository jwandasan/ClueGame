package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.*;

public class GameControlPanel extends JPanel{
	private JTextField theGuess = new JTextField(5);
	private JTextField guessResult = new JTextField(5);
	private JTextField currPlayer = new JTextField(5);
	private JTextField rollText = new JTextField(5);
	private ComputerPlayer currentTurn;
	private int turnNum; 
	
	public GameControlPanel() {
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopPanel();
		add(panel, BorderLayout.NORTH);	// Adds top of total panel with Whose Turn it is, roll, and the required buttons
		panel = createBottomPanel();	
		add(panel, BorderLayout.SOUTH); // Adds bottom half of total panel with guess and guessResult
	}
	
	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		
		// Creates a Panel for whose turn it is
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,0));
		JLabel turnLabel = new JLabel("Whose Turn?");
		turnPanel.add(turnLabel, BorderLayout.NORTH);
		turnPanel.add(currPlayer, BorderLayout.SOUTH);
		panel.add(turnPanel);
		
		// Creates a Panel for the current roll
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(0,2));
		JLabel rollLabel = new JLabel("Roll:");
		rollText.setSize(new Dimension(1,1));
		rollText.setBorder(new LineBorder(Color.blue));
		rollPanel.add(rollLabel, BorderLayout.NORTH);
		rollPanel.add(rollText, BorderLayout.NORTH);
		panel.add(rollPanel);
		
		// Adds 2 buttons needed for player interaction
		JButton accusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		panel.add(accusation);
		panel.add(next);
		return panel;
	}
	
	private JPanel createBottomPanel() {
		// Creates a new panel for the bottom portion
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		// Creates a new guess panel and populates it accordingly
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessPanel.setBorder(new TitledBorder("Guess"));
		guessPanel.add(theGuess);;
		panel.add(guessPanel);
		
		// Creates a new guess result panel and populates it accordingly
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,0));
		resultPanel.setBorder(new TitledBorder("Guess Result"));
		resultPanel.add(guessResult);
		panel.add(resultPanel);
		
		return panel;
	}
	
	public void setTurn(ComputerPlayer aPlayer, int turnNum) {
		currPlayer.setEditable(false);
		rollText.setEditable(false);
		String playerName = aPlayer.getPlayerName();
		Color color = aPlayer.getPlayerColor();
		currPlayer.setBackground(color);
		currPlayer.setText(playerName);
		rollText.setText(String.valueOf(turnNum));
	}
	
	public void setGuess(String guess) {
		theGuess.setEditable(false);
		theGuess.setText(guess); // Updates JTextField theGuess with the right information passed in.
	}
	
	public void setGuessResult(String guessResult) {
		this.guessResult.setEditable(false);
		this.guessResult.setText(guessResult); // Updates JTextField guessResult with the right information passed in.
	}
	
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setTitle("Clue Game");
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}
