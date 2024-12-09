import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import Shared.Player;
import Games.*;

public class CasinoWindow implements ActionListener {
    /*
     * this creates and manages the Casino GUI
     * all GUI management should be done here ideally, including:
     *  - displaying main menu
     *  - displaying games list (blackjack, roulette, craps, etc?)
     *      - obviously we can add/remove games according to time
     * to keep things organized, each game should be on its own pane, and each pane should be its own file
     *  - e.g. Blackjack.java, Roulette.java, Craps.java, etc.
     *      - should we break up game logic and game GUI into different files?
     */

    Player player = null;
    JFrame homeScreen; 
    JMenuBar mb;
    CardLayout cl;
    JPanel mainPanel, homePanel, welcomePanel;
    JMenu fileMenu, viewMenu, debugMenu;
    Runnable updateStats;
    // JMenuItems for File menu
    JMenuItem savePlayerMenuItem, openPlayerMenuItem, exitMenuItem;
    // JMenuItems for Debug menu
    JMenuItem setChipsMenuItem, setWinsMenuItem, setLossesMenuItem;
    // JMenuItems for View menu
    JMenuItem returnHomeMenuItem;
    // icons for home screen
    ImageIcon blackjackIcon = new ImageIcon("assets/HomeScreenIcons/blackjack-scaled.png"),
              diceIcon = new ImageIcon("assets/HomeScreenIcons/dice-scaled.png"),
              rouletteIcon = new ImageIcon("assets/HomeScreenIcons/roulette-scaled.png"),
              slotsIcon = new ImageIcon("assets/HomeScreenIcons/slots-scaled.png");

    CasinoWindow() {
        homeScreen = new JFrame("Home");
        homeScreen.setSize(500,500);
        homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeScreen.setVisible(true);
        cl = new CardLayout();
        mainPanel = new JPanel(cl);

        // add the welcomePanel and homePanel to the mainPanel
        welcomePanel = makeWelcomeScreen();
        mainPanel.add(welcomePanel, "Welcome");
        homePanel = makeHomePanel();
        mainPanel.add(homePanel, "Home");

        homeScreen.add(mainPanel);

        makeMenuBar();
        

        // check to see if the Player has already been initialized

        // homePanel = makeHomePanel();
        // homeScreen.add(homePanel);
        // need to open just welcome screen first and then makeHomeScreen() is called when the player is selected so 2 windows don't open
        // makeWelcomeScreen();

    }

    private void switchScreens(String screenName) {
        cl.show(mainPanel, screenName);
    }

