package expirement;
import java.util.Collections;
import java.util.*;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> visited;
	private Set<TestBoardCell> targets;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		super();
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		grid = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		calcAdj(startCell);
		visited.add(startCell);
		Set<TestBoardCell> tempAdj = startCell.getAdjList();
		
		for (TestBoardCell possMove: tempAdj) {
			if (!(visited.contains(possMove))) {
				visited.add(possMove);
				if (pathlength != 1) {
					calcTargets(possMove, pathlength - 1);
				} else if (pathlength == 1){
					targets.add(possMove);
				}
				visited.remove(possMove);
			} 
		}
	}
	
	public void calcAdj(TestBoardCell cell) {
		if (cell.getRow() - 1 >= 0) {
			cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
		} if (cell.getRow() + 1 < ROWS) {
			cell.addAdjacency(grid[cell.getRow() + 1][cell.getCol()]);
		} if (cell.getCol() - 1 >= 0) {
			cell.addAdjacency(grid[cell.getRow()][cell.getCol() - 1]);
		} if (cell.getCol() + 1 < COLS) {
			cell.addAdjacency(grid[cell.getRow()][cell.getCol() + 1]);
		}
	}
	
	public TestBoardCell[][] getGrid() {
		return grid;
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell getCell = new TestBoardCell(row,col);
		grid[row][col] = getCell;
		return getCell;
	}
	
}
