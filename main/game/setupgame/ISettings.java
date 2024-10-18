package main.game.setupgame;

import java.util.ArrayList;

public interface ISettings {
    int getMaxPlayers();
    int getMinPlayers();
    ArrayList<String> getCardTypes();
    String getPointName();
    String getResourceName();
    int startingPlayerRule(int numPlayers);
    ArrayList<Integer> getAmountOfEachCardType(int numberPlayers);
    String getJsonPath();
    int getTurnLimit();

}
