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
    JTabbedPane tp;
    GroupLayout gl;
    JMenu fileMenu, debugMenu;
    // JMenuItems for File menu
    JMenuItem newPlayerMenuItem, openPlayerMenuItem, exitMenuItem;
    // JMenuItems for Debug menu
    JMenuItem setChipsMenuItem, setWinsMenuItem, setLossesMenuItem;

    CasinoWindow() {
        homeScreen = new JFrame("Home");
        homeScreen.setSize(500,500);
        homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeScreen.setVisible(true);
        // we'll find a better layout for this
        homeScreen.setLayout(new FlowLayout());

        makeMenuBar();
        makeHomeScreen();
        makeWelcomeScreen();

    }

    public void makeMenuBar() {
        // create the menu bar and add to top of the window
        mb = new JMenuBar();
        fileMenu = new JMenu("File");
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
        mb.add(debugMenu);
        homeScreen.setJMenuBar(mb);
    }

    public void makeHomeScreen() {
        JButton playBlackjackButton = new JButton("Play Blackjack...");
        playBlackjackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Blackjack button pressed");
            }
        });
        homeScreen.add(playBlackjackButton);
        JButton playRouletteButton = new JButton("Play Roulette...");
        playRouletteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Roulette button pressed");
            }
        });
        homeScreen.add(playRouletteButton);
        JButton playDiceButton = new JButton("Play Dice...");
        playDiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Dice button pressed");
                new Dice();
            }
        });
        homeScreen.add(playDiceButton);
        JButton playSlotsButton = new JButton("Play Slots...");
        playSlotsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("HomeScreen: Slots button pressed");
            }
        });
        homeScreen.add(playSlotsButton);
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
