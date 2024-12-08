package Games;

import Shared.Player; // need to add game logic to support new player
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Dice implements ActionListener{
    JFrame diceScreen;
    JButton Bet, Roll, Exit, Start;
    int bettedCoins, currCoins, dealerRoll, userRoll;
    private ImageIcon[] diceFaces;
    JLabel coinsCount, dice1, dice2, display, dealer1, dealer2;
    Random rand = new Random();
    
    public Dice(){
        diceScreen = new JFrame("Dice Game");
        diceScreen.setSize(500,500);
        diceScreen.setVisible(true);
        diceScreen.setLayout(new BorderLayout());

        // hardcoded set number of coins
        currCoins = 5;
        coinsCount = new JLabel("Coins " + currCoins, SwingConstants.RIGHT);

        // load all dice images in an array to be randomized during gameplay
        diceFaces = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            ImageIcon original = new ImageIcon(getClass().getResource("/assets/DiceIcons/dice" + (i + 1) + ".png"));
            Image scaled = original.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            diceFaces[i] = new ImageIcon(scaled);
            System.out.println("added " + i);
        }
    
        // action buttons and their listeners
        Start = new JButton("START GAME");
        Start.setVisible(true);
        Bet = new JButton("BET");
        Bet.setVisible(false);
        Roll = new JButton("ROLL");
        Roll.setVisible(false);
        Exit = new JButton("EXIT GAME");
        Exit.setVisible(false);
        Start.addActionListener(this);
        Bet.addActionListener(this);
        Roll.addActionListener(this);
        Exit.addActionListener(this);

        // page layout (add all components on the screen)
        // top panel for the coin count label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(coinsCount, BorderLayout.LINE_START);

        // center panel for dice images (default dice image is dice1)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        dealer1 = new JLabel(diceFaces[0]);
        dealer2 = new JLabel(diceFaces[0]);
        JPanel dealers = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dealers.add(dealer1);
        dealers.add(dealer2);

        display = new JLabel("Make a bet and roll a higher number than the Dealer to win!");
        JPanel displayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        displayPanel.add(display);
        displayPanel.add(Start);

        dice1 = new JLabel(diceFaces[0]);
        dice2 = new JLabel(diceFaces[0]); 
        JPanel dice = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dice.add(dice1);
        dice.add(dice2);

        centerPanel.add(dealers);
        centerPanel.add(displayPanel);
        centerPanel.add(dice);

        // bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(Bet);
        bottomPanel.add(Roll);
        bottomPanel.add(Exit);

        diceScreen.add(topPanel, BorderLayout.NORTH);
        diceScreen.add(centerPanel, BorderLayout.CENTER);
        diceScreen.add(bottomPanel, BorderLayout.SOUTH);
    }

    //actions for the buttons to work when pressed
	public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == Start){
            Start.setVisible(false);
            Bet.setVisible(true);
            Roll.setVisible(true);
            Exit.setVisible(true);
            dealerRoll = rollDice(dealer1, dealer2);
            display.setText("The dealer rolled " + dealerRoll + ". Please make a bet.");
        }
        if(ae.getSource() == Bet){
            System.out.println("Bet button pressed");
            openBetting(diceScreen);
            display.setText("You betted " + bettedCoins + " coins. Make a roll.");
        }
        if(ae.getSource() == Roll){
            System.out.println("Roll button pressed");
            if(bettedCoins == 0){
                JOptionPane.showMessageDialog(diceScreen, "Please make a bet!", "Roll Dice", JOptionPane.ERROR_MESSAGE);
            }else{
                userRoll = rollDice(dice1, dice2);
                score();
            }
        }
        if(ae.getSource() == Exit){
            System.out.println("Exit button pressed");
            diceScreen.dispose();
        }
	}

    private int rollDice(JLabel dice1, JLabel dice2){
        int r1 = rand.nextInt(5);
        int r2 = rand.nextInt(5);
        dice1.setIcon(diceFaces[r1]);
        dice2.setIcon(diceFaces[r2]);
        return r1+r2+2; // roll total will be saved
    }

    // calculates the score and adds coins
    private void score(){
        if(userRoll == dealerRoll){
            display.setText("Tie! You lost/gained no coins.");
             return;
        }
        else if(userRoll > dealerRoll){
            display.setText("You rolled " + userRoll + " and won! You gained " + bettedCoins + " coins!");
            currCoins += bettedCoins;
            coinsCount.setText("Coins " + currCoins);
        }
        else{
            display.setText("You rolled " + userRoll + " and lost. You lose " + bettedCoins + " coins :(");
            currCoins -= bettedCoins;
            coinsCount.setText("Coins " + currCoins);
        }
        bettedCoins = 0;
    }

    // method to show betting dialog box
    private void openBetting(JFrame parent) {
        JDialog betDialog = new JDialog();
        betDialog.setTitle("");
        betDialog.setSize(300, 100);
        betDialog.setLocationRelativeTo(parent);

        betDialog.setModal(true);

        JPanel betPanel = new JPanel();
        betPanel.setLayout(new FlowLayout());

        JLabel request = new JLabel("How many coins?");
        betPanel.add(request);

        SpinnerNumberModel spinModel = new SpinnerNumberModel(1, 1, currCoins, 1);
        JSpinner jspn = new JSpinner(spinModel);
        betPanel.add(jspn);

        JButton bet = new JButton("Make Bet");
        bet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == bet){
                    bettedCoins = (int) jspn.getValue();
                    System.out.println("betted coins = " + bettedCoins);
                    betDialog.dispose();
                }
            }
        });
        betPanel.add(bet);
        betDialog.add(betPanel);
        betDialog.setVisible(true);
    }

}
