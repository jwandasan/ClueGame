package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.*;

public class CardPanel extends JPanel {
	private JTextField peopleHand = new JTextField();
	private JPanel peopleSeen = new JPanel();
	private JTextField roomHand = new JTextField();
	private JPanel roomsSeen = new JPanel();
	private JTextField weaponHand = new JTextField();
	private JPanel weaponsSeen = new JPanel();
	
	private static Card batCard = new Card("Bat", CardType.WEAPON);
	private static Card knifeCard = new Card("Knife", CardType.WEAPON);
	private static Card handgunCard = new Card("Handgun", CardType.WEAPON);
	private static Card swordCard = new Card("Sword", CardType.WEAPON);
	private static Card hammerCard = new Card("Hammer", CardType.WEAPON);
	private static Card panCard = new Card("Pan", CardType.WEAPON);
	private static Card atriumCard = new Card("Atrium", CardType.ROOM);
	private static Card readingAreaCard = new Card("Reading Area", CardType.ROOM);
	private static Card collectorsRoomCard = new Card("Collectors Room", CardType.ROOM);
	private static Card officeCard = new Card("Office", CardType.ROOM);
	private static Card closetCard = new Card("Closet", CardType.ROOM);
	private static Card studioCard = new Card("Studio", CardType.ROOM);
	private static Card bathroomCard = new Card("Bathroom", CardType.ROOM);
	private static Card musicRoomCard = new Card("Music Room", CardType.ROOM);
	private static Card greenHouseCard = new Card("Green House", CardType.ROOM);
	private Card quinnCard = new Card("Quinn XCII", CardType.PERSON);
	private static Card alexCard = new Card("Alexander 23", CardType.PERSON);
	private static Card jeremyCard = new Card("Jeremy Zucker", CardType.PERSON);
	private static Card chelseaCard = new Card("Chelsea Cutler", CardType.PERSON);
	private static Card ayokayCard = new Card("Ayokay", CardType.PERSON);
	private static Card jonCard = new Card("Jon Bellion", CardType.PERSON);
	
	public CardPanel() {
		setLayout(new GridLayout(3,0));
		JPanel panel = createPeoplePanel();
		setBorder(new TitledBorder("Known Cards"));
		add(panel);
		
		panel = createRoomPanel();
		add(panel);
		
		panel = createWeaponPanel();
		add(panel);
	}
	
	private JPanel createPeoplePanel() {
		// Creates a new panel for People
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder("People"));
		peoplePanel.setLayout(new GridLayout(2,0));
		
		// Creates a new panel for people in hand
		JPanel handPeoplePanel = new JPanel();
		JLabel handPeopleLabel = new JLabel("In Hand:");
		handPeoplePanel.setLayout(new GridLayout(2,0));
		handPeoplePanel.setName("In Hand:");
		handPeoplePanel.add(handPeopleLabel);
		handPeoplePanel.add(peopleHand);
		peoplePanel.add(handPeoplePanel);
		
		// Creates a new panel for people that have been seen
		JLabel seenRoomLabel = new JLabel("Seen:");
		peopleSeen.setLayout(new GridLayout(5,0));
		peopleSeen.setName("Seen:");
		peopleSeen.add(seenRoomLabel);
		peoplePanel.add(peopleSeen);
		
