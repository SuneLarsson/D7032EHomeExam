package main.game.piles;

import org.json.JSONArray;

import main.game.game.gameState.GameState;
import main.game.piles.json.JsonReader;
import main.game.piles.pile.Pile;
import main.game.piles.piles.BuildDecks;
import main.game.piles.piles.ICreatePiles;

/**
 * Sets up the piles for the game.
 */
public class SetupPiles {

    private JsonReader jsonReader;
    private JSONArray cardsArray;
    private BuildDecks buildDecks;
    private ICreatePiles pilesCreator;
    
    /**
     * Reads the JSON file and creates the decks of cards.
     * Then creates the piles for the game from the decks.
     * @param gameState The current game state.
     */
    public SetupPiles(GameState gameState) {
        this.jsonReader = new JsonReader();
        this.cardsArray = jsonReader.jsonData(gameState.getSettings().getJsonPath());
        this.buildDecks = new BuildDecks(gameState, cardsArray);       
        this.pilesCreator = gameState.getSetup().getPilesFactory();
        pilesCreator.createPiles(gameState, buildDecks.getDecks());
        for(Pile pile : gameState.getPileManager().getPiles()) {
            pile.setupMarket();
        }
        
    }

}

