package expirement;
import java.util.*;

/**TestBoardCell Class
 * 
 * 
 * @author Jeric Wandasan
 * @author Ethan Vijayabaskaran
 */

public class TestBoardCell {
	private int row, col;
	private boolean isRoom, isOccupied;
	Set<TestBoardCell> adjList;
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		adjList = new HashSet<TestBoardCell>();
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean getRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
}
