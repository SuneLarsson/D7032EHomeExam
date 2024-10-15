package main.game.setupgame;

import java.util.ArrayList;

public interface ISettings {
    int getMaxPlayers();
    int getMinPlayers();
    ArrayList<String> getCardTypes();
}
