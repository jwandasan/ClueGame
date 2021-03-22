package clueGame;

import java.awt.Color;

public abstract class Player {
	private String name;
	private Color color;
	private Card card;
	protected int row, column;
	
	public Player(String name, Color color, Integer row, Integer column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}

	public void updateHand(Card card) {
		this.card = card;
	}
}
