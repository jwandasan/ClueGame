package clueGame;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	private boolean isDealt = false;
	public void setPerson(Card person) {
		this.person = person;
	}
	
	public void setRoom(Card room) {
		this.room = room;
	}
	
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}

	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
	public void setIsDealt(boolean isDealt) {
		this.isDealt = isDealt;
	}
	public boolean getIsDealt() {
		return isDealt;
	}
	
	
}
