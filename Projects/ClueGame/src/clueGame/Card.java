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
	
	
	public boolean equals(CardType card) {
		if(this.CardType.equals(card)) {
			return true;
		} else {
			return false;
		}
	}
	
	

	public CardType getCardType() {
		return CardType;
	}
}
