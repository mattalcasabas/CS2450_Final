import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
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

    JFrame homeScreen; 
    JMenuBar mb;
    JPanel homePanel;
    JMenu fileMenu, viewMenu, debugMenu;
    // JMenuItems for File menu
    JMenuItem newPlayerMenuItem, openPlayerMenuItem, exitMenuItem;
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
        // we'll find a better layout for this
        homeScreen.setLayout(new CardLayout());

        makeMenuBar();
        homePanel = makeHomePanel();
        homeScreen.add(homePanel);
        // need to open just welcome screen first and then makeHomeScreen() is called when the player is selected so 2 windows don't open
        // makeWelcomeScreen();

    }

    public void makeMenuBar() {
        // create the menu bar and add to top of the window
        mb = new JMenuBar();
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");
        debugMenu = new JMenu("Debug");
        homeScreen.setJMenuBar(mb);
        // add menu items for File menu
        newPlayerMenuItem = new JMenuItem("New player...");
        fileMenu.add(newPlayerMenuItem);
        newPlayerMenuItem.addActionListener(this);
        openPlayerMenuItem = new JMenuItem("Open player...");
        fileMenu.add(openPlayerMenuItem);
        openPlayerMenuItem.addActionListener(this);
        fileMenu.addSeparator();
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(this);
        // add menu items for View menu
        returnHomeMenuItem = new JMenuItem("Return to home screen...");
        viewMenu.add(returnHomeMenuItem);
        returnHomeMenuItem.addActionListener(this);
        // add debug items for Debug menu
        setChipsMenuItem = new JMenuItem("Set player chips...");
        debugMenu.add(setChipsMenuItem);
        setChipsMenuItem.addActionListener(this);
        setWinsMenuItem = new JMenuItem("Set player wins...");
        debugMenu.add(setWinsMenuItem);
        setWinsMenuItem.addActionListener(this);
        setLossesMenuItem = new JMenuItem("Set player losses...");
        debugMenu.add(setLossesMenuItem);
        setLossesMenuItem.addActionListener(this);
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
        JLabel playerNameLabel = new JLabel("Player: John Doe");
        JLabel chipsLabel = new JLabel("Chips: 1000");
        JLabel winsLabel = new JLabel("Wins: 5");
        JLabel lossesLabel = new JLabel("Losses: 2");
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
            }
        });
        games.add(playBlackjackButton);

        JButton playRouletteButton = new JButton("Roulette", rouletteIcon);
        playRouletteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        playRouletteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        playRouletteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Roulette button pressed");
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
            }
        });
        games.add(playSlotsButton);
        // add games panel to home panel
        home.add(games, BorderLayout.CENTER);

        return home;
    }

    public void makeWelcomeScreen() {
        JFrame welcomeScreen = new JFrame("Welcome!");
        welcomeScreen.setSize(200,200);
        welcomeScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomeScreen.setVisible(true);
        welcomeScreen.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("<html><h1>Welcome!</h1></html");
        welcomeScreen.add(welcomeLabel);
        JButton newPlayerButton = new JButton("New player");
        newPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WelcomeScreen: New player button pressed");
                // TODO: generate a new Casino session for a new Player

            }
        });
        welcomeScreen.add(newPlayerButton);
        JButton openPlayerButton = new JButton("Use existing player");
        openPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WelcomeScreen: Open player button pressed");
                // TODO: spawn "open file..." dialog
                // player can then open their player file JSON from the dialog
            }
        });
        welcomeScreen.add(openPlayerButton);
        // TODO: when new player added or existing player opened, destroy the welcome screen window
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
