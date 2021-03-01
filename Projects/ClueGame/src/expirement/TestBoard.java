package expirement;
import java.util.Collections;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> visited;
	private Set<TestBoardCell> targets;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		super();
		grid = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int patlength) {
		
	}
	
	public void calcAdj(TestBoardCell cell) {
		if (cell.getRow() - 1 >= 0) {
			cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
		} else if (cell.getRow() + 1 < ROWS) {
			cell.addAdjacency(grid[cell.getRow() + 1][cell.getCol()]);
		} if (cell.getCol() - 1 >= 0) {
			cell.addAdjacency(grid[cell.getRow()][cell.getCol() - 1]);
		} else if (cell.getCol() + 1 < COLS) {
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
