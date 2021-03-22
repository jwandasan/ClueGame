package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.HumanPlayer;

class GameSetupTests {
	
	private static Board board;
	private static Map<String,Card> deck;
	

	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayoutEJ.csv", "ClueSetup.txt");
		// Initialize will load config files 
		board.initialize();
		
		deck = Board.getDeck();
	}
	
	@Test
	void TestLoadedPeople() {
		HumanPlayer testHuman = board.getHuman();
		assertEquals("Quinn XCII", testHuman.getPlayerName());
		
	}

}
