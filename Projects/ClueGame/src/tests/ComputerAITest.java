package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class ComputerAITest {
	
	private static Board board;
	private static Set<Card> deck;
	private static Solution testSolution;
	private static Card batCard, knifeCard, handgunCard, swordCard, hammerCard, panCard, atriumCard, readingAreaCard, collectorsRoomCard,
					    officeCard, closetCard, studioCard, bathroomCard, musicRoomCard, greenHouseCard, quinnCard, alexCard, jeremyCard,
					    chelseaCard, ayokayCard, jonCard;
	@BeforeAll
	public static void setUp () throws FileNotFoundException, BadConfigFormatException {
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
	void selectTargets() {
		// If no rooms in list, select randomly
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", Color.gray, 6, 7);
		BoardCell startCell = board.getCell(6, 7);
		board.calcTargetExecute(board.getCell(9, 8), 1);
		Set<BoardCell> setOfTargets = board.getTargets();
		assertTrue(testPlayer.selectTargets(setOfTargets) != null);
		
		// If room in list that has not been seen, select it
		setOfTargets.clear();
		ComputerPlayer testPlayerTwo = new ComputerPlayer("testPlayerTwo", Color.gray, 4, 7);
		board.calcTargetExecute(board.getCell(4, 7), 3);
		setOfTargets = board.getTargets();
		testPlayerTwo.updateSeen(readingAreaCard);
		BoardCell testCell = testPlayerTwo.selectTargets(setOfTargets);
		assertTrue(testCell.getRoomName() != "Reading Area");
		// If room in list that has been seen, each target (including room) selected randomly
		setOfTargets.clear();
		ComputerPlayer testPlayerThree = new ComputerPlayer("testPlayerThree", Color.gray, 6, 3);
		board.calcTargetExecute(board.getCell(6, 3), 3);
		setOfTargets = board.getTargets();
		testPlayerThree.updateSeen(readingAreaCard);
		testCell = testPlayerThree.selectTargets(setOfTargets);
		assertTrue(testCell != null);
	}
	
	@Test
	void createSuggestion() {
		// Room matches current location
		ComputerPlayer testPlayerOne = new ComputerPlayer("testPlayer", Color.gray, 22, 1);
		testPlayerOne.updateHand(bathroomCard);
		Solution testSolution = testPlayerOne.chooseSolution(bathroomCard,null,null);
		assertEquals(testSolution.getRoom().getCardName(), "Bathroom");
		// If only one weapon not seen, it's selected
		testPlayerOne.updateSeen(batCard);
		testPlayerOne.updateSeen(knifeCard);
		testPlayerOne.updateSeen(handgunCard);
		testPlayerOne.updateSeen(swordCard);
		testPlayerOne.updateSeen(hammerCard);
		testPlayerOne.updateSeen(chelseaCard);
		testPlayerOne.updateHand(panCard);
		testPlayerOne.updateHand(alexCard);
		testPlayerOne.createSuggestion();
		testPlayerOne.chooseSolution(null, null, panCard);
		assertEquals(testSolution.getWeapon(), panCard);
		// If multiple weapons not seen, one of them is randomly selected
		ComputerPlayer testPlayerTwo = new ComputerPlayer("testPlayerTwo", Color.gray, 22, 1);
		testPlayerTwo.updateSeen(batCard);
		testPlayerTwo.updateSeen(knifeCard);
		testPlayerTwo.updateSeen(handgunCard);
		testPlayerTwo.updateHand(hammerCard);
		testPlayerTwo.updateHand(alexCard);
		testPlayerTwo.updateHand(readingAreaCard);
		testPlayerTwo.createSuggestion();
		assertTrue(testPlayerTwo.getSolution().getWeapon() != batCard);
		assertTrue(testPlayerTwo.getSolution().getWeapon() != knifeCard);
		assertTrue(testPlayerTwo.getSolution().getWeapon() != handgunCard);
		// if multiple persons not seen, one of them is randomly selected
		testPlayerTwo.updateSeen(quinnCard);
		testPlayerTwo.updateSeen(jonCard);
		testPlayerTwo.updateSeen(chelseaCard);
		assertTrue(testPlayerTwo.getSolution().getPerson() != quinnCard);
		assertTrue(testPlayerTwo.getSolution().getPerson() != jonCard);
		assertTrue(testPlayerTwo.getSolution().getPerson() != chelseaCard);
	}
}
