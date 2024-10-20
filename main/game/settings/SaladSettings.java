package main.game.settings;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that defines the specific settings for the PointSalad game
 * Such as the maximum number of players, the card types, the point name, the resource name, the amount of each vegetable per player, and the json path
 */
public class SaladSettings implements ISettings {
    private int maxPlayers = 6;
    private ArrayList<String> vegetableTypes = new ArrayList<>(Arrays.asList("PEPPER", "LETTUCE", "CARROT", "CABBAGE", "ONION", "TOMATO"));
    private String pointName = "Criteria";
    private String resourceName = "Vegetable";
    private int amountOfEachVegetablePerPlayer = 3;
    private String jsonPath = "./main/game/PointSaladManifest.json";


    @Override
    public ArrayList<Integer> getAmountOfEachCardType(int numberPlayers) {
        ArrayList<Integer> amountPerType = new ArrayList<>();
        int amountOfEachVegetable = amountOfEachVegetablePerPlayer * numberPlayers;
        for (int i = 0; i < vegetableTypes.size(); i++) {
            amountPerType.add(amountOfEachVegetable);
        }
        return amountPerType;

    }

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

    @Override
    // Rule 6: Randomly select a starting player.
    public int startingPlayerRule(int numPlayers) {
        return (int) (Math.random() * numPlayers);
    }

    @Override
    public String getJsonPath() {
        return jsonPath;
    }

    @Override
    public int getTurnLimit() {
        return -1;
    }


}
