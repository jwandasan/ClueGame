package tests;

import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * FileInitTest JUnit Test
 * 
 * @author Jeric Wandasan
 * @author Ethan Vijayabaskaran
 * 
 * Handles testing our code, and checking everything should be where it is. Currently all tests fail with no errors.
 * 
 */

class FileInitTest {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 29;
	public static final int NUM_COLUMNS = 24;
	
	private static Board board;
	
	@BeforeAll
	public static void setUP() throws FileNotFoundException, BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueLayoutEJ.csv","ClueSetup.txt");
		board.initialize();
	}
	
	
	// Test used to ensure that files open, and that it is reading properly
	// This test was deemed unnecessary due to being achieved in the @BeforeAll statement
	//@Test
	//public void testLayoutAndSetup() throws FileNotFoundException, BadConfigFormatException {
	
	//}
	
	// Test to make sure that rooms have the correct labels associated with their name.
	@Test
	public void testRoomLabels() {
		assertEquals("Atrium",board.getRoom('A').getName());
		assertEquals("Reading Area",board.getRoom('R').getName());
		assertEquals("Collectors Room",board.getRoom('C').getName());
		assertEquals("Office",board.getRoom('O').getName());
		assertEquals("Closet",board.getRoom('T').getName());
		assertEquals("Studio",board.getRoom('S').getName());
		assertEquals("Studio",board.getRoom('S').getName());
	}
	
	// Test to make sure that each cell has the right initial that goes with its room name.
	@Test
	public void testCorrectInitial() {
		BoardCell cell = board.getCell(0,12);
		assertEquals('A', cell.getInitial());
		cell = board.getCell(1,2);
		assertEquals('R', cell.getInitial());
		cell = board.getCell(5,22);
		assertEquals('C', cell.getInitial());
		cell = board.getCell(15,20);
		assertEquals('O', cell.getInitial());
	}
	
	// Test to make sure that the board is the right length and width.
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	// Test to make sure that there are the correct amount of doors.
	@Test
	public void testNumberOfDoorWays() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(21, numDoors);
	}

	// Test to make sure that there is one of each door direction.
	@Test
	public void testFourDoorDirections() {
		BoardCell cell = board.getCell(11, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(9, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(20, 19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(18, 15);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board.getCell(7, 18);
		assertFalse(cell.isDoorway());
	}
	
	// Test to make sure that a certain cell is the center of a room.
	@Test
	public void testCenterAndLabel() {
		BoardCell cell = board.getCell(4,21);
		assertTrue(cell.isRoomCenter());
		cell = board.getCell(21, 21);
		assertTrue(cell.isRoomCenter());
	}
	
}
