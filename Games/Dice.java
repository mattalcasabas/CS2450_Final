package Games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dice implements ActionListener{
    JFrame diceScreen;
    JButton Bet, Roll, Back;
    int bettedCoins;

    public Dice(){
        diceScreen = new JFrame("Dice Game");
        diceScreen.setSize(500,500);
        //diceScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        diceScreen.setVisible(true);
        diceScreen.setLayout(new FlowLayout());

        Bet = new JButton("BET");
        Roll = new JButton("ROLL");
        Back = new JButton("GO BACK");

        Bet.addActionListener(this);
        Roll.addActionListener(this);
        Back.addActionListener(this);

        diceScreen.add(Bet);
        diceScreen.add(Roll);
        diceScreen.add(Back);
    }

    //actions for the buttons to work when pressed
	public void actionPerformed (ActionEvent ae){
       if(ae.getSource() == Bet){
        System.out.println("Bet button pressed");
        openBetting(diceScreen);
       }
       if(ae.getSource() == Roll){
        System.out.println("Roll button pressed");
        // rolls the dice (need to use game logic for this)
       }
       if(ae.getSource() == Back){
        System.out.println("Back button pressed");
        // redirect to the original home page
       }
	}

    // method to show betting dialog box
    private void openBetting(JFrame parent) {
        JDialog betDialog = new JDialog();
        betDialog.setTitle("");
        betDialog.setSize(300, 150);
        betDialog.setLocationRelativeTo(parent);

        betDialog.setModal(true);

        JPanel betPanel = new JPanel();
        betPanel.setLayout(new FlowLayout());

        JLabel request = new JLabel("How many coins?");
        betPanel.add(request);

        // number of current coins hard-coded, need to implement that
        SpinnerNumberModel spinModel = new SpinnerNumberModel(0, 0, 10, 1);
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
