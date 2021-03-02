package clueGame;

import java.util.*;

/**
 * BoardCell Class
 * 
 * @author Jeric Wandasan
 * @author Ethan Vijayabaskaran
 * 
 * Handles the creation of individual cells, and tracking certain cells within a board instance.
 * 
 */

import expirement.TestBoardCell;

public class BoardCell {
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter;
	private char secretPassage;
	private boolean isRoom, isOccupied;
	private boolean isDoorway;
	Set<BoardCell> adjList;
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		adjList = new HashSet<BoardCell>();
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean getRoom() {
		return isRoom;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public Object getDoorDirection() {
		return doorDirection;
	}
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	
	
}
