package Games;
import Shared.Player; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
    
    public class Slots extends JFrame {
        private JLabel slot1, slot2, slot3;
        private JButton spinButton;
        private JLabel balanceLabel;
        private int balance = 5; // Starting balance 

        //TODO: Get player coin data
        private String[] symbols = {"🍌", "🍋", "🔔", "🍎","💣"}; // Used emojis, could replace with pictures
        private Random random = new Random(); 
    
        public Slots() {
            setTitle("Slot Machine");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
    
            // Slots panel
            JPanel slotsPanel = new JPanel();
            slotsPanel.setLayout(new GridLayout(1, 3));
            slot1 = new JLabel("🍌", SwingConstants.CENTER);
            slot2 = new JLabel("🍋", SwingConstants.CENTER);
            slot3 = new JLabel("🔔", SwingConstants.CENTER);
            slot1.setFont(new Font("SansSerif", Font.BOLD, 40));
            slot2.setFont(new Font("SansSerif", Font.BOLD, 40));
            slot3.setFont(new Font("SansSerif", Font.BOLD, 40));

            slot1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            slot2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            slot3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));


            slotsPanel.add(slot1);
            slotsPanel.add(slot2);
            slotsPanel.add(slot3);
    
            // Control panel
            JPanel controlPanel = new JPanel();
            JPanel coinPanel = new JPanel();
            spinButton = new JButton("Spin");
            balanceLabel = new JLabel("Coins: $" + balance);
            controlPanel.add(spinButton);
            coinPanel.add(balanceLabel);
            // Add panels to frame
            add(slotsPanel, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.SOUTH);
            add(coinPanel, BorderLayout.WEST);
    
            // Button action
            spinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    spin();
                }
            });
    
            setVisible(true);
        }
    
        private void spin() {
            // Disable the spin button during the spin process
            spinButton.setEnabled(false);
    
            // using timer to cause "spin" since 
            Timer timer = new Timer(50, new ActionListener() {
                int count = 0;
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    updateSlots(randomSymbol(), randomSymbol(), randomSymbol()); //does all random, will make better chances later
    
                    count++;
                    if (count == 18) { // create spin animation for 18 times and then gives result 
                        ((Timer) e.getSource()).stop();
                        balance -= 1;
                        // Show spin results
                        String result1 = randomSymbol();
                        String result2 = randomSymbol();
                        String result3 = randomSymbol();
                        updateSlots(result1, result2, result3);
    
                        
                        if (result1.equals(result2) || result2.equals(result3)|| result1.equals(result3)) { //atleast one alike
                            balance += 3; // Win reward
                            
                            // TODO: find a way to delay message so they first see they won by themselves
                            // TODO: add more win casses, and do different win ratios
                            // TODO: make it so that its not pure chance (rig it but not rig it lol)
                            JOptionPane.showMessageDialog(Slots.this, "You win 2 coins!");
                        } else {
                            JOptionPane.showMessageDialog(Slots.this, "Oh no! try again!");
                        }
    
                        // Update balance
                        balanceLabel.setText("Coins: $" + balance);
    
                        // TODO: Placeholder for game, in actual game would be kick out function
                        if (balance <= 0) {
                            JOptionPane.showMessageDialog(Slots.this, "Game Over!");
                            balance = 0;
                            balanceLabel.setText("Coins: $" + balance);
                        }
                        // spin again after spin
                        spinButton.setEnabled(true);
                    }
                }
            });
    
            timer.start();
        }
    
        private void updateSlots(String result1, String result2, String result3) {
            slot1.setText(result1);
            slot2.setText(result2);
            slot3.setText(result3);
        }
    
        private String randomSymbol() {
            return symbols[random.nextInt(symbols.length)];
        }
    
        public static void main(String[] args) {
           
            new Slots();
        }
    }
    
