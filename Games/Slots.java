package Games;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Slots extends JFrame {
    private JLabel slot1, slot2, slot3;
    private JButton spinButton;
    private JLabel balanceLabel;
    private JTextArea infoTextArea; // Update: Added a text area for info
    private int balance = 5; // Starting balance

    private String[] symbols = {"🍌", "🍋", "🔔", "🍎", "💣", "💀"};
    private Random random = new Random();

    public Slots() {
        setTitle("Slot Machine");
        setSize(500, 500); 
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
        coinPanel.setLayout(new BorderLayout()); 
        spinButton = new JButton("Spin");
        balanceLabel = new JLabel("Coins: " + balance);

        // Information Text Area
        infoTextArea = new JTextArea(1, 20); 
        infoTextArea.setEditable(false);
        infoTextArea.setText("Rules\n" +
                " ☆ Each spin costs 1 coin.\n" +
                " ☆ Match 3 symbols = 5 coin Jackpot!\n" +
                " ☆ Match 2 symbols = 2 coins.\n" +
                " ☆ Match 1st and 3rd symbol = 1 coin.\n" ); 
        JScrollPane scrollPane = new JScrollPane(infoTextArea);

        controlPanel.add(spinButton);
        coinPanel.add(balanceLabel, BorderLayout.NORTH);
        coinPanel.add(scrollPane, BorderLayout.CENTER); 

        // Add panels to frame
        add(slotsPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(coinPanel, BorderLayout.WEST);

        // Button actions
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spin();
            }
        });

        setVisible(true);
    }

    private void spin() {
        if (balance <= 0) {
            JOptionPane.showMessageDialog(Slots.this, "You have no coins left!");
            return;
        }

        spinButton.setEnabled(false);

        Timer timer = new Timer(50, new ActionListener() {
            int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                updateSlots(randomSymbol(), randomSymbol(), randomSymbol());
                count++;

                if (count == 18) {
                    ((Timer) e.getSource()).stop();
                    balance -= 1;

                    String result1 = randomSymbol();
                    String result2 = randomSymbol();
                    String result3 = randomSymbol();
                    updateSlots(result1, result2, result3);

                    if (result1.equals(result2) && result2.equals(result3)) {
                        balance += 5;
                        JOptionPane.showMessageDialog(Slots.this, "Jackpot! You won 5 coins!");
                    } else if (result1.equals(result2) || result2.equals(result3)) {
                        balance += 2;
                        JOptionPane.showMessageDialog(Slots.this, "You won 2 coins!");
                    } else if(result1.equals(result3)){
                        balance += 1;
                        JOptionPane.showMessageDialog(Slots.this, "You won 1 coin!");
                    }
                    else {
                        JOptionPane.showMessageDialog(Slots.this, "Oh no! Try again!");
                    }

                    balanceLabel.setText("Coins: $" + balance);
                    spinButton.setEnabled(balance > 0);
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
