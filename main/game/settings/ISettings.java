package main.game.settings;

import java.util.ArrayList;

/**
 * Interface that defines the settings for the game.
 */
public interface ISettings {
    /**
     * Gets the maximum number of players for the game.
     * @return int Maximum number of players.
     */
    int getMaxPlayers();
    /**
     * Gets the minimum number of players for the game.
     * @return int Minimum number of players.
     */
    int getMinPlayers();
    /**
     * Get the array of card types for the game.
     * @return ArrayList<String> of card types.
     */
    ArrayList<String> getCardTypes();
    /**
     * Get name Of the pointSide of the card
     * @return String name of the point side of the card
     */
    String getPointName();
    /**
     * Get name of the resource side of the card
     * @return String name of the resource side of the card
     */
    String getResourceName();
    /**
     * Get the Starting player rule for the game
     * @param numPlayers The number of players in the game
     * @return int The starting player
     */
    int startingPlayerRule(int numPlayers);
    /**
     * Get the amount of each card type for the game
     * @param numberPlayers The number of players in the game
     * @return ArrayList<Integer> The amount of each card type
     */
    ArrayList<Integer> getAmountOfEachCardType(int numberPlayers);
    /**
     * Get the path to the json file
     * @return String The path to the json file
     */
    String getJsonPath();
    /**
     * Get the turn limit for the game
     * @return int The turn limit for the game
     */
    int getTurnLimit();

}
