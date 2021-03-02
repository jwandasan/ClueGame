package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import expirement.TestBoardCell;

/**
 * Board Class
 * 
 * @author Jeric Wandasan
 * @author Ethan Vijayabaskaran
 * 
 * Handles the creation of the board, rooms, opening/reading the needed files.
 * 
 */

public class Board {
	private static Board theInstance = new Board();
	private BoardCell[][] grid;
	private int numRows, numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private String name = "Holder";
	private Room room = new Room(name);
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	public void loadConfigFiles() throws BadConfigFormatException, FileNotFoundException {
	
	}
	
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		if (setupConfigFile != null) {
			throw new BadConfigFormatException();
		}
	}
	
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		if (layoutConfigFile != null) {
			throw new BadConfigFormatException();
		}
	}
	public void setConfigFiles(String csv, String txt) {
		
	}
	
	public BoardCell getCell(int row, int col) {
		BoardCell cell = new BoardCell(row, col);
		return cell;
	}

	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return room;
	}
	public Room getRoom(BoardCell cell) {
		return room;
	}
}
