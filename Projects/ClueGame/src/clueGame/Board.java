package clueGame;

import java.io.*;
import java.util.*;
import java.awt.Color;
import java.io.*;
import java.util.Map.Entry;
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
	private Map<Character, String> charToName = new HashMap<Character, String>();
	private Map<Character, Room> roomMap = new HashMap<Character,Room>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<String> rooms = new HashSet<String>();
	private Set<String> characters = new HashSet<String>();
	private Set<String> weapons = new HashSet<String>();
	private static Set<Card> deckOfCards = new HashSet<Card>();
	private HumanPlayer human;
	private ComputerPlayer computer;
	private Map<String, ComputerPlayer> computers = new HashMap<String, ComputerPlayer>();
	private ArrayList<Player> allPlayers = new ArrayList<Player>();
	ArrayList<Card> copyDeck = new ArrayList<Card>();
	private static Solution theAnswer = new Solution();
	private static final int MAX_PLAYERS = 6;
	
	private Board() {
		super();
	}
	
	/*
	 * 
	 * All getters
	 * 
	 */
	 
	public static Board getInstance() {//returns the instance of a single board
		return theInstance;
	}
	
	public static Set<Card> getDeck(){
		return deckOfCards;
	}
	
	public HumanPlayer getHuman() {
		return human;
	}
	
	public Map<String, ComputerPlayer> getComputerList() {
		return computers;
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
	
	public static Solution getSolution() {
		return theAnswer;
	}
	
	public ArrayList<Player> getAllPlayers(){
		return allPlayers;
	}
	/*
	 * Ends section for all getter methods
	 */

	/*
	 * 
	 *  All setters
	 *  
	 */
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
	
	/*
	 * Ends Section for all setters
	 */
	
	/*
	 * 
	 * All other functions needed
	 * 
	 */
	
	public void initialize() throws FileNotFoundException, BadConfigFormatException{ //Loads files properly
		loadConfigFiles();
	}
	public void initializeCards() {
		loadCards();
		setupSolution();
		dealCards();
	}
	
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {//loads the setup and layout configurations
		loadSetupConfig();
		loadLayoutConfig();
	}
	
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
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
					if (split[0].contentEquals("Room") || split[0].contentEquals("Space")) {	// Loads information for rooms and spaces
						charToName.put(split[2].charAt(0), split[1]);
						if(split[0].contentEquals("Room")) {
							
							rooms.add(split[1]);
						}
					} else if(split[0].contentEquals("Human") || split[0].contentEquals("Weapon") || split[0].contentEquals("Computer")) {	// Loads information for players and weapons
						if(split[0].contentEquals("Human")) {
							characters.add(split[1]);
							human = new HumanPlayer(split[1], Color.red, Integer.valueOf((split[3])), Integer.valueOf(split[4]));
							allPlayers.add(human);
						} else if(split[0].contentEquals("Weapon")) {
							weapons.add(split[1]);
						} else if (split[0].contentEquals("Computer")) {
							characters.add(split[1]);
							if (split[2].contentEquals("black")) {
								computer = new ComputerPlayer(split[1], Color.black, Integer.valueOf(split[3]), Integer.valueOf(split[4]));
							} else if (split[2].contentEquals("blue")) {
								computer = new ComputerPlayer(split[1], Color.blue, Integer.valueOf(split[3]), Integer.valueOf(split[4]));
							} else if (split[2].contentEquals("green") ) {
								computer = new ComputerPlayer(split[1], Color.green, Integer.valueOf(split[3]), Integer.valueOf(split[4]));
							} else if (split[2].contentEquals("cyan")) {
								computer = new ComputerPlayer(split[1], Color.cyan, Integer.valueOf(split[3]), Integer.valueOf(split[4]));
							} else if (split[2].contentEquals("yellow")) {
								computer = new ComputerPlayer(split[1], Color.yellow, Integer.valueOf(split[3]), Integer.valueOf(split[4]));
							}
							
							computers.put(split[1], computer);
							allPlayers.add(computer);
						}
					} else {	// If there is any errors in the file, there will be a BadConfigFormatException
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
					if(labels[j].charAt(0) == 'W') {
						cell.setIsWalkway(true);
					}
					if(labels[j].length() > 1) {
						switch(labels[j].charAt(1)) {
						case '<':
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.LEFT);
							cell.setIsDoorway(true);
							break;
						case '>':
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.RIGHT);
							cell.setIsDoorway(true);
							break;
						case '^':
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.UP);
							cell.setIsDoorway(true);
							break;
						case 'v':
							cell.setLabel(labels[j]);
							cell.setDoorDirection(DoorDirection.DOWN);
							cell.setIsDoorway(true);
							break;
						case '#':
							cell.setLabel(labels[j]);
							cell.setIsLabel(true);
							break;
						case '*':
							cell.setLabel(labels[j]);
							cell.setIsCenter(true);
							room.setCenterCell(cell);
							break;
						}
						
						if(Character.isLetter(labels[j].charAt(1))) {
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
	
	/*
	 * 
	 * Beginning of section of code includes everything for calcTargets
	 * 
	 */
	
	public void calculateTargets(BoardCell startCell, int pathlength) {
		calcAdjacency(startCell);
		visited.add(startCell);
		Set<BoardCell> tempAdj = startCell.getAdjList();
		for (BoardCell possMove: tempAdj) {
			if (!(visited.contains(possMove))) {
				visited.add(possMove);
				 if(possMove.isOccupied() && !possMove.isRoomCenter()) {
						continue;
				} else if (pathlength == 1 || possMove.isRoomCenter()) {
					targets.add(possMove);
				} else if (pathlength != 1) {
					calculateTargets(possMove, pathlength - 1);
				}
				visited.remove(possMove);
			} else if ((visited.contains(possMove))){
				continue;
			}
		}
	}
	
	public void calcTargetExecute(BoardCell startCell, int pathlength) {	// Sets visited and targets to new to make sure we get a "fresh" list for every target calculation
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calculateTargets(startCell, pathlength);
	}
	/*
	 * Ends section for calcTargets methods
	 */
	
	
	
	
	/*
	 * 
	 * Beginning of section of Code includes everything associated with calcAdjacency
	 * 
	 */
	public void calcAdjacency(BoardCell cell) {
		int cellRow = cell.getRow();
		int cellCol = cell.getCol();
		char cellInitial = cell.getInitial();
		if(cellInitial == 'W' && !cell.isDoorway()){	// Conditional to populate walkway adjacency with cells that are also walkways
			walkwayAdjacency(cell, cellRow, cellCol);
		} else if(cell.isDoorway()) {	// Conditional to populate walkways with doors adjacency with room center cell
			doorwayAdjacency(cell, cellRow, cellCol);
			// Taken from the regular walkway test because if a cell is a doorway, it is also a walkway
			walkwayAdjacency(cell, cellRow, cellCol);
		} else if(cell.isRoomCenter()) {	// Conditional to populate adjacency for a center to a room and it's doorways
			roomAdjacency(cell, cellInitial);
		}
	}

	private void roomAdjacency(BoardCell cell, char cellInitial) {
		BoardCell compCell = null;
		
		// Has conditionals to check door directions, and grabs the "entrance" cell to stay in the right room when iterating through the board.
		for(int i = 1; i < numRows - 1; i++) {// iterates through grid 2d array
			for(int j = 1; j < numColumns - 1; j++) {
				char gridInitial = grid[i][j].getInitial();
				if(gridInitial == cellInitial && grid[i + 1][j].isDoorway()) { //Checks if the cell above the current room board piece is a doorway
					if(grid[i + 1][j].getDoorDirection() == DoorDirection.UP) {// Checks up doorway and grabs cell to get the room we are in
						compCell = grid[i][j];
					} else if (grid[i + 1][j].getDoorDirection() == DoorDirection.DOWN) {//Checks Down door way
						compCell = grid[i + 2][j];
					} else if (grid[i + 1][j].getDoorDirection() == DoorDirection.LEFT) {//Checks left doorway
						compCell = grid[i + 1][j - 1];
					} else if (grid[i + 1][j].getDoorDirection() == DoorDirection.RIGHT) {//Checks right doorway
						compCell = grid[i + 1][j + 1];
					}
					if(compCell.getInitial() == cellInitial) {// adds cell to adjacency list if cell initial and compare cell initial is the same
						cell.addAdjacency(grid[i + 1][j]);
					}
				} else if(gridInitial == cellInitial && grid[i - 1][j].isDoorway()) {	// Checks if the cell below the current room board piece is a doorway
					if(grid[i - 1][j].getDoorDirection() == DoorDirection.UP) {	// Checks up doorway and grabs cell to get the room we are in
						compCell = grid[i - 2][j];
					} else if (grid[i - 1][j].getDoorDirection() == DoorDirection.DOWN) {//Checks Down door way
						compCell = grid[i][j];
					} else if (grid[i - 1][j].getDoorDirection() == DoorDirection.LEFT) {//Checks left doorway
						compCell = grid[i - 1][j - 1];
					} else if (grid[i - 1][j].getDoorDirection() == DoorDirection.RIGHT) {//Checks right doorway
						compCell = grid[i - 1][j + 1];
					}
					if(compCell.getInitial() == cellInitial) {// adds cell to adjacency list if cell initial and compare cell initial is the same
						cell.addAdjacency(grid[i - 1][j]);
					}
				} else if(gridInitial == cellInitial && grid[i][j + 1].isDoorway()) {	// Checks if the cell to the right of the current room board piece is a doorway
					if(grid[i][j + 1].getDoorDirection() == DoorDirection.UP) {							// Checks up doorway and grabs cell to get the room we are in	
						compCell = grid[i - 1][j + 1];
					} else if (grid[i][j + 1].getDoorDirection() == DoorDirection.DOWN) {				//Checks Down door way
						compCell = grid[i + 1][j + 1];		
					} else if (grid[i][j + 1].getDoorDirection() == DoorDirection.LEFT) {				//Checks left doorway
						compCell = grid[i][j];
					} else if (grid[i][j + 1].getDoorDirection() == DoorDirection.RIGHT) {				//Checks right doorway
						compCell = grid[i][j + 2];
					}
					if(compCell.getInitial() == cellInitial) {
						cell.addAdjacency(grid[i][j + 1]);
					}
				} else if(gridInitial == cellInitial && grid[i][j - 1].isDoorway()) {	// Checks if the cell to the left of the current room board piece is a doorway
					if(grid[i][j - 1].getDoorDirection() == DoorDirection.UP) {							// Checks up doorway and grabs cell to get the room we are in
						compCell = grid[i - 1][j - 1];
					} else if (grid[i][j - 1].getDoorDirection() == DoorDirection.DOWN) {				//Checks Down door way	
						compCell = grid[i + 1][j - 1];
					} else if (grid[i][j - 1].getDoorDirection() == DoorDirection.LEFT) {				//Checks left doorway	
						compCell = grid[i][j - 2];	
					} else if (grid[i][j - 1].getDoorDirection() == DoorDirection.RIGHT) {				//Checks right doorway
						compCell = grid[i][j];	
					}
					if(compCell.getInitial() == cellInitial) {// adds cell to adjacency list if cell initial and compare cell initial is the same
						cell.addAdjacency(grid[i][j - 1]);
					}
				}
			}
		}
		// Allows for secret passage cells to be populated.
		for(int i = 0; i < numRows; i++) {	// Iterates through the grid to find a cell that has a secret passage
			for(int j = 0; j < numColumns; j++) {
				char gridInitial = grid[i][j].getInitial();
				if(gridInitial == cellInitial && grid[i][j].isSecret()) {		
					for(int k = 0; k < numRows; k++) {	// Iterates through the grid again to find a room with the appropriate secret passage
						for (int l = 0; l < numColumns; l++) {
							if(grid[k][l].getInitial() == grid[i][j].getSecretPassage() && grid[k][l].isRoomCenter()) {	// Grabs the center cell of the connecting secret passage
								cell.addAdjacency(grid[k][l]);															// and adds it to the adjacency list
								break;	
							}
						}
					}
				}
			}
		}
					
	}

	private void doorwayAdjacency(BoardCell cell, int cellRow, int cellCol) {
		BoardCell compCell = null;
		if(cell.getDoorDirection() == DoorDirection.UP) {	// Checks up doorway and makes compCell the cell right after "entering" doorway
			compCell = grid[cellRow - 1][cellCol];
		} else if(cell.getDoorDirection() == DoorDirection.DOWN) {	// Checks down doorway and makes compCell the cell right after "entering" doorway
			compCell = grid[cellRow + 1][cellCol];
		} else if(cell.getDoorDirection() == DoorDirection.RIGHT) {	// Checks right doorway and makes compCell the cell right after "entering" doorway
			compCell = grid[cellRow][cellCol + 1];
		} else if(cell.getDoorDirection() == DoorDirection.LEFT) {	// Checks left doorway and makes compCell the cell right after "entering" doorway
			compCell = grid[cellRow][cellCol - 1];
		} 	
		
		for(int i = 0; i < numRows; i++) { //gets the proper room center and adds to adjacency list
			for(int j = 0; j < numColumns; j++) {
				if((compCell.getInitial() == grid[i][j].getInitial()) && grid[i][j].isRoomCenter()) {
					cell.addAdjacency(grid[i][j]);
				}
			}
		}
	}

	private void walkwayAdjacency(BoardCell cell, int cellRow, int cellCol) {
		if(cellRow >= 1 && grid[cellRow - 1][cellCol].isWalkway()) {	// Adds cell above the current walkway to adjacency list
			cell.addAdjacency(grid[cellRow - 1][cellCol]);
		} if(cellRow < numRows - 1 && grid[cellRow + 1][cellCol].isWalkway()) {	// Adds cell above the current walkway to adjacency list
			cell.addAdjacency(grid[cellRow + 1][cellCol]);
		} if(cellCol >= 1 && grid[cellRow][cellCol - 1].isWalkway()) {	// Adds cell above the current walkway to adjacency list
			cell.addAdjacency(grid[cellRow][cellCol - 1]);
		} if(cellCol < numColumns - 1 && grid[cellRow][cellCol + 1].isWalkway()) {	// Adds cell above the current walkway to adjacency list
			cell.addAdjacency(grid[cellRow][cellCol + 1]);
		}
	}
	/*
	 * Ends section for calcAdjacency methods
	 */
	
	/*
	 * 
	 * Card Setup
	 * 
	 */
	
	public void loadCards() {
		deckOfCards.clear();
		for(String weapon: weapons) {
			Card aWeapon = new Card(weapon, CardType.WEAPON);
			deckOfCards.add(aWeapon);
		}
		for(String person: characters) {
			Card aPerson = new Card(person, CardType.PERSON);
			deckOfCards.add(aPerson);
		}
		for(String room: rooms) {
			Card aRoom = new Card(room, CardType.ROOM);
			deckOfCards.add(aRoom);
		}
		for(Card copyCard: deckOfCards) {
			copyDeck.add(copyCard);
		}
	}
	
	public void setupSolution() {
		Random rand = new Random();
		int randWeaponNum = rand.nextInt(6);
		int randPersonNum = rand.nextInt(6);
		int randRoomNum = rand.nextInt(9);
		int numRoom = 0, numWeapon = 0, numPerson = 0;
		for(Card aCard: deckOfCards) {
			if(aCard.getCardType() == CardType.WEAPON) {
				if(numWeapon == randWeaponNum) {
					theAnswer.setWeapon(aCard);
					copyDeck.remove(aCard);
				}
				numWeapon++;
			} else if(aCard.getCardType() == CardType.PERSON) {
				if(numPerson == randPersonNum) {
					theAnswer.setPerson(aCard);
					copyDeck.remove(aCard);
				}
				numPerson++;
			} else if(aCard.getCardType() == CardType.ROOM) {
				if(numRoom == randRoomNum) {
					theAnswer.setRoom(aCard);
					copyDeck.remove(aCard);
				}
				numRoom++;
			}
		}
		theAnswer.setIsDealt(true);
	}
	
	public void dealCards() {
		int iterator = 0;
		for(int i = 0; i < copyDeck.size(); i++) {
			allPlayers.get(iterator).updateHand(copyDeck.get(0));
			copyDeck.remove(0);
			if (iterator == allPlayers.size()) {
				iterator = 0;
			}
		}
	}
	
	/*
	 * 
	 */
	
}


