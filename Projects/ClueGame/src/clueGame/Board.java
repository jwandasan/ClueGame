package clueGame;

import java.io.*;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.*;

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
	//Initialization of all variables
	private static Board theInstance = new Board();
	private BoardCell[][] grid;
	private int numRows , numColumns;
	private String layoutConfigFile; // Filename
	private String setupConfigFile; // Filename
	private String name; 
	private Room room = new Room(name);
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<String> layoutData;
	private Set<String> setupData;
	private Map<Character, String> charToName = new HashMap<Character, String>();
	private Map<Character, Room> roomMap = new HashMap<Character,Room>();
	
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {//returns the instance of a single board
		return theInstance;
	}
	
	public void initialize() throws FileNotFoundException, BadConfigFormatException{//Loads file
		loadConfigFiles();
	}
	
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {//loads the setup and layout configurations
		loadSetupConfig();
		loadLayoutConfig();
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
	
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		Map<String,Character> setupMap = new HashMap<String,Character>();
		charToName.clear();
		// Load the initial and name for each room
		try {
			File fin = new File(setupConfigFile);
			Scanner in = new Scanner(fin);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] split = line.split(", ");
				// Splits the line based off of ", " in order to correctly populate our map of rooms and columns
				if(split[0].charAt(0) != '/') {
					if (split[0].contentEquals("Room") || split[0].contentEquals("Space")) {
						charToName.put(split[2].charAt(0), split[1]);
					} else {
						throw new BadConfigFormatException();
					}
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error: Setup config file not found");
		}
		
	}

	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException{
		roomMap.clear();
		// Setup the number of rows and columns for the board
		try {
			theInstance.numRows = 0;
			File fin = new File(layoutConfigFile);
			Scanner in = new Scanner(fin);
			String line = "";
			while(in.hasNextLine()) {
				line = in.nextLine();
				theInstance.numRows++;
				String[] lineSplit = line.split(",");
				theInstance.numColumns = lineSplit.length;
			}
			in.close();
		} catch(FileNotFoundException e) {
			System.out.println("Error: Layout file not found.");
		}
		
		// Set the room initial, name, and if it is a room, doorway, or other type of space on board
		try {
			File finTwo = new File(layoutConfigFile); //Scans in
			Scanner inTwo = new Scanner(finTwo);
			String[] labels = new String[theInstance.numColumns];
			grid = new BoardCell[numRows][numColumns];
			
			for(int i = 0; i < theInstance.numRows; i++) { 
				//if(!(inTwo.hasNextLine())) {
					//throw new BadConfigFormatException();
				//}
				labels = inTwo.nextLine().split(",");
				for(int j = 0; j < theInstance.numColumns; j++) {
					grid[i][j] = new BoardCell(i,j);
					BoardCell cell = grid[i][j];
					if(j >= labels.length) {
						throw new BadConfigFormatException();
					}
					char initial = labels[j].charAt(0);
					if(!(charToName.containsKey(initial))) {
						throw new BadConfigFormatException();
					}
					String roomName = charToName.get(initial);
					if(!(roomMap.containsKey(initial))) {
						roomMap.put(initial, new Room(roomName));
					}
					Room room = roomMap.get(initial);
					room.setInitial(initial);
					cell.setInitial(initial);
					cell.setRoomName(roomName);
					
					if(labels[j].length() > 1) {
						if(labels[j].charAt(1) == '<') {
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.LEFT);
							cell.setIsDoorway(true);
						} else if(labels[j].charAt(1) == '>') {
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.RIGHT);
							cell.setIsDoorway(true);
						} else if(labels[j].charAt(1) == '^') {
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.UP);
							cell.setIsDoorway(true);
						} else if(labels[j].charAt(1) == 'v') {
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.DOWN);
							cell.setIsDoorway(true);
						} else if(labels[j].charAt(1) == '#') {
							cell.setLabel(labels[j]);
							cell.setIsLabel(true);
						} else if(labels[j].charAt(1) == '*') {
							cell.setLabel(labels[j]);
							cell.setIsCenter(true);
							room.setCenterCell(cell);
						} else if(Character.isLetter(labels[j].charAt(1))) {
							cell.setSecretPassage(labels[j].charAt(1));
						}
					} else if(labels[j].length() == 1) {
						cell.setLabel(roomName);
					} 
				}
			}
			inTwo.close();
		}catch(FileNotFoundException e){
			System.out.println("Error: Layout file not found.");
		}
	}

	public void setConfigFiles(String csv, String txt) throws FileNotFoundException, BadConfigFormatException {
		layoutConfigFile = "data/"+ csv;
		setupConfigFile = "data/"+ txt;

	}
	
	
	public BoardCell getCell(int row, int col) {
		BoardCell cell = grid[row][col];
		return cell;
	}

	public Room getRoom(char c) {
		Room returnVal = null;
		for(Map.Entry<Character, Room> aRoom: roomMap.entrySet()) {
			if(aRoom.getKey() == c) {
				returnVal =  aRoom.getValue();
			}
		}
		return returnVal;
	}
	public Room getRoom(BoardCell cell) { 
		Room returnVal = null;
		for(Map.Entry<Character, Room> aRoom: roomMap.entrySet()) {
			if(aRoom.getKey() == cell.getInitial()) {
				returnVal =  aRoom.getValue();
			}
		}
		return returnVal;
	}
	
}
