package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public abstract class Player {
	private String name;
	private Color color;
	private static Set<Card> hand = new HashSet<Card>();
	private static Set<Card> seenCard = new HashSet<Card>();//public or private
	protected int row, column;
	private boolean hasSol = false;
	
	public Player(String name, Color color, Integer row, Integer column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}
	
	public Card disproveSuggestion(Solution accusation) { //Takes in accusation
		Card newCard = null;
		for(Card iter: hand) { //iterates through hand
			if(iter.equals(accusation.getPerson())) { //Returns card if player holds card said in accusation
				newCard = accusation.getPerson();
			}
			else if (iter.equals(accusation.getRoom())) {
				newCard = accusation.getRoom();
			}
			else if (iter.equals(accusation.getWeapon())) {
				newCard = accusation.getWeapon();
			}
			else {
				continue;
			}
		} 
		return newCard;
	}
	
	public void clearHand() {
		hand.clear();
	}
	
	public void updateSeen(Card seenCard) {
		Player.seenCard.add(seenCard);
	}

	public void updateHand(Card card) {
		hand.add(card);
	}
	public String getPlayerName() {
		return name;
	}
	public Color getPlayerColor() {
		return color;
	}
	public Integer getPlayerRow() {
		return row;
	}
	public Integer getPlayerColumn() {
		return column;
	}
	

	public static Set<Card> getSeenCard() {
		return seenCard;
	}

	public boolean isHasSol() {
		return hasSol;
	}

	public void setHasSol(boolean hasSol) {
		this.hasSol = hasSol;
	}
	
	public static Set<Card> getHand(){
		return hand;
	}
	
}
