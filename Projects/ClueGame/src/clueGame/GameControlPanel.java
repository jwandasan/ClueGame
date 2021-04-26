package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.*;

public class GameControlPanel extends JPanel{
	private JTextField theGuess = new JTextField(5);
	private JTextField guessResult = new JTextField(5);
	private JTextField currPlayer = new JTextField(5);
	private JTextField rollText = new JTextField(5);
	public Player currentTurn;
	private static Board board = Board.getInstance();
	private boolean unfinished = true;
	private boolean lastDidSuggestion;
	private int rollNum; 
	private int turnNum = 0;
	private JDialog accusationFrame = new JDialog();
	private JComboBox<String> peopleCombo = new JComboBox<String>();
	private JComboBox<String> weaponsCombo = new JComboBox<String>();
	private JComboBox<String> roomsCombo = new JComboBox<String>();
	private JDialog endGame = new JDialog();
	private JTextField endText = new JTextField(10);
	private JDialog unfinishedDialog = new JDialog();
	private JPanel textPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	
	public GameControlPanel() {
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopPanel();
		add(panel, BorderLayout.NORTH);	// Adds top of total panel with Whose Turn it is, roll, and the required buttons
		panel = createBottomPanel();	
		add(panel, BorderLayout.SOUTH); // Adds bottom half of total panel with guess and guessResult
		rollDice();
		setGuess("I have no guess");
		setGuessResult("So you have nothing");
		board.initializeCards();
		ArrayList<Player> players = board.getAllPlayers();
		currentTurn = players.get(0);
		setTurn(players.get(0), rollNum);
		BoardCell aCell = board.getCell(currentTurn.getPlayerRow(), currentTurn.getPlayerColumn());
		board.drawTargets(aCell, rollNum, currentTurn);
	}
	
	public void setLastDidSuggestion(boolean lastSuggestion) {
		lastDidSuggestion = lastSuggestion;
	}
	
