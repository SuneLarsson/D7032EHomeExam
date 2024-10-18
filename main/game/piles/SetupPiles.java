package main.game.piles;


import org.json.JSONArray;

import main.game.json.JsonReader;
import main.game.piles.pile.Pile;
import main.game.piles.piles.BuildDecks;
import main.game.piles.piles.ICreatePiles;
import main.game.setupgame.GameState;

public class SetupPiles {

    private JsonReader jsonReader;
    private JSONArray cardsArray;
    private BuildDecks buildDecks;
    private ICreatePiles pilesCreator;
    
    public SetupPiles(GameState gameState) {
        this.jsonReader = new JsonReader();
        this.cardsArray = jsonReader.jsonData(gameState.getSettings().getJsonPath());
        this.buildDecks = new BuildDecks(gameState, cardsArray);       
        this.pilesCreator = gameState.getSetup().getCreatePiles();

        pilesCreator.createPiles(gameState, buildDecks.getDecks());

        for(Pile pile : gameState.getPileManager().getPiles()) {
            // System.out.println(pile.getCards().get(0));
            pile.setupMarket();
        }
        
    }

}

