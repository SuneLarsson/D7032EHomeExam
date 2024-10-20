package main.game.game.gameState;

import java.util.ArrayList;
import java.util.Scanner;

import main.game.game.setupgame.FactorySetup;
import main.game.piles.PileManager;
import main.game.players.IPlayer;
import main.game.settings.ISettings;


/**
 * The GameState class represents the current state of the game, including the game mode,
 * number of players, number of bots, players, start player, current player, pile manager,
 * settings, setup, and scanner for input.
 */
public class GameState {
    private String gameMode;
    private int numPlayers;
    private int numberOfBots;
    private ArrayList<IPlayer> players;
    private int startPlayer;
    private int currentPlayer;
    private PileManager pileManager;
    private ISettings settings;
    private FactorySetup setup;
    private Scanner scanner;
    // private ITurnLogic turnLogic;
    
    /**
     * Constructor that creates the current state of the game.
     * @param gameMode The game mode of the game.
     * @param in The scanner to read input from the console.
     */
    public GameState(String gameMode, Scanner in) {
        this.players = new ArrayList<IPlayer>();
        this.currentPlayer = 0;
        this.pileManager = new PileManager();
        this.gameMode = gameMode;
        this.setup = new FactorySetup(this);
        this.scanner = in;
    }

    /**
     * Gets the scanner used for input.
     * @return The scanner used for input.
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Sets the game settings.
     * @param settings The settings to be applied to the game.
     */
    public void setSettings(ISettings settings) {
        this.settings = settings;
    }

    /**
     * Gets the number of Human players in the game.
     * @return The number of players.
     */
    public int getNumPlayers() {
        return numPlayers;
    }
    
    /**
     * Gets the number of bots in the game.
     * @return The number of bots.
     */
    public int getNumberOfBots() {
        return numberOfBots;
    }

    /**
     * Sets the number of bots in the game.
     * @param numberOfBots The number of bots to be set.
     */
    public void setNumberOfBots(int numberOfBots) {
        this.numberOfBots = numberOfBots;
    }

    
    /**
     * Sets the number of players in the game.
     * @param numPlayers The number of players to be set.
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * Gets the list of players in the game.
     * @return ArrayList<IPlayer> The list of players.
     */
    public ArrayList<IPlayer> getPlayers() {
        return players;
    }

    /**
     * Gets a player by index.
     * @param index The index of the player.
     * @return The player at the specified index.
     */
    public IPlayer getPlayer(int index) {
        return players.get(index);
    }

    /**
     * Adds a player to the game.
     * @param player The player to be added.
     */
    public void addPlayer(IPlayer player) {
        players.add(player);
    }

    /**
     * Gets the index of the start player.
     * @return The index of the start player.
     */
    public int getStartPlayer() {
        return startPlayer;
    }

    /**
     * Sets the index of the start player.
     * @param startPlayer The index of the start player to be set.
     */
    public void setStartPlayer(int startPlayer) {
        this.startPlayer = startPlayer;
        setCurrentPlayer(startPlayer);
    }

    /**
     * Gets the index of the current player.
     * @return The index of the current player.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the index of the current player.
     * @param currentPlayer The index of the current player to be set.
     */ 
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Gets the pile manager of the game.
     * @return The pile manager.
     */
    public PileManager getPileManager() {
        return pileManager;
    }

    /**
     * Gets the game mode of the game.
     * @return The game mode.
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Gets the game settings.
     * @return The game settings.
     */
    public ISettings getSettings() {
        return settings;
    }

    /**
     * Gets the setup of the game.
     * @return The setup.
     */
    public FactorySetup getSetup() {
        return setup;
    }

    /**
     * Calculates the number of available market cards.
     * @return The number of available market cards.
     */    
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
