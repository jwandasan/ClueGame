package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
import java.awt.font.*;

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

public class BoardCell {
	private int row, col;
	private char initial;
	private String label;
	private String roomName;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private char secretPassage;
	private boolean isRoom, isOccupied;
	private boolean isDoorway;
	private boolean isCenter;
	private boolean isSecret;
	private boolean isWalkway;
	private boolean isEmpty;
	private static final int OFFSET = 2;
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
	
	public boolean isWalkway() {
		return isWalkway;
	}
	public boolean isEmpty() {
		return isEmpty;
	}
	/*
	 * Ends section for all getter methods
	 */
	
	
	
	
	/*
	 * 
	 *  All setter methods
	 *  
	 *  
	 */
	public void setIsWalkway(boolean isWalkway) {
		this.isWalkway = isWalkway;
	}
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
	
	public void setIsEmpty(boolean value) {
		this.isEmpty = true;
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
	
	public void drawCell(Graphics g, int x, int y) {
		Color color = Color.green;
		if (isDoorway()) {
			color = Color.blue;
		} else if (isWalkway()) {
			color = Color.green;
		}
		if(isWalkway()) {
			g.setColor(Color.black);
			g.drawRect(col * x, row * y, x, y);	// Creates the initial rectangle
			g.setColor(Color.lightGray);
			g.fillRect((col * x + OFFSET), (row * y + OFFSET), x - OFFSET, y - OFFSET);	// Fills rectangle and OFFSET is needed to ensure that the outer black borders are drawn properly
			if(getDoorDirection() == DoorDirection.DOWN){ //Determines how to set which side the door should be on (UP, DOWN, RIGHT, LEFT)
				g.setColor(color);
				g.fillRect(col * x , row * y + 28, x, y - 27);
			} else if(getDoorDirection() == DoorDirection.UP) {
				g.setColor(color);
				g.fillRect(col * x , row * y, x, y - 27);
			} else if(getDoorDirection() == DoorDirection.LEFT) {
				g.setColor(color);
				g.fillRect(col * x, row * y, x - 35, y);
			} else if(getDoorDirection() == DoorDirection.RIGHT) {
				g.setColor(color);
				g.fillRect(col * x + 37, row * y, x -35, y);
			}
		} else if(isEmpty()) {
			g.setColor(Color.black);	// If the cell is "Unused" it will be blacked out.
			g.fillRect(col * x, row * y, x, y);
		}
	}
	
	public void drawRoom(Graphics g, int x, int y) {	// This function will print the room name where the label is supposed to be
		Font font = new Font("Sans Seriff Plain", Font.PLAIN, 15);
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(roomName, col * x, row * y);
	}
	
	/*
	 * Ends section for all other functions
	 */
	
}
