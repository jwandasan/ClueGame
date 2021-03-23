package clueGame;

public class Card {
	private String cardName;
	
	public Card(String cardName) {
		this.cardName = cardName;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public boolean equals(Card card) {
		if(card.getCardName() == cardName) {
			return true;
		} else {
			return false;
		}
	}
}
