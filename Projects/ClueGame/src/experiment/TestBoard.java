package experiment;
import java.util.Collections;
import java.util.Set;

public class TestBoard {
	
	public TestBoard() {
		super();
	}
	public void calcTargets(TestBoardCell startCell, int patlength) {
		
	}
	public Set<TestBoardCell> getTargets(){
		Set<TestBoardCell> testTarget = Collections.emptySet();
		return testTarget;
	}
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell getCell = new TestBoardCell(row, col);
		return getCell;
	}
}