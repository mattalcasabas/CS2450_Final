package Shared;
import java.io.*;
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private int playerID;
    private int chips;
    private int wins;
    private int losses;

    // TODO: implement loading this data from a JSON file so the player can resume their session

    Player(int playerID, int chips) {
        this.playerID = playerID;
        this.chips = chips;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public int getChips() {
        return this.chips;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }

    // saving the player data to file
    public void saveToFile(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
            System.out.println("Saved player data as " + filePath);
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // load the player data from file
    public static Player loadFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // create a new player from the file
            return (Player) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error reading data: " + e.getMessage());
            return null;
        }
    }
}
