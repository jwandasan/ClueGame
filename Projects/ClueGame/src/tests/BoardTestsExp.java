package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import expirement.TestBoard;
import expirement.TestBoardCell;

class BoardTestsExp {
	TestBoard board;
	@BeforeEach 
	public void setUp(){
		board = new TestBoard();
	}
	@Test
	public void testAdjacency() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcAdj(cell);
		TestBoardCell[][] testGrid = board.getGrid();
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(testGrid[1][0]));
		Assert.assertTrue(testList.contains(testGrid[0][1]));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void testEmpty() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.isEmpty());
	}
	
	@Test
	public void testOneOccupied() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.isEmpty());
	}
	//@Test
	public void testOneRoom() {
		
	}
	
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		TestBoardCell[][] testGrid = board.getGrid();
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(testGrid[3][0]));
		Assert.assertTrue(targets.contains(testGrid[2][1]));
		Assert.assertTrue(targets.contains(testGrid[0][1]));
		Assert.assertTrue(targets.contains(testGrid[1][2]));
		Assert.assertTrue(targets.contains(testGrid[0][3]));
		Assert.assertTrue(targets.contains(testGrid[1][0]));
	}		
		@Test
	public void testTargetsMixed() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(0,2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		TestBoardCell[][] testGrid = board.getGrid();
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(testGrid[1][1]));
		Assert.assertTrue(targets.contains(testGrid[2][2]));
		Assert.assertTrue(targets.contains(testGrid[3][3]));
	}
	
}
