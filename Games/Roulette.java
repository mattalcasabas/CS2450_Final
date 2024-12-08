import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Roulette {
    private int balance = 100;
    private int winnings = 0;
    private int betAmount = 0;
    private String selectedBet = "Straight Bet (35:1)";
    private int selectedNumber = -1;
    private String selectedColor = "";

    public Roulette() {
        // Main frame setup
        JFrame jfrm = new JFrame("Roulette Game");
        jfrm.setSize(800, 600);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLayout(new BorderLayout());

        // Left Panel: Betting Options
        JPanel bettingPanel = new JPanel();
        bettingPanel.setLayout(new BoxLayout(bettingPanel, BoxLayout.Y_AXIS));
        bettingPanel.setPreferredSize(new Dimension(300, 600));

        JLabel balanceLabel = new JLabel("Balance: $100");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel betTypeLabel = new JLabel("Select Bet Type:");
        JComboBox<String> betTypeComboBox = new JComboBox<>(new String[]{
                "Straight Bet (35:1)",
                "Split Bet (17:1)",
                "Street Bet (11:1)",
                "Corner Bet (8:1)",
                "Color Bet (1:1)",
                "Dozen Bet (2:1)",
                "Column Bet (2:1)"
        });

        JLabel betAmountLabel = new JLabel("Enter Bet Amount:");
        JTextField betAmountField = new JTextField();
        betAmountField.setMaximumSize(new Dimension(200, 30));

        JButton placeBetButton = new JButton("Place Bet");
        placeBetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton spinButton = new JButton("Spin");
        spinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinButton.setEnabled(false);

        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(balanceLabel);
        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(betTypeLabel);
        bettingPanel.add(betTypeComboBox);
        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(betAmountLabel);
        bettingPanel.add(betAmountField);
        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(placeBetButton);
        bettingPanel.add(Box.createVerticalStrut(10));
        bettingPanel.add(spinButton);

        // Right Panel: Roulette Table
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(12, 3, 5, 5));
        tablePanel.setPreferredSize(new Dimension(500, 600));

        // List of red numbers
        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

        // Adding number buttons to table
        for (int i = 0; i <= 36; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setPreferredSize(new Dimension(50, 50));

            // Set color based on number
            if (i == 0) {
                numberButton.setBackground(Color.GREEN); // 0 is green
            } else if (i == 37) {
                // For 00
                numberButton.setText("00");
                numberButton.setBackground(Color.GREEN); // 00 is green
            } else if (isRedNumber(i, redNumbers)) {
                numberButton.setBackground(Color.RED); // Red numbers
            } else {
                numberButton.setBackground(Color.BLACK); // Black numbers
                numberButton.setForeground(Color.WHITE); // Set text color to white for black buttons
            }

            numberButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedNumber = Integer.parseInt(numberButton.getText().equals("00") ? "37" : numberButton.getText());
                    selectedColor = numberButton.getBackground() == Color.RED ? "Red" : numberButton.getBackground() == Color.BLACK ? "Black" : "Green";
                }
            });

            tablePanel.add(numberButton);
        }

        // Bottom Panel: Result Display
        JPanel resultPanel = new JPanel(new GridLayout(2, 1));
        JLabel resultLabel = new JLabel("Result: ");
        JLabel outcomeLabel = new JLabel("Outcome: ");
        resultPanel.add(resultLabel);
        resultPanel.add(outcomeLabel);

        // Add panels to frame
        jfrm.add(bettingPanel, BorderLayout.WEST);
        jfrm.add(tablePanel, BorderLayout.CENTER);
        jfrm.add(resultPanel, BorderLayout.SOUTH);

        // Button logic for placing bet
        placeBetButton.addActionListener(e -> {
            try {
                String input = betAmountField.getText();
                if (input == null || input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(jfrm, "Please enter a bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                betAmount = Integer.parseInt(input);
                if (betAmount <= 0 || betAmount > balance) {
                    JOptionPane.showMessageDialog(jfrm, "Invalid bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                balance -= betAmount;
                balanceLabel.setText("Balance: $" + balance);
                selectedBet = (String) betTypeComboBox.getSelectedItem();

                spinButton.setEnabled(true);
                placeBetButton.setEnabled(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(jfrm, "Enter a valid numeric bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Spin button logic with progress bar
        spinButton.addActionListener(new ActionListener() {
            private Random random = new Random();

            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable spin button and show progress bar
                spinButton.setEnabled(false);
                JProgressBar progressBar = new JProgressBar(0, 100);
                progressBar.setStringPainted(true);
                progressBar.setPreferredSize(new Dimension(200, 30));
                bettingPanel.add(progressBar);
                bettingPanel.revalidate();
                bettingPanel.repaint();

                // Create a new thread to simulate the spinning process
                new Thread(() -> {
                    for (int i = 0; i <= 100; i++) {
                        try {
                            Thread.sleep(50); // Increment every 50ms to fill the bar in 5 seconds
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        progressBar.setValue(i);
                    }

                    // After 5 seconds, generate the result
                    int winningNumber = random.nextInt(37); // 0-36
                    String winningColor = random.nextBoolean() ? "Red" : "Black";
                    if (winningNumber == 0) winningColor = "Green";
                    if (winningNumber == 37) winningColor = "Green"; // For "00"

                    final String finalWinningColor = winningColor;  // Make winningColor final to use inside lambda

                    // Display result based on the bet type
                    if (selectedBet.equals("Straight Bet (35:1)") && selectedNumber == winningNumber) {
                        winnings = betAmount * 35;
                    } else if (selectedBet.equals("Color Bet (1:1)") && selectedColor.equals(finalWinningColor)) {
                        winnings = betAmount * 2;
                    }
                    // Additional bet types can be handled here

                    // Update UI after the spinning process
                    SwingUtilities.invokeLater(() -> {
                        if (winnings > 0) {
                            balance += winnings;
                            outcomeLabel.setText("Outcome: You won $" + winnings + "!");
                        } else {
                            outcomeLabel.setText("Outcome: You lost $" + betAmount + ".");
                        }

                        resultLabel.setText("Result: " + winningNumber + " " + finalWinningColor);
                        balanceLabel.setText("Balance: $" + balance);
                        bettingPanel.remove(progressBar); // Remove the progress bar
                        bettingPanel.revalidate();
                        bettingPanel.repaint();

                        spinButton.setEnabled(true); // Re-enable the spin button
                    });
                }).start();
            }
        });

        jfrm.setVisible(true);
    }

    // Helper function to check if a number is red
    private boolean isRedNumber(int number, int[] redNumbers) {
        for (int red : redNumbers) {
            if (number == red) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Roulette::new);
    }
}
