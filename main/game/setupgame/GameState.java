package main.game.setupgame;

import java.util.ArrayList;
import java.util.Scanner;

// import main.game.gamelogic.turnlogic.ITurnLogic;
import main.game.piles.PileManager;
import main.game.players.IPlayer;

public class GameState {
    private String gameMode;
    private int numPlayers;
    private int numberOfBots;
    private ArrayList<IPlayer> players;
    private int startPlayer;
    private int currentPlayer;
    private PileManager pileManager;
    private ISettings settings;
    private Setup setup;
    private Scanner scanner;
    // private ITurnLogic turnLogic;
    

    public GameState(String gameMode, Scanner in) {
        this.players = new ArrayList<IPlayer>();
        this.currentPlayer = 0;
        this.pileManager = new PileManager();
        this.gameMode = gameMode;
        this.setup = new Setup(this);
        this.scanner = in;
    }

    public Scanner getScanner() {
        return scanner;
    }
    public void setSettings(ISettings settings) {
        this.settings = settings;
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

    public int getStartPlayer() {
        return startPlayer;
    }
    public void setStartPlayer(int startPlayer) {
        this.startPlayer = startPlayer;
        setCurrentPlayer(startPlayer);
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

    public ISettings getSettings() {
        return settings;
    }

    public Setup getSetup() {
        return setup;
    }

    public int availableMarketCards(){
        int marketCards = 0;
        for (int i = 0; i < pileManager.getPiles().size(); i++) {
            for (int j = 0; j < 2; j++) {
                if (pileManager.getPile(i).getMarketCard(j) != null) {
                    marketCards++;
                }
            }
        }
        return marketCards;
    }
    

}
