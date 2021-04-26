package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public abstract class Player {
	private Board board = Board.getInstance();
	private String name;
	private Color color;
    private Set<Card> hand = new HashSet<Card>();
	private Set<Card> seenCard = new HashSet<Card>();//public or private
	private Set<Card> unSeenCards = new HashSet<Card>();
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
		ArrayList<Card> copyDeck = Board.getCopyDeck();
		for(Card iter: copyDeck) { //iterates through hand
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
		this.seenCard.add(seenCard);
	}

	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void setUnseen() {
		Set<Card> deckOfCards = board.getDeck();
		for(Card aCard: hand) {
			for(Card cardTwo: deckOfCards) {
				if(aCard != cardTwo) {
					unSeenCards.add(cardTwo);
				}
			}
		}
	}
	
	public Set<Card> getUnseen() {
		return unSeenCards;
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
	
	public void setPlayerRow(int row) {
		this.row = row;
	}
	
	public void setPlayerCol(int col) {
		this.column = col;
	}

	public Set<Card> getSeenCard() {
		return seenCard;
	}

	public boolean isHasSol() {
		return hasSol;
	}

	public void setHasSol(boolean hasSol) {
		this.hasSol = hasSol;
	}
	
	public Set<Card> getHand(){
		return hand;
	}
	
	
	
}
