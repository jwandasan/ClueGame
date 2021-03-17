package clueGame;

import java.util.*;

//import com.sun.org.apache.bcel.internal.generic.RETURN;

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
	private String label;
	private String roomName;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter;
	private char secretPassage;
	private boolean isRoom, isOccupied;
	private boolean isDoorway;
	private boolean isCenter;
	private boolean isSecret;
	private Object roomDirection;
	Set<BoardCell> adjList;
	
	
	/*
	 * Constructor
	 */
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		adjList = new HashSet<BoardCell>();
	}
	
	
	/*
	 * 
	 *  All getter methods
	 *  
	 */
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean isRoom() { // changed getRoom to isRoom
		return isRoom;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public boolean isOccupied() {	// changed getOccupied to isOccupied for clarification
		return isOccupied;
	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public Object getDoorDirection() {
		return doorDirection;
	}
	public boolean isRoomCenter() {
		return isCenter;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public boolean isSecret() {
		return isSecret;
	}
	/*
	 * Ends section for all getter methods
	 */
	
	
	
	
	/*
	 * 
	 *  All setter methods
	 *  
	 */
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public void setIsSecret(boolean isSecret) {
		this.isSecret = isSecret;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public void setIsCenter(boolean isCenter) {
		this.isCenter = isCenter;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public void setIsLabel(boolean isLabel) {
		this.roomLabel = isLabel;
	}
	
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	public void setDoorDirection(DoorDirection direction) {
		this.doorDirection = direction;
	}
	
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	

	public void setSecretPassage(char sk) {
		this.secretPassage = sk;
	}
	
	/*
	 * Ends section for all setter methods
	 */
	
	/*
	 *  
	 *  All other functions
	 *  
	 */
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	/*
	 * Ends section for all other functions
	 */
	
}
