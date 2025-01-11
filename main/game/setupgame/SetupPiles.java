package main.game.setupgame;

import org.json.JSONArray;

import main.game.gameState.GameState;
import main.piles.json.JsonReader;
import main.piles.pile.Pile;
import main.piles.piles.BuildDecks;
import main.piles.piles.ICreatePiles;
import main.piles.piles.PilesFactory;

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
        this.pilesCreator = PilesFactory.createPilesFactory(gameState);
        pilesCreator.createPiles(gameState, buildDecks.getDecks());
        for(Pile pile : gameState.getPileManager().getPiles()) {
            pile.setupMarket();
        }
        
    }

}