		return peoplePanel;
	}
	
	private JPanel createRoomPanel() {
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder("Rooms"));
		roomPanel.setLayout(new GridLayout(2,0));
		
		// Creates a new panel for people in hand
		JPanel handWeaponPanel = new JPanel();
		JLabel handWeaponLabel = new JLabel("In Hand:");
		handWeaponPanel.setLayout(new GridLayout(2,0));
		handWeaponPanel.setName("In Hand:");
		handWeaponPanel.add(handWeaponLabel);
		handWeaponPanel.add(roomHand);
		roomPanel.add(handWeaponPanel);
		
		// Creates a new panel for people that have been seen
		JLabel seenRoomLabel = new JLabel("Seen:");
		roomsSeen.setLayout(new GridLayout(8,0));
		roomsSeen.setName("Seen:");
		roomsSeen.add(seenRoomLabel);
		roomPanel.add(roomsSeen);
		
		return roomPanel;
	}
	
	private JPanel createWeaponPanel() {
		JPanel weaponPanel = new JPanel();
		weaponPanel.setBorder(new TitledBorder("Weapons"));
		weaponPanel.setLayout(new GridLayout(2,0));
		
		// Creates a new panel for people in hand
		JPanel handWeaponPanel = new JPanel();
		JLabel handWeaponLabel = new JLabel("In Hand:");
		handWeaponPanel.setLayout(new GridLayout(2,0));
		handWeaponPanel.setName("In Hand:");
		handWeaponPanel.add(handWeaponLabel);
		handWeaponPanel.add(weaponHand);
		weaponPanel.add(handWeaponPanel);
		
		// Creates a new panel for people that have been seen
		JLabel seenRoomLabel = new JLabel("Seen:");
		weaponsSeen.setLayout(new GridLayout(5,0));
		weaponsSeen.setName("Seen:");
		weaponsSeen.add(seenRoomLabel);
		weaponPanel.add(weaponsSeen);
		
		return weaponPanel;
	}
	
	public JPanel addJTextField() {
		JPanel panel = new JPanel();
		
		return panel;
	}
	
	public void setPeopleHand(Card personCard) {
		peopleHand.setText(personCard.getCardName());
	}
	
	public void setPeopleSeen(Set<Card> peopleSeen) {
		int peopleCount = 0;
		for(Card aCard: peopleSeen) {
			if(aCard.getCardType() == CardType.PERSON) {
				peopleCount++;
				JTextField peopleText = new JTextField(5);
				peopleText.setText(aCard.getCardName());
				this.peopleSeen.add(peopleText, BorderLayout.PAGE_END);
			}
		}
		this.peopleSeen.updateUI();
	}
	
	public void setRoomHand(Card roomCard) {
		roomHand.setText(roomCard.getCardName());
	}
	
	public void setRoomSeen(Set<Card> roomsSeen) {
		for(Card aCard: roomsSeen) {
			if(aCard.getCardType() == CardType.ROOM) {
				JTextField peopleText = new JTextField(5);
				peopleText.setText(aCard.getCardName());
				this.roomsSeen.add(peopleText, BorderLayout.PAGE_END);
			}
		}
		this.roomsSeen.updateUI();
	}
	
	public void setWeaponHand(Card weaponCard) {
		weaponHand.setText(weaponCard.getCardName());
	}
	
	public void setWeaponSeen(Set<Card> weaponsSeen) {
		for(Card aCard: weaponsSeen) {
			if(aCard.getCardType() == CardType.WEAPON) {
				JTextField peopleText = new JTextField(5);
				peopleText.setText(aCard.getCardName());
				this.weaponsSeen.add(peopleText, BorderLayout.PAGE_END);
			}
		}
		this.weaponsSeen.updateUI();
	}
	
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setTitle("Clue Game");
		frame.setSize(200, 700); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		HumanPlayer aHuman = new HumanPlayer("Quinn XCII", Color.red, 0, 7);
		ComputerPlayer computerOne = new ComputerPlayer("Alexander 23", Color.black, 18, 0);
		ComputerPlayer computerTwo = new ComputerPlayer("Jeremy Zucker", Color.blue, 24, 9);
		ComputerPlayer computerThree = new ComputerPlayer("Chelsea Cutler", Color.green, 7, 23);
		ComputerPlayer computerFour = new ComputerPlayer("Ayokay", Color.cyan, 24, 14);
		ComputerPlayer computerFive = new ComputerPlayer("Jon Bellion", Color.yellow, 17, 23);
		
		aHuman.updateHand(jonCard);
		aHuman.updateHand(batCard);
		aHuman.updateHand(atriumCard);
		aHuman.updateSeen(alexCard);
		aHuman.updateSeen(jeremyCard);
		aHuman.updateSeen(chelseaCard);
		aHuman.updateSeen(ayokayCard);
		aHuman.updateSeen(jonCard);
		aHuman.updateSeen(knifeCard);
		aHuman.updateSeen(handgunCard);
		aHuman.updateSeen(swordCard);
		aHuman.updateSeen(hammerCard);
		aHuman.updateSeen(panCard);
		aHuman.updateSeen(readingAreaCard);
		aHuman.updateSeen(collectorsRoomCard);
		aHuman.updateSeen(officeCard);
		aHuman.updateSeen(closetCard);
		aHuman.updateSeen(studioCard);
		aHuman.updateSeen(bathroomCard);
		aHuman.updateSeen(musicRoomCard);
		aHuman.updateSeen(greenHouseCard);
		Set<Card> humanSeenCards = aHuman.getSeenCard();
		
		panel.setPeopleHand(jonCard);
		panel.setWeaponHand(batCard);
		panel.setRoomHand(atriumCard);
		panel.setPeopleSeen(humanSeenCards);
		panel.setRoomSeen(humanSeenCards);
		panel.setWeaponSeen(humanSeenCards);
		
	}
}
