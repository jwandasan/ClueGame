package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private static Set<Card> seen = new HashSet<Card>();
	private Solution aSolution = new Solution();
	public ComputerPlayer(String name, Color color, Integer row, Integer column) {
		super(name, color, row, column);
	}

	public void createSuggestion() {
		Random rand = new Random();
		Solution suggestion = null;
		Set<Card> unSeen = getHand();//Starts as current hand
		seen = getSeenCard();//collects seen card
		
		//Sets arraylists of specific cards
		ArrayList<Card> unSeenWeapons = new ArrayList<Card>();
		ArrayList<Card> unSeenPeople = new ArrayList<Card>();
		ArrayList<Card> unSeenRooms = new ArrayList<Card>();
		
		//sets variables for later use
		Card randWeapon;
		Card randPerson;
		Card randRoom;
		
		
		for(Card iter: seen) {//Removes seen cards and makes unSeen set actually unSeen
			if(seen.contains(iter)) {
				unSeen.remove(iter);
			}
		}
		
		// Organizes unseen cards by type
		for(Card weapon: unSeen) { 
			if(weapon.equals(CardType.WEAPON)) {
				unSeenWeapons.add(weapon);
			}else {
				continue;
			}
		}
		for(Card person: unSeen) {
			if(person.equals(CardType.PERSON)) {
				unSeenPeople.add(person);
			} else {
				continue;
			}
		}
		for(Card room: unSeen) {
			if(room.equals(CardType.ROOM)) {
				unSeenRooms.add(room);
			}else {
				continue;
			}
		}
		
		//Gets a random card from unseen arraylist
		
		randWeapon = unSeenWeapons.get(rand.nextInt(unSeenWeapons.size()));
		randPerson = unSeenPeople.get(rand.nextInt(unSeenPeople.size()));
		randRoom = unSeenRooms.get(rand.nextInt(unSeenRooms.size()));
		
		chooseSolution(randRoom, randPerson, randWeapon);
	}
	
	
	public Solution chooseSolution(Card roomAnswer, Card personAnswer, Card weaponAnswer) {
		aSolution.setRoom(roomAnswer);
		aSolution.setPerson(personAnswer);
		aSolution.setWeapon(weaponAnswer);
		return aSolution;
		
	}
	
	public void updateSeen(Card aCard) {
		seen.add(aCard);
	}
	
	public Solution getSolution() {
		return aSolution;
	}
	
	public BoardCell selectTargets(Set<BoardCell> setOfTargets) {
		Random rand = new Random();
		BoardCell room = new BoardCell(row,column);
		boolean select = true;
		Set<BoardCell> targets = setOfTargets;
		BoardCell returnRoom = null;
		for(BoardCell aCell: setOfTargets) {
			if (aCell.isRoom()) {
				for(Card iter: seen) {
					if (iter.getCardName().equals(aCell.getRoomName())) {
						select = false;
					}
				}
				if(select == false) {
					if(aCell.isRoom()) {
						returnRoom = aCell;
					}	
				}
			} else {
				int randVar = rand.nextInt(targets.size());
				int iter = 0;
				for(BoardCell randRoom: targets) {
					if(iter == randVar) {
						returnRoom = randRoom;
						break;
					}
					else {
						iter++;
					}
				}
			}
		}
		/*
		if(room.isRoom()) {
			for(Card iter: seen) {
				if (iter.getCardName().equals(room.getRoomName())) {
					select = false;
				}
			}
			if(select == false) {
				returnRoom = room;
			}
		}else {
			int randVar = rand.nextInt(targets.size());
			int iter = 0;
			for(BoardCell randRoom: targets) {
				if(iter == randVar) {
					returnRoom = randRoom;
					break;
				}
				else {
					iter++;
				}
			}
		}
		*/
		return returnRoom;
		
	}
}
