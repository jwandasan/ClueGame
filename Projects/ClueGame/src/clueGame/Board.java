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
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<String> layoutData;
	private Set<String> setupData;
	private Map<Character, String> charToName = new HashMap<Character, String>();
	private Map<Character, Room> roomMap = new HashMap<Character,Room>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	
	private Board() {
		super();
	}
	
	// All getters
	public static Board getInstance() {//returns the instance of a single board
		return theInstance;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	

	public int getNumRows() {
		return numRows;
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
	
	public Set<BoardCell> getAdjList(int i, int j) {
		BoardCell cell = grid[i][j];
		calcAdjacency(cell);
		
		return cell.getAdjList();
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}

	// All setters
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	
	public void setConfigFiles(String csv, String txt) throws FileNotFoundException, BadConfigFormatException {
		layoutConfigFile = "data/"+ csv;
		setupConfigFile = "data/"+ txt;

	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
	
	// All other functions needed
	
	public void initialize() throws FileNotFoundException, BadConfigFormatException{//Loads file
		loadConfigFiles();
	}
	
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {//loads the setup and layout configurations
		loadSetupConfig();
		loadLayoutConfig();
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
					
					// If there is a special identifying character, it applies the right assignment depending on the character
					// Including doorways, walkways, and center/labels
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
							cell.setIsSecret(true);
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
	
	public void getCalcTargets(BoardCell startCell, int pathlength) {
		calcAdjacency(startCell);
		visited.add(startCell);
		Set<BoardCell> tempAdj = startCell.getAdjList();
		for (BoardCell possMove: tempAdj) {
			if (!(visited.contains(possMove))) {
				visited.add(possMove);
				 if(possMove.getOccupied() && !possMove.isRoomCenter()) {
						continue;
				} else if (pathlength == 1 || possMove.isRoomCenter()) {
					targets.add(possMove);
				} else if (pathlength != 1) {
					getCalcTargets(possMove, pathlength - 1);
				}
				visited.remove(possMove);
			} else if ((visited.contains(possMove))){
				continue;
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathlength) {	// Sets visited and targets to new to make sure we get a "fresh" list for every target calculation
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		getCalcTargets(startCell, pathlength);
	}
	
	public void calcAdjacency(BoardCell cell) {
		if(cell.getInitial() == 'W' && !cell.isDoorway()){	// Conditional to populate walkway adjacency with cells that are also walkways
			if(cell.getRow() >= 1 && grid[cell.getRow() - 1][cell.getCol()].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
			} if(cell.getRow() < numRows - 1 && grid[cell.getRow() + 1][cell.getCol()].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow() + 1][cell.getCol()]);
			} if(cell.getCol() >= 1 && grid[cell.getRow()][cell.getCol() - 1].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow()][cell.getCol() - 1]);
			} if(cell.getCol() < numColumns - 1 && grid[cell.getRow()][cell.getCol() + 1].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow()][cell.getCol() + 1]);
			}
		} else if(cell.isDoorway()) {	// Conditional to populate walkways with doors adjacency with room center cell
			BoardCell compCell = null;
			if(cell.getDoorDirection() == DoorDirection.UP) {
				compCell = grid[cell.getRow() - 1][cell.getCol()];
			} else if(cell.getDoorDirection() == DoorDirection.DOWN) {
				compCell = grid[cell.getRow() + 1][cell.getCol()];
			} else if(cell.getDoorDirection() == DoorDirection.RIGHT) {
				compCell = grid[cell.getRow()][cell.getCol() + 1];
			} else if(cell.getDoorDirection() == DoorDirection.LEFT) {
				compCell = grid[cell.getRow()][cell.getCol() - 1];
			} 	
			
			for(int i = 0; i < numRows; i++) {
				for(int j = 0; j < numColumns; j++) {
					if((compCell.getInitial() == grid[i][j].getInitial()) && grid[i][j].isRoomCenter()) {
						cell.addAdjacency(grid[i][j]);
					}
				}
			}
			// Taken from the regular walkway test because if a cell is a doorway, it is also a walkway
			if(cell.getRow() >= 0 && grid[cell.getRow() - 1][cell.getCol()].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
			} if(cell.getRow() < numRows - 1 && grid[cell.getRow() + 1][cell.getCol()].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow() + 1][cell.getCol()]);
			} if(cell.getCol() >= 0 && grid[cell.getRow()][cell.getCol() - 1].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow()][cell.getCol() - 1]);
			} if(cell.getCol() < numColumns - 1 && grid[cell.getRow()][cell.getCol() + 1].getInitial() == 'W') {
				cell.addAdjacency(grid[cell.getRow()][cell.getCol() + 1]);
			}
		} else if(cell.isRoomCenter()) {	// Conditional to populate adjacency for a center to a room and it's doorways
			BoardCell compCell = null;
			
			// Has conditionals to check door directions, and grabs the "entrance" cell to stay in the right room when iterating through the board.
			for(int i = 1; i < numRows - 1; i++) {
				for(int j = 1; j < numColumns - 1; j++) {
					if(grid[i][j].getInitial() == cell.getInitial() && grid[i + 1][j].isDoorway()) {
						if(grid[i + 1][j].getDoorDirection() == DoorDirection.UP) {
							compCell = grid[i][j];
						} else if (grid[i + 1][j].getDoorDirection() == DoorDirection.DOWN) {
							compCell = grid[i + 2][j];
						} else if (grid[i + 1][j].getDoorDirection() == DoorDirection.LEFT) {
							compCell = grid[i + 1][j - 1];
						} else if (grid[i + 1][j].getDoorDirection() == DoorDirection.RIGHT) {
							compCell = grid[i + 1][j + 1];
						}
						if(compCell.getInitial() == cell.getInitial()) {
							cell.addAdjacency(grid[i + 1][j]);
						}
					} else if(grid[i][j].getInitial() == cell.getInitial() && grid[i - 1][j].isDoorway()) {
						if(grid[i - 1][j].getDoorDirection() == DoorDirection.UP) {
							compCell = grid[i - 2][j];
						} else if (grid[i - 1][j].getDoorDirection() == DoorDirection.DOWN) {
							compCell = grid[i][j];
						} else if (grid[i - 1][j].getDoorDirection() == DoorDirection.LEFT) {
							compCell = grid[i - 1][j - 1];
						} else if (grid[i - 1][j].getDoorDirection() == DoorDirection.RIGHT) {
							compCell = grid[i - 1][j + 1];
						}
						if(compCell.getInitial() == cell.getInitial()) {
							cell.addAdjacency(grid[i - 1][j]);
						}
					} else if(grid[i][j].getInitial() == cell.getInitial() && grid[i][j + 1].isDoorway()) {
						if(grid[i][j + 1].getDoorDirection() == DoorDirection.UP) {
							compCell = grid[i - 1][j + 1];
						} else if (grid[i][j + 1].getDoorDirection() == DoorDirection.DOWN) {
							compCell = grid[i + 1][j + 1];
						} else if (grid[i][j + 1].getDoorDirection() == DoorDirection.LEFT) {
							compCell = grid[i][j];
						} else if (grid[i][j + 1].getDoorDirection() == DoorDirection.RIGHT) {
							compCell = grid[i][j + 2];
						}
						if(compCell.getInitial() == cell.getInitial()) {
							cell.addAdjacency(grid[i][j + 1]);
						}
					} else if(grid[i][j].getInitial() == cell.getInitial() && grid[i][j - 1].isDoorway()) {
						if(grid[i][j - 1].getDoorDirection() == DoorDirection.UP) {
							compCell = grid[i - 1][j - 1];
						} else if (grid[i][j - 1].getDoorDirection() == DoorDirection.DOWN) {
							compCell = grid[i + 1][j - 1];
						} else if (grid[i][j - 1].getDoorDirection() == DoorDirection.LEFT) {
							compCell = grid[i][j - 2];
						} else if (grid[i][j - 1].getDoorDirection() == DoorDirection.RIGHT) {
							compCell = grid[i][j];
						}
						if(compCell.getInitial() == cell.getInitial()) {
							cell.addAdjacency(grid[i][j - 1]);
						}
					}
				}
			}
			// Allows for secret passage cells to be populated.
			for(int i = 0; i < numRows; i++) {
				for(int j = 0; j < numColumns; j++) {
					if(grid[i][j].getInitial() == cell.getInitial() && grid[i][j].isSecret()) {
						for(int k = 0; k < numRows; k++) {
							for (int l = 0; l < numColumns; l++) {
								if(grid[k][l].getInitial() == grid[i][j].getSecretPassage() && grid[k][l].isRoomCenter()) {
									cell.addAdjacency(grid[k][l]);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
