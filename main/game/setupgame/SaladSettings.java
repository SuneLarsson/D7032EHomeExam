package main.game.setupgame;

import java.util.ArrayList;
import java.util.Arrays;

public class SaladSettings implements ISettings {
    private int maxPlayers = 6;
    private ArrayList<String> vegetableTypes = new ArrayList<>(Arrays.asList("PEPPER", "LETTUCE", "CARROT", "CABBAGE", "ONION", "TOMATO"));
    private String pointName = "Criteria";
    private String resourceName = "Vegetable";
    private int amountOfEachVegetablePerPlayer = 3;
    private String jsonPath = "./src/PointSaladManifest.json";


    @Override
    public ArrayList<Integer> getAmountOfEachCardType(int numberPlayers) {
        ArrayList<Integer> amountPerType = new ArrayList<>();
        int amountOfEachVegetable = amountOfEachVegetablePerPlayer * numberPlayers;
        System.out.println("Amount of each vegetable: " + amountOfEachVegetable);
        for (int i = 0; i < vegetableTypes.size(); i++) {
            amountPerType.add(amountOfEachVegetable);
        }
        // amountPerType.add(amountOfEachVegetablePerPlayer);
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


}
