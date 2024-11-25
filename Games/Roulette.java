import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Roulette 
{
    private int balance = 100;
    private int betAmount = 0;

    public Roulette() 
    {
        JFrame jfrm = new JFrame("Roulette Game");
        jfrm.setSize(400, 400);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLayout(new GridLayout(5, 1));

        JPanel numberPanel = new JPanel();
        JLabel numberLabel = new JLabel("Select Number (0-36):");
        JSpinner numberSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 36, 1));
        numberPanel.add(numberLabel);
        numberPanel.add(numberSpinner);
        jfrm.add(numberPanel);

        JPanel colorPanel = new JPanel();
        JLabel colorLabel = new JLabel("Select Color:");
        JComboBox<String> colorComboBox = new JComboBox<>(new String[]{"Red", "Black"});
        colorPanel.add(colorLabel);
        colorPanel.add(colorComboBox);
        jfrm.add(colorPanel);

        JPanel resultPanel = new JPanel();
        JLabel resultLabel = new JLabel("Result: ");
        JLabel outcomeLabel = new JLabel("Outcome: ");
        JLabel balanceLabel = new JLabel("Balance: $100");
        resultPanel.add(resultLabel);
        resultPanel.add(outcomeLabel);
        resultPanel.add(balanceLabel);
        jfrm.add(resultPanel);

        JPanel buttonPanel = new JPanel();
        JButton spinButton = new JButton("Spin");
        JButton betButton = new JButton("Bet");
        buttonPanel.add(betButton);
        buttonPanel.add(spinButton);
        jfrm.add(buttonPanel);

        spinButton.setEnabled(false);

        betButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    String input = JOptionPane.showInputDialog(jfrm, "Enter Bet Amount:", "Place Your Bet", JOptionPane.PLAIN_MESSAGE);

                    if (input == null || input.trim().isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(jfrm, "Bet canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    betAmount = Integer.parseInt(input);
                    if (betAmount <= 0 || betAmount > balance) 
                    {
                        JOptionPane.showMessageDialog(jfrm, "Invalid bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    balance -= betAmount;
                    balanceLabel.setText("Balance: $" + balance);

                    spinButton.setEnabled(true);
                    betButton.setEnabled(false);
                } 
                catch (NumberFormatException ex) 
                {
                    JOptionPane.showMessageDialog(jfrm, "Enter a valid numeric bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        spinButton.addActionListener(new ActionListener() 
        {
            private Random random = new Random();

            @Override
            public void actionPerformed(ActionEvent e) {
                int winningNumber = random.nextInt(37); // 0-36
                String winningColor = random.nextBoolean() ? "Red" : "Black";

                int selectedNumber = (int) numberSpinner.getValue();
                String selectedColor = (String) colorComboBox.getSelectedItem();

                boolean numberMatch = (selectedNumber == winningNumber);
                boolean colorMatch = (selectedColor.equals(winningColor));

                int winnings = 0;
                if (numberMatch) 
                {
                    winnings += betAmount * 35;
                }
                if (colorMatch) 
                {
                    winnings += betAmount + 1;
                }

                if (winnings > 0) 
                {
                    balance += winnings;
                    outcomeLabel.setText("Outcome: You won $" + winnings + "!");
                } 
                else 
                {
                    outcomeLabel.setText("Outcome: You lost $" + betAmount + ". :(");
                }

                resultLabel.setText("Result: " + winningNumber + " " + winningColor);
                balanceLabel.setText("Balance: $" + balance);

                betButton.setEnabled(true);
                spinButton.setEnabled(false);
            }
        });

        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Roulette::new);
    }
}
