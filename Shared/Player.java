package Shared;
public class Player {
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
}
