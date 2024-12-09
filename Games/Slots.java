package Games;

import Shared.Player; // Import the Player class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Slots extends JFrame {
    private JLabel slot1, slot2, slot3;
    private JButton spinButton;
    private JLabel balanceLabel;
    private JTextArea infoTextArea;
    private Player player; // Update: Add a Player object reference
    private String saveFilePath = "playerData.dat"; // File path for saving player data

    private String[] symbols = {"🍌", "🍋", "🔔", "🍎", "💣"};
    private Random random = new Random();

    public Slots(Player player) { // Update: Accept Player object in constructor
        this.player = player;

        setTitle("Slot Machine");
        setSize(500, 400);
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
        balanceLabel = new JLabel("Coins: $" + player.getChips()); // Update: Use player.getChips()

        // Information Text Area
        infoTextArea = new JTextArea(5, 20);
        infoTextArea.setEditable(false);
        infoTextArea.setText("Welcome to the Slot Machine!\n" +
                "☆ Each spin costs 1 coin.\n" +
                "☆ 3 matching symbols = Jackpot (5 coins).\n" +
                "☆ 2 matching symbols (in a row)= 2 coins.\n" +
                "☆ 2 matching symbols = 1 coin.\n" );
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
        if (player.getChips() <= 0) { // Update: Check player's chips
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

                if (count == 25) {
                    ((Timer) e.getSource()).stop();
                    player.setChips(player.getChips() - 1); // Update: Deduct chips from player

                    String result1 = randomSymbol();
                    String result2 = randomSymbol();
                    String result3 = randomSymbol();
                    updateSlots(result1, result2, result3);

                    // Win conditions
                    if (result1.equals(result2) && result2.equals(result3)) {
                        player.setChips(player.getChips() + 5); 
                        JOptionPane.showMessageDialog(Slots.this, "Jackpot! You win 5 coins!");
                    } else if (result1.equals(result2) || result2.equals(result3)) {
                        player.setChips(player.getChips() + 2); 
                        JOptionPane.showMessageDialog(Slots.this, "You win 2 coins!");
                    } else if (result1.equals(result3)) {
                        player.setChips(player.getChips() + 1); 
                        JOptionPane.showMessageDialog(Slots.this, "You win 1 coins!");
                    } else {
                        JOptionPane.showMessageDialog(Slots.this, "Oh no! Try again!");
                    }

                    balanceLabel.setText("Coins: $" + player.getChips());
                    spinButton.setEnabled(player.getChips() > 0);

                    // Save player data
                    player.saveToFile(saveFilePath); 
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
        Player player = Player.loadFromFile("playerData.dat"); // Update: Load player data
        if (player == null) {
            player = new Player(1, 5); // Create new player with default balance
        }
        new Slots(player);
    }
}
