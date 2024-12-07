package Games;

import Shared.Player; // need to add game logic to support new player
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Dice implements ActionListener{
    JFrame diceScreen;
    JButton Bet, Roll, Exit;
    int bettedCoins, currCoins;
    private ImageIcon[] diceFaces;
    JLabel coinsCount, dice1, dice2;
    Random rand = new Random();
    
    public Dice(){
        diceScreen = new JFrame("Dice Game");
        diceScreen.setSize(500,500);
        //diceScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        diceScreen.setVisible(true);
        diceScreen.setLayout(new GridLayout(3,1));

        // load all dice images in an array to be randomized during gameplay
        diceFaces = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            ImageIcon original = new ImageIcon(getClass().getResource("/assets/DiceIcons/dice" + (i + 1) + ".png"));
            Image scaled = original.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            diceFaces[i] = new ImageIcon(scaled);
            System.out.println("added " + i);
        }

        // hardcoded set number of coins
        currCoins = 5;
        coinsCount = new JLabel("Coins " + currCoins, SwingConstants.RIGHT);

        // action buttons and their listeners
        Bet = new JButton("BET");
        Roll = new JButton("ROLL");
        Exit = new JButton("EXIT GAME");
        Bet.addActionListener(this);
        Roll.addActionListener(this);
        Exit.addActionListener(this);

        // page layout (add all components on the screen)
        // top panel for the coin count label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(coinsCount, BorderLayout.LINE_START);

        // center panel for dice images (default dice image is dice1)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        dice1 = new JLabel(diceFaces[0]);
        dice2 = new JLabel(diceFaces[0]); 
        centerPanel.add(dice1);
        centerPanel.add(dice2);

        // bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(Bet);
        bottomPanel.add(Roll);
        bottomPanel.add(Exit);

        diceScreen.add(topPanel);
        diceScreen.add(centerPanel);
        diceScreen.add(bottomPanel);
    }

    //actions for the buttons to work when pressed
	public void actionPerformed (ActionEvent ae){
       if(ae.getSource() == Bet){
        System.out.println("Bet button pressed");
        openBetting(diceScreen);
       }
       if(ae.getSource() == Roll){
        System.out.println("Roll button pressed");
        rollDice();
       }
       if(ae.getSource() == Exit){
        System.out.println("Exit button pressed");
        diceScreen.dispose();
       }
	}

    // method to simulate a dice roll on the UI
    private void rollDice(){
        int r1 = rand.nextInt(5);
        int r2 = rand.nextInt(5);
        dice1.setIcon(diceFaces[r1]);
        dice2.setIcon(diceFaces[r2]);
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

        SpinnerNumberModel spinModel = new SpinnerNumberModel(0, 0, currCoins, 1);
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
