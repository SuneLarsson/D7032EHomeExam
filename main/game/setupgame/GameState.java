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
    private String gameMode;
    private String resourceName;
    private String pointName;
    private ArrayList<String> cardTypes;
    private ISettings settings;

    public GameState(String gameMode) {

        this.players = new ArrayList<IPlayer>();
        this.currentPlayer = 0;
        this.pileManager = new PileManager();
        this.gameMode = gameMode;
    }

    public void setSettings(ISettings settings) {
        this.settings = settings;
        this.cardTypes = settings.getCardTypes();
    }

    public int getMaxPlayers() {
        return settings.getMaxPlayers();
    }

    public int getMinPlayers() {
        return settings.getMinPlayers();
    }
    public int getNumPlayers() {
        
        return numPlayers;
    }
    
    public int getNumberOfBots() {
        return numberOfBots;
    }

    public void setNumberOfBots(int numberOfBots) {
        this.numberOfBots = numberOfBots;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
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

    public String getGameMode() {
        return gameMode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public ArrayList<String> getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(ArrayList<String> cardTypes) {
        this.cardTypes = cardTypes;
    }



}