    public void makeMenuBar() {
        // create the menu bar and add to top of the window
        mb = new JMenuBar();
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");
        debugMenu = new JMenu("Debug");
        homeScreen.setJMenuBar(mb);
        // add menu items for File menu
        savePlayerMenuItem = new JMenuItem("Save player...");
        fileMenu.add(savePlayerMenuItem);
        savePlayerMenuItem.addActionListener(new ActionListener() {
            // dialog to save current player status
            public void actionPerformed(ActionEvent e) {
                savePlayerToFile();
            }
         });
        openPlayerMenuItem = new JMenuItem("Open player...");
        fileMenu.add(openPlayerMenuItem);
        openPlayerMenuItem.addActionListener(new ActionListener() {
            // dialog to open a player from file
            public void actionPerformed(ActionEvent e) {
                loadPlayerFromFile();
            }
        });
        fileMenu.addSeparator();
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // prompt player to save data
                savePlayerToFile();
                // exit the program
                System.exit(0);
            }
        });
        // add menu items for View menu
        returnHomeMenuItem = new JMenuItem("Return to home screen...");
        viewMenu.add(returnHomeMenuItem);
        returnHomeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchScreens("Home");
            }
        });
        // add debug items for Debug menu
        setChipsMenuItem = new JMenuItem("Set player chips...");
        debugMenu.add(setChipsMenuItem);
        setChipsMenuItem.addActionListener(new ActionListener() {
            // debug: add 100 player chips
            public void actionPerformed(ActionEvent e) {
                String playerChipsString = JOptionPane.showInputDialog(homeScreen, "Set player chips:");
                try {
                    player.setChips(Integer.parseInt(playerChipsString.trim()));
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(homeScreen, "Invalid player chips!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                updateStats.run();
            }
        });
        setWinsMenuItem = new JMenuItem("Add player win...");
        debugMenu.add(setWinsMenuItem);
        setWinsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.addWin();
                updateStats.run();
            }
        });
        setLossesMenuItem = new JMenuItem("Add player loss...");
        debugMenu.add(setLossesMenuItem);
        setLossesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.addLoss();
                updateStats.run();
            }
        });
        // add menus to menu bar
        mb.add(fileMenu);
        mb.add(viewMenu);
        mb.add(debugMenu);
        homeScreen.setJMenuBar(mb);
    }

    public JPanel makeHomePanel() {
        JPanel home = new JPanel();
        // setting border layout for top/center alignment
        home.setLayout(new BorderLayout()); 
        
        // panel for player stats
        JPanel stats = new JPanel();
        // align text to the left
        stats.setLayout(new FlowLayout(FlowLayout.LEFT));
        // add padding
        stats.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // labels for stats
        JLabel playerNameLabel = new JLabel();
        JLabel chipsLabel = new JLabel();
        JLabel winsLabel = new JLabel();
        JLabel lossesLabel = new JLabel();

        // runnable to update stats dynamically
        updateStats = () -> {
            if (player != null) {
                playerNameLabel.setText("Player: " + player.getPlayerID());
                chipsLabel.setText("Chips: " + player.getChips());
                winsLabel.setText("Wins: " + player.getWins());
                lossesLabel.setText("Losses: " + player.getLosses());
            }
        };
        // initialize with current stats
        updateStats.run();

        // add stats to the stats panel
        stats.add(playerNameLabel);
        // add space between stats
        stats.add(Box.createHorizontalStrut(20));
        stats.add(chipsLabel);
        stats.add(Box.createHorizontalStrut(20));
        stats.add(winsLabel);
        stats.add(Box.createHorizontalStrut(20));
        stats.add(lossesLabel);
        // add stats panel to home panel
        home.add(stats, BorderLayout.NORTH);

        // panel for game selection
        JPanel games = new JPanel();
        games.setLayout(new GridLayout(2, 2, 10, 10));
        JButton playBlackjackButton = new JButton("Blackjack", blackjackIcon);
        playBlackjackButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        playBlackjackButton.setHorizontalTextPosition(SwingConstants.CENTER);
        playBlackjackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Blackjack button pressed");
                new BlackJack();
            }
        });
        games.add(playBlackjackButton);

        JButton playRouletteButton = new JButton("Roulette", rouletteIcon);
        playRouletteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        playRouletteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        playRouletteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Roulette button pressed");
                new Roulette();
            }
        });
        games.add(playRouletteButton);

        JButton playDiceButton = new JButton("Dice", diceIcon);
        playDiceButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        playDiceButton.setHorizontalTextPosition(SwingConstants.CENTER);
        playDiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Dice button pressed");
                // need to make it so the game opens in the same window instead of a new jframe (maybe use cardlayout?)
                new Dice();
            }
        });
        games.add(playDiceButton);

        JButton playSlotsButton = new JButton("Slots", slotsIcon);
        playSlotsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        playSlotsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        playSlotsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Slots button pressed");
                new Slots();
            }
        });
        games.add(playSlotsButton);
        // add games panel to home panel
        home.add(games, BorderLayout.CENTER);

        return home;
    }

    public JPanel makeWelcomeScreen() {
        JPanel welcomeScreen = new JPanel();
        welcomeScreen.setLayout(new GridLayout(3, 0));

        JLabel welcomeLabel = new JLabel("<html><h1>Welcome!</h1></html");
        welcomeScreen.add(welcomeLabel);
        JButton newPlayerButton = new JButton("New player");
        newPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WelcomeScreen: New player button pressed");

                // create the new Player
                player = createNewPlayer();
                JOptionPane.showMessageDialog(homeScreen, "New player created!");
                switchScreens("Home");
            }
        });
        welcomeScreen.add(newPlayerButton);

        JButton openPlayerButton = new JButton("Use existing player");
        openPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WelcomeScreen: Open player button pressed");
                loadPlayerFromFile();

                if (player != null) {
                    switchScreens("Home");
                }
            }
        });
        welcomeScreen.add(openPlayerButton);
        // TODO: when new player added or existing player opened, destroy the welcome screen window
        return welcomeScreen;
    }

    private Player createNewPlayer() {
        // prompt for new player details
        String playerIDString = JOptionPane.showInputDialog(homeScreen, "Enter Player ID:");
        if (playerIDString == null || playerIDString.trim().isEmpty()) {
            // if player id is null or empty, return error message
            JOptionPane.showMessageDialog(homeScreen, "Player ID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        int playerID = 0;
        try {
            // try to parse the playerID from the entered string
            playerID = Integer.parseInt(playerIDString.trim());
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(homeScreen, "Invalid Player ID, using default of 0", "Error", JOptionPane.ERROR_MESSAGE);
        }
        // start the player with 100 chips by default
        int startingChips = 100;
        try {
            String chipsString = JOptionPane.showInputDialog(homeScreen, "Enter starting chips (default: 100):");
            if (chipsString != null && !chipsString.trim().isEmpty()) {
                startingChips = Integer.parseInt(chipsString.trim());
            }
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(homeScreen, "Invalid chip value, using default of 100 chips.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return new Player(playerID, startingChips);        
    }

    // create a file save dialog to save the player's current state as a .dat file
    private void savePlayerToFile() {
        if (player == null) {
            JOptionPane.showMessageDialog(homeScreen, "No player data to save!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Player File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Player Data Files", "dat"));

        int userSelection = fileChooser.showSaveDialog(homeScreen);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().endsWith(".dat")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".dat");
            }

            player.saveToFile(fileToSave.getAbsolutePath());
            JOptionPane.showMessageDialog(homeScreen, "Player data saved successfully!");
        }
    }

    // create a file load dialog to load the player's currents state as a .dat file
    private void loadPlayerFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Player File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Player Data Files", "dat"));

        int userSelection = fileChooser.showOpenDialog(homeScreen);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();

            Player loadedPlayer = Player.loadFromFile(fileToOpen.getAbsolutePath());
            if (loadedPlayer != null) {
                player = loadedPlayer;
                JOptionPane.showMessageDialog(homeScreen, "Player data loaded successfully!");
                System.out.println("Player ID: " + player.getPlayerID());
                System.out.println("Chips: " + player.getChips());
                System.out.println("Wins: " + player.getWins());
                System.out.println("Losses: " + player.getLosses());
            } 
            else {
                JOptionPane.showMessageDialog(homeScreen, "Failed to load player data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateStats.run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
