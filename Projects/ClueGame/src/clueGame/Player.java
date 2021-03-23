package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	private Set<Card> hand = new HashSet<Card>();
	protected int row, column;
	
	public Player(String name, Color color, Integer row, Integer column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
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
}
