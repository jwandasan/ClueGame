package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSetupTests {
	
	private static Board board;
	private static Set<Card> deck;
	private static Solution testSolution;
	private static Card batCard, knifeCard, handgunCard, swordCard, hammerCard, panCard, atriumCard, readingAreaCard, collectorsRoomCard,
					    officeCard, closetCard, studioCard, bathroomCard, musicRoomCard, greenHouseCard, quinnCard, alexCard, jeremyCard,
					    chelseaCard, ayokayCard, jonCard;
	

	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayoutEJ.csv", "ClueSetup.txt");
		// Initialize will load config files 
		board.initialize();
		board.initializeCards();
		
		deck = Board.getDeck();
		testSolution = Board.getSolution();
		batCard = new Card("Bat", CardType.WEAPON);
		knifeCard = new Card("Knife", CardType.WEAPON);
		handgunCard = new Card("Handgun", CardType.WEAPON);
		swordCard = new Card("Sword", CardType.WEAPON);
		hammerCard = new Card("Hammer", CardType.WEAPON);
		atriumCard = new Card("Atrium", CardType.WEAPON);
		readingAreaCard = new Card("Reading Room", CardType.ROOM);
		collectorsRoomCard = new Card("Collectors Room", CardType.ROOM);
		officeCard = new Card("Reading Room", CardType.ROOM);
		closetCard = new Card("Reading Room", CardType.ROOM);
		studioCard = new Card("Reading Room", CardType.ROOM);
		bathroomCard = new Card("Reading Room", CardType.ROOM);
		musicRoomCard = new Card("Reading Room", CardType.ROOM);
		greenHouseCard = new Card("Reading Room", CardType.ROOM);
		quinnCard = new Card("Quinn XCII", CardType.PERSON);
		alexCard = new Card("Alexander 23", CardType.PERSON);
		jeremyCard = new Card("Jeremy Zucker", CardType.PERSON);
		chelseaCard = new Card("Chelsea Cutler", CardType.PERSON);
		ayokayCard = new Card("Ayokay", CardType.PERSON);
		jonCard = new Card("Jon Bellion", CardType.PERSON);
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
		assertEquals(21, deck.size());
		
		for(Card testCard: deck) {
			if(testCard.getCardName() == "Bat") {
				assertEquals(testCard.getCardType(), CardType.WEAPON); // Tests first weapon
			} else if(testCard.getCardName() == "Pan") {
				assertEquals(testCard.getCardType(), CardType.WEAPON); // Tests last weapon
			} else if(testCard.getCardName() == "Atrium") {
				assertEquals(testCard.getCardType(), CardType.ROOM); // Tests first room
			} else if(testCard.getCardName() == "Green House") {
				assertEquals(testCard.getCardType(), CardType.ROOM); // Tests last room
			} else if(testCard.getCardName() == "Quinn XCII") {
				assertEquals(testCard.getCardType(), CardType.PERSON); // Tests first person
			} else if(testCard.getCardName() == "Jon Bellion") {
				assertEquals(testCard.getCardType(), CardType.PERSON); // tests last person
			}
		}
		
		
		// These tests became invalid after refactoring our code
		//Tests weapons
		/*
		assertTrue(deck.contains(batCard));
		assertTrue(deck.contains(swordCard));
		
		//Tests People
		assertTrue(deck.contains(quinnCard));
		assertTrue(deck.contains(chelseaCard));
		
		//Tests Rooms
		assertTrue(deck.contains(atriumCard));
		assertTrue(deck.contains(greenHouseCard));
		*/
	}

	@Test
	void TestSolution() {
		
		assertTrue(testSolution.getIsDealt() == true);
	}
	
	@Test
	void TestDealtToPlayers() {
		Card solPerson = testSolution.getPerson();
		Card solWeapon = testSolution.getWeapon();
		Card solRoom = testSolution.getRoom();
		
		// This set of tests does not assess what we want because the deck should contain these cards, but the hands should not.
		/*
		assertFalse(deck.contains(solPerson));
		assertFalse(deck.contains(solWeapon));
		assertFalse(deck.contains(solRoom));
		*/
		
		ArrayList<Player> allPlayers = board.getAllPlayers();
		Set<Card> hand = null;
		for(int i = 0; i < allPlayers.size(); i++) {
			hand = allPlayers.get(i).getHand();
			for(Card iter: hand) {
				assertFalse(hand.contains(solPerson));
				assertFalse(hand.contains(solWeapon));
				assertFalse(hand.contains(solRoom));
			}
		}
		
		
	}
}
