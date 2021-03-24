package clueGame;

public class Card {
	private String cardName;
	private CardType CardType;
	
	public Card(String cardName, CardType CardType) {
		this.cardName = cardName;
		this.CardType = CardType;
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

	public CardType getCardType() {
		return CardType;
	}
}
