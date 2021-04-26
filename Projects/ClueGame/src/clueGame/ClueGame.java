package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	public ClueGame() throws BadConfigFormatException, LineUnavailableException, UnsupportedAudioFileException, IOException {
		Board board = Board.getInstance();  //Instantiates the board drawing 
		board.setConfigFiles("ClueLayoutEJ.csv","ClueSetup.txt"); // Grabs the correct files to read
		board.initialize();	// Loads the files to create board, and get all needed information
		//board.makeSound();
		
		
		GameControlPanel gameControlPanel = new GameControlPanel(); //creates gamecontrol object and cardPanel object
	    CardPanel cardPanel = new CardPanel();
	    
	    board.setGameControlPanel(gameControlPanel);
	    board.setCardPanel(cardPanel);
		
		setTitle("Clue Game");//Sets title and sizing
		setSize(1000,1000);
		
		add(board, BorderLayout.CENTER);	// Adds the board to the center of the frame
		add(cardPanel, BorderLayout.EAST);	// Adds the card control panel to the right side of the frame
		add(gameControlPanel, BorderLayout.SOUTH);	// Adds the game control panel at the bottom of the frame
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits
		JOptionPane.showMessageDialog(this, "You are Quinn XCII. \n Can you find the solution \n before the Computer Players?");
		setVisible(true);	// Makes the frame visible
		
	
		PlayMusic soundPlayer = new PlayMusic();
		soundPlayer.play();
		
		
	}
	
	public static void main(String args[]) throws BadConfigFormatException, LineUnavailableException, UnsupportedAudioFileException, IOException {
		ClueGame clue = new ClueGame();	// Creates the instance of the game, constructors handle all needed calls to make the game work
	}
}