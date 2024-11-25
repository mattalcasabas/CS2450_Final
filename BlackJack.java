package finalProject.codejava;

/*
 * This program is part of the overall "Casino"
 * final project for CS 2450-01 Fall 2024.
 * This program is the "Blackjack" game of the
 * "Casino." This program is written by
 * Moises Santander for Group 6.
 * */

import javax.swing.*;
import java.awt.*;

public class BlackJack {
	BlackJack() {
		//JFrame
		JFrame jfrm = new JFrame("Blackjack Game");
		jfrm.setSize(500, 500);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new FlowLayout());
		
		//create JButtons for the following actions:
		//hit, stand, surrender
		
		//JPanel to hold game contents
		JPanel blackjackPanel = new JPanel();
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
	}
}