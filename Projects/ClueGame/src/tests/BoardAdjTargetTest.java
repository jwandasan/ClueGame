package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {

	private static Board board;
	
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayoutEJ.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	// Tests locations with only walkways as adjacent locations
	@Test
	public void testAdjacencyWalkways() {
		//Tests next to unused and room
		Set<BoardCell> testList = board.getAdjList(19, 23);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(20, 23)));
		
		// Test near unused and other walkways
		testList = board.getAdjList(27, 18);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(27, 18)));
		assertTrue(testList.contains(board.getCell(28, 19)));
		assertTrue(testList.contains(board.getCell(27, 19)));
	}

	// Tests locations within rooms not center (should have empty adjacency list)
	@Test
	public void testAdjacencyRooms() {
		Set<BoardCell> testList = board.getAdjList(3,2); //Test Reading room
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(5, 2)));
		assertTrue(testList.contains(board.getCell(2, 7)));
		
		// tests atrium room
		testList = board.getAdjList(4, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(9, 19)));
			
		// tests bathroom and secret passage
		testList = board.getAdjList(20, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(21, 3)));
		assertTrue(testList.contains(board.getCell(22, 3)));
		assertTrue(testList.contains(board.getCell(21, 22)));
	}
	
	// Tests locations that are at each edge of the board
	@Test
	public void testAdjacencyEdges() {
		//Tests right edge
		Set<BoardCell> testList = board.getAdjList(4, 23);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(3, 23)));
		assertTrue(testList.contains(board.getCell(5, 23)));
		assertTrue(testList.contains(board.getCell(4, 22)));
		
		//Tests top edge
		testList = board.getAdjList(0, 12);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(0, 11)));
		assertTrue(testList.contains(board.getCell(0, 13)));
		assertTrue(testList.contains(board.getCell(1, 12)));
		
		//Tests left edge
		testList = board.getAdjList(2, 0);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		
		//Tests bottom edge
		testList = board.getAdjList(28, 12);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(28, 11)));
		assertTrue(testList.contains(board.getCell(27, 12)));
		assertTrue(testList.contains(board.getCell(28, 13)));
	}
	
	// Tests locations that are beside a room cell that is not a doorway
	@Test
	public void testAdjacencyNextToRoom() {
		Set<BoardCell> testList = board.getAdjList(27,12); // test not nextToRoom
		assertFalse(testList.contains(board.getCell(24, 8)));
		
		testList = board.getAdjList(7,3);
		assertEquals(3, testList.size()); //test nextToRoom
		assertTrue(testList.contains(board.getCell(8, 3)));
		assertTrue(testList.contains(board.getCell(7, 4)));
	}
	
	// Tests locations that are doorways
	@Test
	public void testAdjacencyDoorway() {
		Set<BoardCell> testList = board.getAdjList(2,7); //tests doorway for Reading Room
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(3, 7)));
		assertTrue(testList.contains(board.getCell(1, 7)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		
		testList = board.getAdjList(15,17); //tests doorway for Office
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 20)));
		assertTrue(testList.contains(board.getCell(15, 16)));
		assertTrue(testList.contains(board.getCell(14, 16)));
		assertTrue(testList.contains(board.getCell(14, 17)));
	}
	
	// Tests locations that are connected by secret passage
	@Test
	public void testAdjacencySecretPassage() {
		Set<BoardCell> testList = board.getAdjList(19,22);
		assertTrue(testList.contains(board.getCell(19, 21)));
		assertTrue(testList.contains(board.getCell(19, 23)));
		assertTrue(testList.contains(board.getCell(25, 1)));
		assertTrue(testList.contains(board.getCell(20, 22)));
	}
	
	// Tests targets along walkways, at various distances
	@Test
	public void testTargetsWalkways() {
		board.calcTargets(board.getCell(25, 5), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(25, 7)));
		assertTrue(targets.contains(board.getCell(25, 8)));	
	}
	
	// Tests targets that allow the user to enter a room
	@Test
	public void testTargetsEnterRoom() {
		//Test one entrance
		board.calcTargets(board.getCell(25, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(24, 7)));
		
		//Test two entrances
		board.calcTargets(board.getCell(21,3), 1);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(21,2)));
		
		
		board.calcTargets(board.getCell(22,3), 1);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(22,2)));
	}
	
	// Tests targets calculated when leaving a room without secret passage
	@Test
	public void testTargetsWithoutSecret() {
		//tests no target
		board.calcTargets(board.getCell(21, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1,targets.size());
		//tests next room without a target
		board.calcTargets(board.getCell(21, 15), 1);
		targets = board.getTargets();
		assertEquals(1,targets.size());
	}
	
	// Tests targets that reflect blocking by other players
	@Test
	public void testTargetsBlocking() {
		//Tests two different blocks
		board.calcTargets(board.getCell(7, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1,targets.size());
		
		board.calcTargets(board.getCell(1, 16), 1);
		targets = board.getTargets();
		assertEquals(1,targets.size());
	}
}
