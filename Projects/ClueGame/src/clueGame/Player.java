package clueGame;

import java.awt.Color;

public abstract class Player {
	private String name;
	private Color color;
	protected int row, column;
	private static final int MAX_PLAYERS = 6;
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public void updateHand(Card card) {
		
	}
}