	public boolean getLastDidSuggestion() {
		return lastDidSuggestion;
	}
	
	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		
		// Creates a Panel for whose turn it is
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,0));
		JLabel turnLabel = new JLabel("Whose Turn?");
		turnPanel.add(turnLabel, BorderLayout.NORTH);
		turnPanel.add(currPlayer, BorderLayout.SOUTH);
		panel.add(turnPanel);
		
		// Creates a Panel for the current roll
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(0,2));
		JLabel rollLabel = new JLabel("Roll:");
		rollText.setSize(new Dimension(1,1));
		rollText.setBorder(new LineBorder(Color.blue));
		rollPanel.add(rollLabel, BorderLayout.NORTH);
		rollPanel.add(rollText, BorderLayout.NORTH);
		panel.add(rollPanel);
		
		// Adds 2 buttons needed for player interaction
		JButton accusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		next.addActionListener((new NextButtonListener()));
		accusation.addActionListener((new AccusationButtonListener()));
		panel.add(accusation);
		panel.add(next);
		return panel;
	}
	
	private JPanel createBottomPanel() {
		// Creates a new panel for the bottom portion
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		// Creates a new guess panel and populates it accordingly
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessPanel.setBorder(new TitledBorder("Guess"));
		guessPanel.add(theGuess);;
		panel.add(guessPanel);
		
		// Creates a new guess result panel and populates it accordingly
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,0));
		resultPanel.setBorder(new TitledBorder("Guess Result"));
		resultPanel.add(guessResult);
		panel.add(resultPanel);
		
		return panel;
	}
	
	public void setTurn(Player aPlayer, int turnNum) {
		currPlayer.setEditable(false);
		rollText.setEditable(false);
		String playerName = aPlayer.getPlayerName();
		Color color = aPlayer.getPlayerColor();
		currPlayer.setBackground(color);
		currPlayer.setText(playerName);
		rollText.setText(String.valueOf(turnNum));
	}
	
	public void setGuess(String guess) {
		theGuess.setEditable(false);
		theGuess.setText(guess); // Updates JTextField theGuess with the right information passed in.
	}
	
	public void setGuessResult(String guessResult) {
		this.guessResult.setEditable(false);
		this.guessResult.setText(guessResult); // Updates JTextField guessResult with the right information passed in.
	}
	
	private class OkButtonListenerTwo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			unfinishedDialog.setVisible(false);
			textPanel.removeAll();
			buttonPanel.removeAll();
		}
		
	}
	
	private class NextButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(unfinished) {
				unfinishedDialog.setSize(400, 100);
				unfinishedDialog.setLayout(new GridLayout(0,2));
				unfinishedDialog.setTitle("Cannot Continue");
				unfinishedDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new OkButtonListenerTwo());
	
				JTextField notFinished = new JTextField(10);
				notFinished.setText("Finish your turn.");
				notFinished.setEditable(false);
				
				textPanel.setSize(300, 100);
				textPanel.add(notFinished);
				buttonPanel.add(okButton);
				
				unfinishedDialog.add(textPanel);
				unfinishedDialog.add(buttonPanel);
				
				unfinishedDialog.setVisible(true);
			} else {
				if(turnNum == 5) {
					turnNum = 0;
				} else {
					turnNum++;
				}
				currentTurn = board.getNextPlayer(currentTurn, turnNum);
				rollDice();
				setTurn(currentTurn, rollNum);
				if(currentTurn instanceof HumanPlayer) {
					unfinished = true;
					board.getCardPanel().updatePanel(currentTurn);
					BoardCell aCell = board.getCell(currentTurn.getPlayerRow(), currentTurn.getPlayerColumn());
					board.drawTargets(aCell, rollNum, currentTurn);
				} else if(currentTurn instanceof ComputerPlayer) {
					board.computerTargets(currentTurn, rollNum);
					if(lastDidSuggestion) {
						((ComputerPlayer) currentTurn).createSuggestion();
						Solution compSuggestion = ((ComputerPlayer) currentTurn).getSolution();
						board.handleSuggestionComputer(compSuggestion);
					}
				}
			}
		}
	}
	
	private class AccusationButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			showAccusationPopUp();
		}
	}
	

	
	public int rollDice() {
		Random rand = new Random();
		while(true) {
			rollNum = rand.nextInt(6);
			if(rollNum > 0) {
				break;
			} else {
				rollNum = rand.nextInt(6);
			}
		}
		
		return this.rollNum;
	}
	
	public void setUnfinished(boolean status) {
		this.unfinished = status;
	}
	
	public void showAccusationPopUp() {
		accusationFrame.setTitle("Make an accusation");
		accusationFrame.setSize(600, 200);
		accusationFrame.setLayout(new GridLayout(1,3));
		accusationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Set<Card> handOfCards = currentTurn.getHand();
		
		// Creates JButtons for accusationFrame
		JPanel buttonPanel = new JPanel();
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		submitButton.addActionListener(new SubmitButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
		
		// Creates JPanels to "Title the combo boxes"
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder("People"));
		JPanel weaponPanel = new JPanel();
		weaponPanel.setBorder(new TitledBorder("Weapons"));
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(new TitledBorder("Rooms"));
		
		// Sets up the Combo boxes for the menu
		peopleCombo.setSize(100,20);
		weaponsCombo.setSize(100,20);
		roomsCombo.setSize(100,20);

		
		// Populates the combo boxes with the correct cards
		for(Card aCard: handOfCards) {
			if(aCard.getCardType() == CardType.PERSON) {
				peopleCombo.addItem(aCard.getCardName());
			} else if(aCard.getCardType() == CardType.WEAPON) {
				weaponsCombo.addItem(aCard.getCardName());
			} else if(aCard.getCardType() == CardType.ROOM) {
				roomsCombo.addItem(aCard.getCardName());
			}
		}
		
		// Adds combo boxes to panels
		peoplePanel.add(peopleCombo);
		weaponPanel.add(weaponsCombo);
		roomPanel.add(roomsCombo);
		
		// Adds name Panels
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);
		accusationFrame.add(peoplePanel);
		accusationFrame.add(weaponPanel);
		accusationFrame.add(roomPanel);
		accusationFrame.add(buttonPanel);
		
		accusationFrame.setVisible(true);
		
	}
	
	private class SubmitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			accusationFrame.setVisible(false);
			Set<Card> deck = board.getDeck();
			Card personCard = null;
			Card weaponCard = null;
			Card roomCard = null;
			String person = (String) peopleCombo.getSelectedItem();
			String weapon = (String) weaponsCombo.getSelectedItem();
			String room = (String) roomsCombo.getSelectedItem();
			
			for(Card aCard: deck) {
				if(person == aCard.getCardName()) {
					personCard = aCard;
				} else if(weapon == aCard.getCardName()) {
					weaponCard = aCard;
				} else if(room == aCard.getCardName()) {
					roomCard = aCard;
				}
			}
			Solution finalAccusation = new Solution(roomCard, personCard, weaponCard);
			endGame.setSize(new Dimension(500, 100));
			endGame.setLayout(new GridLayout(0,1));
			endText.setEditable(false);
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new OkButtonListener());
			if(board.checkAccusation(finalAccusation) == false) {
				endGame.setTitle("You Lost!");
				endText.setText("Unfortunately you have lost the game. Good Luck Next Time!");
				endGame.add(endText);
				endGame.add(okButton);
				endGame.setVisible(true);
			} else if(board.checkAccusation(finalAccusation) == true) {
				endGame.setTitle("You Have Won!");
				endText.setText("You have managed to win the game! Good Job!");
				endGame.add(endText);
				endGame.add(okButton);			
				endGame.setVisible(true);
			}
		}
	}
	
	private class OkButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
		
	}
	
	private class CancelButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			accusationFrame.setVisible(false);
		}
		
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setTitle("Clue Game");
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}
