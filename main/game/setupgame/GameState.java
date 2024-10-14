package main.game.setupgame;

import java.util.ArrayList;

import main.game.piles.PileManager;
import main.game.players.IPlayer;

public class GameState {
    private int numPlayers;
    private int numberOfBots;
    private ArrayList<IPlayer> players;
    private int currentPlayer;
    private PileManager pileManager;
    private String gameName;

    public GameState(int numPlayers, int numberOfBots, String gameName) {
        this.numPlayers = numPlayers;
        this.numberOfBots = numberOfBots;
        this.players = new ArrayList<IPlayer>();
        this.currentPlayer = 0;
        this.pileManager = new PileManager();
    }

    public int getNumPlayers() {
        return numPlayers;
    }
    
    public int getNumberOfBots() {
        return numberOfBots;
    }

    public ArrayList<IPlayer> getPlayers() {
        return players;
    }
    public IPlayer getPlayer(int index) {
        return players.get(index);
    }

    public void addPlayer(IPlayer player) {
        players.add(player);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public PileManager getPileManager() {
        return pileManager;
    }

    public String getGameName() {
        return gameName;
    }


}
