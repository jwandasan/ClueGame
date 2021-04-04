package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.*;

public class GameControlPanel extends JPanel{
	private JTextField theGuess;
	private JTextField guessResult;
	private JTextField name;
	private ComputerPlayer currentTurn;
	private int turnNum;
	
	public GameControlPanel() {
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopPanel();
		add(panel, BorderLayout.NORTH);
		panel = createBottomPanel();
		add(panel, BorderLayout.SOUTH);
	}
	
	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		JLabel nameLabel = new JLabel("Whose Turn?");
		panel.add(nameLabel, BorderLayout.NORTH);
		JTextField playerLabel = new JTextField(1);
		panel.add(playerLabel, BorderLayout.CENTER);
		JButton accusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		panel.add(accusation);
		panel.add(next);
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		JLabel guessLabel = new JLabel();
		guessLabel.setBorder(new TitledBorder("Guess"));
		panel.add(guessLabel);
		JLabel resultLabel = new JLabel();
		resultLabel.setBorder(new TitledBorder("Guess Result"));
		panel.add(resultLabel);
		
		return panel;
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
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0, 0), 5);
		//panel.setGuess( "I have no guess!");
		//panel.setGuessResult( "So you have nothing?");
	}
}
