/* The following 2 lines are for connecting this
 * program to the overall "Casino" project:
 * */
package Games;
import Shared.Player;

/*
 * This program is part of the overall "Casino"
 * final project for CS 2450-01 Fall 2024.
 * This program is the "Blackjack" game of the
 * "Casino." This program is written by
 * Moises Santander for Group 6.
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.util.ArrayList;
import java.io.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;

public class BlackJack {
	//ArrayLists for the dealer's & player's hands
	ArrayList<Card> dealerHand;
	ArrayList<Card> playerHand;
	
	//calls to classes for shoe & player
	static Shoe shoe;
	static Player dealer;
	static Player player;
	
	BlackJack() {
		//JFrame
		JFrame jfrm = new JFrame("Blackjack Game");
		jfrm.setSize(500, 500);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new FlowLayout());
		
		//create JButtons for the following actions:
		//hit, stand, surrender
		JButton hitBut = new JButton("Hit");
		JButton standBut = new JButton("Stand");
		JButton surrenderBut = new JButton("Surrender");
		
		//action listener for "Surrender" button
		surrenderBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jfrm.dispose(); //close the JFrame
			}
		});
		
		//JPanel to hold game contents
		JPanel blackjackPanel = new JPanel();
		blackjackPanel.add(hitBut);
		blackjackPanel.add(standBut);
		blackjackPanel.add(surrenderBut);
		//add contents to blackjackPanel
		
		//add blackjackPanel to JFrame
		jfrm.getContentPane().add(blackjackPanel);
		
		//display the JFrame
		jfrm.setVisible(true);
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BlackJack();
			}
		});
		
		//initialize shoe, dealer, and player into game
		shoe = new Shoe();
		dealer = new Player();
		player = new Player();
		
		//run this loop to start a new round &
		//check for conditions
		while(true) {
			startRound();
			
			//check if the player has reached 21 score
			if(player.getTotal() == 21) {
				handleBlackJack(); //determine the player wins
			}
			else {
				handleHitOrStand(); //asks player to hit/stand
				handleFinalHands(); //counts score of hands
			}
			
			//break out of the program if players chooses to
			//not play again
			if(!playAgain()) {
				break;
			}
		}
	}
	
	public static void startRound() {
		dealer.reset();
		player.reset();
		
		reshuffleShoeCheck();
		
		dealer.addToHand(shoe.draw());
		dealer.addToHand(shoe.draw());
		
		player.addToHand(shoe.draw());
		player.addToHand(shoe.draw()); 
		
		//TODO: display message
	}
	
	public static void handleBlackJack() {
		printGameState(false);
		
		if(dealer.getTotal() == 21) {
			//TODO: display "Tie" message
		}
		else {
			//TODO: display "You win!" message
		}
	}
	
	public static void handleHitOrStand() {
		printGameState(true);
		while(player.getTotal() < 22) {
			//TODO: display options "Woudl you like to...?"
			//TODO: display Hit/Stand/Surrender buttons
			//TODO: if-else statements for option chosen
			printGameState(true);
		}
	}
	
	public static void handleFinalHands() {
		if(player.getTotal() > 21) {
			printGameState(false);
			//TODO: display "You lose." message
		}
		else {
			//hitDealerToSeventeen();
			
			//TODO: display "Revealing outcome..."
			printGameState(false);
			
			
		}
	}
	
	public static boolean playAgain() {
		return false;
	}
	
	public static void reshuffleShoeCheck() {
		if(shoe.getCardCount() < 26) {
			//TODO: display message
			shoe.shuffle(); //shuffle the shoe
		}
	}
	
	public static void printGameState(boolean hideDealerHand) {
		
	}
}

class Card {
	
}

class Player {
	static private Map<String,Integer> cardValues = Map.ofEntries(
	        Map.entry("ace",11),
	        Map.entry("king",10),
	        Map.entry("queen",10),
	        Map.entry("jack",10),
	        Map.entry("ten",10),
	        Map.entry("nine",9),
	        Map.entry("eight",8),
	        Map.entry("seven",7),
	        Map.entry("six",6),
	        Map.entry("five",5),
	        Map.entry("four",4),
	        Map.entry("three",3),
	        Map.entry("two",2),
	        Map.entry("one",1)
	    );
	
	private int total;
	private ArrayList<String> hand;
	
	Player() {
		total = 0;
		hand = new ArrayList<String>();
	}
	
	public int getTotal() {
		return total;
	}
	
	public void reset() {
		total = 0;
		hand.clear();
	}
	
	public void addToHand(String card) {
        hand.add(card);

        total = 0;
        int acesCount = 0;
        for (String cardName : hand) {
            total += cardValues.get(cardName);
            if (cardName.equals("ace")) {
                acesCount++;
            }
        }

        for (int i=0; i<acesCount; i++) {
            if (total < 22) {
                break;
            }
            total -= 10;
        }
    }
}

class Shoe {
	static private String[] cardTypes = {"ace", "king", "queen",
			"jack", "ten", "nine", "eight", "seven", "six",
			"five", "four", "three", "two", "one"};
	private HashMap<String,Integer> deck;
	private int cardCount;
	
	Shoe() {
		
	}
	
	public void shuffle() {
		deck.replaceAll((card, count) -> 4);
		cardCount = 52;
	}
	
	public int getCardCount() {
		return cardCount;
	}
	
	public String draw() {
		ArrayList<String> filteredDeck = getFilteredDeck();

        Random random = new Random();
        int randomInt = random.nextInt(filteredDeck.size());

        String selectedCard = filteredDeck.get(randomInt);

        deck.put(selectedCard, deck.get(selectedCard) - 1);

        cardCount--;

        return selectedCard;
	}
	
	private ArrayList<String> getFilteredDeck() {
        ArrayList<String> filteredDeck = new ArrayList<String>();

        for (String card : cardTypes) {
            if (deck.get(card) > 0) {
                filteredDeck.add(card);
            }
        }

        return filteredDeck;
    }
}
