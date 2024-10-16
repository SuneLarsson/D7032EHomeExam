package main.game.setupgame;

import java.util.ArrayList;
import java.util.Arrays;

public class SaladSettings implements ISettings {
    private int maxPlayers = 6;
    private ArrayList<String> vegetableTypes = new ArrayList<>(Arrays.asList("PEPPER", "LETTUCE", "CARROT", "CABBAGE", "ONION", "TOMATO"));
    private String pointName = "Criteria";
    private String resourceName = "Vegetable";
    

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getMinPlayers() {
        return 2;
    }

    @Override
    public ArrayList<String> getCardTypes() {
        return vegetableTypes;
    }
    
    @Override
    public String getPointName() {
        return pointName;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }
}
