package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Room Class
 * 
 * @author Jeric Wandasan
 * @author Ethan Vijayabaskaran
 * 
 * Handles the creation of a room class to store all of the needed variables for a room including the name, center, and label
 */

public class Room {
	private String name = null;
	private char initial;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name) {
		super();
		this.name = name;
	}
	public Room() {
		super();
	}
	
	public String getRoom(BoardCell cell) {
		return cell.getRoomName();
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
