package Games;

import javax.swing.*;
import java.awt.*;

public class Dice {
    public Dice(){
        JFrame diceScreen = new JFrame("Dice Game");
        diceScreen.setSize(500,500);
        diceScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        diceScreen.setVisible(true);
        diceScreen.setLayout(new FlowLayout());
    }
}
