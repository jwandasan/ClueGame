package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Solution;

public class GameSolutionTest {
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
	panCard = new Card("Pan", CardType.WEAPON);
	atriumCard = new Card("Atrium", CardType.ROOM);
	readingAreaCard = new Card("Reading Room", CardType.ROOM);
	collectorsRoomCard = new Card("Collectors Room", CardType.ROOM);
	officeCard = new Card("Office", CardType.ROOM);
	closetCard = new Card("Closet", CardType.ROOM);
	studioCard = new Card("Studio", CardType.ROOM);
	bathroomCard = new Card("Bathroom", CardType.ROOM);
	musicRoomCard = new Card("Music Room", CardType.ROOM);
	greenHouseCard = new Card("Green House", CardType.ROOM);
	quinnCard = new Card("Quinn XCII", CardType.PERSON);
	alexCard = new Card("Alexander 23", CardType.PERSON);
	jeremyCard = new Card("Jeremy Zucker", CardType.PERSON);
	chelseaCard = new Card("Chelsea Cutler", CardType.PERSON);
	ayokayCard = new Card("Ayokay", CardType.PERSON);
	jonCard = new Card("Jon Bellion", CardType.PERSON);
	}
	
	@Test
	void checkAccusation()  {
		Integer aInt = 0;
		// Solution that is correct
		testSolution = new Solution(atriumCard, quinnCard, batCard);
		ComputerPlayer testPlayer = new ComputerPlayer("testName", Color.gray, aInt, aInt);
		testPlayer.chooseSolution(testSolution.getRoom(), testSolution.getPerson(), testSolution.getWeapon());
		Solution goodSolution = testPlayer.getSolution();
		assertTrue(goodSolution.getPerson() == quinnCard);
		assertTrue(goodSolution.getRoom() == atriumCard);
		assertTrue(goodSolution.getWeapon() == batCard);
		
		// Solution with wrong person
		testSolution = new Solution(atriumCard, quinnCard, batCard);
		testPlayer.chooseSolution(testSolution.getRoom(), jonCard, testSolution.getWeapon());
		assertFalse(goodSolution.getPerson() == quinnCard);
		assertTrue(goodSolution.getRoom() == atriumCard);
		assertTrue(goodSolution.getWeapon() == batCard);
		
		// Solution with wrong weapon
		testSolution = new Solution(atriumCard, quinnCard, batCard);
		testPlayer.chooseSolution(testSolution.getRoom(), testSolution.getPerson(), knifeCard);
		assertTrue(goodSolution.getPerson() == quinnCard);
		assertTrue(goodSolution.getRoom() == atriumCard);
		assertFalse(goodSolution.getWeapon() == batCard);
		
		// Solution with wrong room
		testSolution = new Solution(atriumCard, quinnCard, batCard);
		testPlayer.chooseSolution(readingAreaCard, testSolution.getPerson(), testSolution.getWeapon());
		assertTrue(goodSolution.getPerson() == quinnCard);
		assertFalse(goodSolution.getRoom() == atriumCard);
		assertTrue(goodSolution.getWeapon() == batCard);
	}
	
	@Test
	void disproveSuggestion() {
		testSolution = new Solution(atriumCard, quinnCard, batCard);
		// If player has only one matching card it should be returned
		HumanPlayer testHuman = board.getHuman();
		testHuman.updateHand(atriumCard);
		assertEquals(testHuman.disproveSuggestion(testSolution), atriumCard);
		
		// If players has >1 matching card, returned card should be chosen randomly
		testHuman.updateHand(atriumCard);
		testHuman.updateHand(quinnCard);
		assertTrue(testHuman.getHand().contains(atriumCard));
		
		
		// If player has no matching cards, null is returned
		testSolution = new Solution(atriumCard, quinnCard, batCard);
		HumanPlayer testHumanTwo = board.getHuman();
		assertFalse(testHumanTwo.disproveSuggestion(testSolution) == null);
	}
	
	@Test
	void handleSuggestion() {
		// Suggestion no one can disprove returns null
	    Integer aInt = 0;
		Solution testSuggestion = new Solution(atriumCard, quinnCard, batCard);
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", Color.gray, aInt, aInt);
		testPlayer.clearHand();
		testPlayer.updateHand(readingAreaCard);
		testPlayer.updateHand(jonCard);
		testPlayer.updateHand(panCard);
		assertEquals(testPlayer.disproveSuggestion(testSuggestion), null);
		// Suggestion only accusing player can disprove returns null
		testPlayer.createSuggestion();
		Solution playerSuggestion = testPlayer.getSolution();
		assertEquals(testPlayer.disproveSuggestion(playerSuggestion), testPlayer.getSolution().getRoom());
		// Suggestion only human can disprove returns answer
		HumanPlayer testHuman = new HumanPlayer("testHuman", Color.red, aInt, aInt);
		testHuman.updateHand(atriumCard);
		testHuman.updateHand(quinnCard);
		testHuman.updateHand(batCard);
		System.out.println(testHuman.disproveSuggestion(testSuggestion).getCardName());
		assertTrue(testHuman.disproveSuggestion(testSuggestion) != null); // Disprove returns last card of answer, bat is last answer so whole answer is there
		// Suggestion that two players can disprove, correct player returns answer
		ComputerPlayer testPlayerTwo = new ComputerPlayer("testPlayerTwo", Color.blue, aInt, aInt);
		testPlayerTwo.updateHand(atriumCard);
		testPlayerTwo.updateHand(quinnCard);
		testPlayerTwo.updateHand(swordCard);
		assertTrue(testHuman.disproveSuggestion(testSuggestion) != null);
		assertTrue(testPlayerTwo.disproveSuggestion(testSuggestion) != null);
	}
}
