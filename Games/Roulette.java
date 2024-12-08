import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Roulette 
{
    private int chips = 100;
    private int winnings = 0;
    private int betAmount = 0;

    private String selectedBet = "Straight Bet (35:1)";
    private int selectedNumber = -1;
    private String selectedColor = "";

    private boolean isBetPlaced = false;

    public Roulette() 
    {
        JFrame jfrm = new JFrame("Roulette Game");
        jfrm.setSize(900, 600);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLayout(new BorderLayout());

        // Left Panel: Betting Panel
        JPanel bettingPanel = new JPanel();
        bettingPanel.setLayout(new BoxLayout(bettingPanel, BoxLayout.Y_AXIS));
        bettingPanel.setPreferredSize(new Dimension(350, 600));

        JLabel chipsLabel = new JLabel("Chips: $100");
        chipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel betTypeLabel = new JLabel("Select Bet Type:");
        JComboBox<String> betTypeComboBox = new JComboBox<>(new String[]{
                "Straight Bet (35:1)", "Split Bet (17:1)", "Street Bet (11:1)", "Corner Bet (8:1)",
                "Color Bet (2:1)", "Dozen Bet (2:1)", "Column Bet (2:1)", "High or Low Bet (2:1)",
                "Odd or Even Bet (2:1)"
        });

        JLabel betAmountLabel = new JLabel("Enter Bet Amount:");
        JSpinner betAmountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, chips, 1));
        betAmountSpinner.setMaximumSize(new Dimension(200, 30));

        JButton placeBetButton = new JButton("Place Bet");
        placeBetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton spinButton = new JButton("Spin");
        spinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinButton.setEnabled(false);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(200, 30));

        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(chipsLabel);

        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(betTypeLabel);
        bettingPanel.add(betTypeComboBox);

        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(betAmountLabel);
        bettingPanel.add(betAmountSpinner);

        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(placeBetButton);

        bettingPanel.add(Box.createVerticalStrut(10));
        bettingPanel.add(spinButton);

        bettingPanel.add(Box.createVerticalStrut(20));
        bettingPanel.add(progressBar);

        // Center Panel: Roulette Table
        JPanel tablePanel = new JPanel(new GridLayout(4, 10, 5, 5));
        tablePanel.setPreferredSize(new Dimension(550, 600));

        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

        for (int i = 0; i <= 36; i++) 
        {
            JButton numberButton = createNumberButton(i, redNumbers);
            tablePanel.add(numberButton);
        }

        JButton numberButton00 = new JButton("00");
        numberButton00.setBackground(Color.GREEN);
        numberButton00.setForeground(Color.BLACK);
        numberButton00.addActionListener(e -> {
            selectedNumber = 37; // "00" as 37
            selectedColor = "Green";
        });
        tablePanel.add(numberButton00);

        // Bottom Panel: Result Panel
        JPanel resultPanel = new JPanel(new GridLayout(2, 1));
        JLabel resultLabel = new JLabel("Result: ");
        JLabel outcomeLabel = new JLabel("Outcome: ");
        resultPanel.add(resultLabel);
        resultPanel.add(outcomeLabel);

        jfrm.add(bettingPanel, BorderLayout.WEST);
        jfrm.add(tablePanel, BorderLayout.CENTER);
        jfrm.add(resultPanel, BorderLayout.SOUTH);

        placeBetButton.addActionListener(e -> handlePlaceBet(betAmountSpinner, chipsLabel, betTypeComboBox, placeBetButton, spinButton));

        spinButton.addActionListener(new ActionListener() 
        {
            private final Random random = new Random();

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (!isBetPlaced) 
                {
                    JOptionPane.showMessageDialog(null, "Place a bet!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                spinButton.setEnabled(false);

                Thread spinThread = new Thread(() -> {
                    for (int i = 0; i <= 100; i++) {
                        progressBar.setValue(i);
                        try 
                        {
                            Thread.sleep(50);
                        } 
                        catch (InterruptedException ex) 
                        {
                            ex.printStackTrace();
                        }
                    }

                    int winningNumber = random.nextInt(38);
                    String winningColor = determineWinningColor(winningNumber, redNumbers);

                    calculateWinnings(winningNumber, winningColor, resultLabel, outcomeLabel, chipsLabel);
                    progressBar.setValue(0);
                    spinButton.setEnabled(true);
                    placeBetButton.setEnabled(true);
                    isBetPlaced = false;
                });
                spinThread.start();
            }
        });

        jfrm.setVisible(true);
    }

    private JButton createNumberButton(int number, int[] redNumbers) {
        JButton numberButton = new JButton(String.valueOf(number));
        numberButton.setBackground(isRedNumber(number, redNumbers) ? Color.RED : (number == 0 ? Color.GREEN : Color.BLACK));
        numberButton.setForeground(number == 0 || isRedNumber(number, redNumbers) ? Color.BLACK : Color.WHITE);
        numberButton.addActionListener(e -> {
            selectedNumber = number;
            selectedColor = numberButton.getBackground() == Color.RED ? "Red" : numberButton.getBackground() == Color.BLACK ? "Black" : "Green";
        });
        return numberButton;
    }

    private boolean isRedNumber(int number, int[] redNumbers) 
    {
        for (int red : redNumbers) 
        {
            if (number == red) return true;
        }
        return false;
    }

    private String determineWinningColor(int winningNumber, int[] redNumbers) 
    {
        if (winningNumber == 0 || winningNumber == 37) return "Green";
        return isRedNumber(winningNumber, redNumbers) ? "Red" : "Black";
    }

    private void handlePlaceBet(JSpinner betAmountSpinner, JLabel chipsLabel, JComboBox<String> betTypeComboBox, JButton placeBetButton, JButton spinButton) {
        betAmount = (int) betAmountSpinner.getValue();
        if (betAmount <= 0 || betAmount > chips) 
        {
            JOptionPane.showMessageDialog(null, "Invalid bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        chips -= betAmount;
        chipsLabel.setText("Chips: $" + chips);

        selectedBet = (String) betTypeComboBox.getSelectedItem();

        spinButton.setEnabled(true);
        placeBetButton.setEnabled(false);

        isBetPlaced = true;
    }

    private void calculateWinnings(int winningNumber, String winningColor, JLabel resultLabel, JLabel outcomeLabel, JLabel chipsLabel) {
        winnings = 0;
    
        if (selectedBet.equals("Straight Bet (35:1)") && selectedNumber == winningNumber) 
        {
            winnings = betAmount * 35;
        } 
        else if (selectedBet.equals("Split Bet (17:1)") && (selectedNumber == winningNumber || Math.abs(selectedNumber - winningNumber) == 1)) 
        {
            winnings = betAmount * 17;
        } 
        else if (selectedBet.equals("Street Bet (11:1)") && ((winningNumber - 1) / 3 == (selectedNumber - 1) / 3)) 
        {
            winnings = betAmount * 11;
        } 
        else if (selectedBet.equals("Corner Bet (8:1)") && isCornerBetWin(selectedNumber, winningNumber)) 
        {
            winnings = betAmount * 8;
        } 
        else if (selectedBet.equals("Five Number Bet (6:1)") && (winningNumber == 0 || winningNumber == 37 || winningNumber == 1 || winningNumber == 2 || winningNumber == 3)) 
        {
            winnings = betAmount * 6;
        } 
        else if (selectedBet.equals("Line Bet (5:1)") && isLineBetWin(selectedNumber, winningNumber)) 
        {
            winnings = betAmount * 5;
        } 
        else if (selectedBet.equals("Dozen Bet (2:1)") && isDozenBetWin(selectedNumber, winningNumber)) 
        {
            winnings = betAmount * 2;
        } 
        else if (selectedBet.equals("Column Bet (2:1)") && winningNumber % 3 == selectedNumber % 3) 
        {
            winnings = betAmount * 2;
        } 
        else if (selectedBet.equals("Low Bet (2:1)") && winningNumber >= 1 && winningNumber <= 18) 
        {
            winnings = betAmount * 2;
        } 
        else if (selectedBet.equals("High Bet (2:1)") && winningNumber >= 19 && winningNumber <= 36) 
        {
            winnings = betAmount * 2;
        } 
        else if (selectedBet.equals("Color Bet (2:1)") && selectedColor.equals(winningColor)) 
        {
            winnings = betAmount * 2;
        } 
        else if (selectedBet.equals("Odd or Even Bet (2:1)") && ((winningNumber % 2 == 0 && selectedNumber == 0) || (winningNumber % 2 != 0 && selectedNumber == 1))) 
        {
            winnings = betAmount * 2;
        }

        if (winnings > 0) {
            chips += winnings;
            outcomeLabel.setText("Outcome: You won $" + winnings + "!");
        } else {
            outcomeLabel.setText("Outcome: You lost $" + betAmount + " :(");
        }
        resultLabel.setText("Result: " + (winningNumber == 37 ? "00" : winningNumber) + " " + winningColor);
        chipsLabel.setText("Chips: $" + chips);
    }

    private boolean isCornerBetWin(int selectedNumber, int winningNumber) {
        int[][] cornerGroups = {
            {1, 2, 4, 5}, {2, 3, 5, 6}, {4, 5, 7, 8}, {5, 6, 8, 9},
            {7, 8, 10, 11}, {8, 9, 11, 12}, {10, 11, 13, 14}, {11, 12, 14, 15},
            {13, 14, 16, 17}, {14, 15, 17, 18}, {16, 17, 19, 20}, {17, 18, 20, 21},
            {19, 20, 22, 23}, {20, 21, 23, 24}, {22, 23, 25, 26}, {23, 24, 26, 27},
            {25, 26, 28, 29}, {26, 27, 29, 30}, {28, 29, 31, 32}, {29, 30, 32, 33},
            {31, 32, 34, 35}, {32, 33, 35, 36}
        };

        for (int[] group : cornerGroups) 
        {
            for (int num : group) 
            {
                if (num == selectedNumber) 
                {
                    for (int num2 : group) 
                    {
                        if (num2 == winningNumber) return true;
                    }
                }
            }
        }
        return false;
    }    

    private boolean isLineBetWin(int selectedNumber, int winningNumber) 
    {
        int startOfLine = (selectedNumber - 1) / 6 * 6 + 1;
        return winningNumber >= startOfLine && winningNumber < startOfLine + 6;
    }

    private boolean isDozenBetWin(int selectedNumber, int winningNumber) 
    {
        if (selectedNumber == 1 && winningNumber >= 1 && winningNumber <= 12) return true;
        else if (selectedNumber == 2 && winningNumber >= 13 && winningNumber <= 24) return true;
        else if (selectedNumber == 3 && winningNumber >= 25 && winningNumber <= 36) return true;
        return false;
    }

    public static void main(String[] args) 
    {
        new Roulette();
    }
}