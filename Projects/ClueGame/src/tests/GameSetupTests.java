package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
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
		Map<String,ComputerPlayer> testComp = board.getComputerList();
		Set <String> keys = testComp.keySet();
		String test1 = "";
		String test2 = "";
		String test3 = "";
		for(String val: keys) {
			if(val.equals("Alexander 23")) {
				test1 = val;
			}else if(val.equals("Ayokay")) {
				test2 = val;
			}else if(val.equals("Jon Bellion")) {
				test3 = val;
			}else {
				continue;
			}
		}
		assertEquals("Alexander 23",test1);
		assertEquals("Ayokay", test2);
		assertEquals("Jon Bellion", test3);
	}
	
	@Test
	void TestProperData() {
		HumanPlayer testHuman = board.getHuman();
		Map<String,ComputerPlayer> testComp = board.getComputerList();
		
		//tests human
		assertEquals(Color.red, testHuman.getPlayerColor());
		assertEquals(7,testHuman.getPlayerColumn());
		assertEquals(0,testHuman.getPlayerRow());
		
		//tests computer
		ComputerPlayer comp1 = null;
		ComputerPlayer comp2 = null;
		ComputerPlayer comp3 = null;
		for(Map.Entry<String, ComputerPlayer> list: testComp.entrySet()) {
			if(list.getKey().equals( "Alexander 23")) {
				comp1 = list.getValue();
			}else if(list.getKey().equals("Ayokay")) {
				comp2 = list.getValue();
			}else if(list.getKey().equals("Jon Bellion")) {
				comp3 = list.getValue();
			}
		}
		System.out.println(testComp.get("Alexander 23"));
			//tests first computer
		assertEquals(Color.black,comp1.getPlayerColor());
		assertEquals(0, comp1.getPlayerColumn());
		assertEquals(18, comp1.getPlayerRow());
			//test second computer
		assertEquals(Color.cyan,comp2.getPlayerColor());
		assertEquals(14, comp2.getPlayerColumn());
		assertEquals(24, comp2.getPlayerRow());
			//test third computer
		assertEquals(Color.yellow,comp3.getPlayerColor());
		assertEquals(23, comp3.getPlayerColumn());
		assertEquals(17, comp3.getPlayerRow());
	}
	
	@Test
	void TestDeckCreated() {
		//Tests weapons
		assertTrue(deck.containsKey("Bat"));
		assertTrue(deck.containsKey("Sword"));
		
		//Tests People
		assertTrue(deck.containsKey("Quinn XCII"));
		assertTrue(deck.containsKey("Chelsea Cutler"));
		
		//Tests Rooms
		assertTrue(deck.containsKey("Atrium"));
		assertTrue(deck.containsKey("Green House"));
	}

}
