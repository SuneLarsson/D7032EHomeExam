package main.game.setupgame;

import main.game.card.CardFactory;
import main.game.card.ICardFactory;
import main.game.gamelogic.turnlogic.ITurnLogic;
import main.game.gamelogic.turnlogic.TurnFactory;
import main.game.piles.piles.ICreatePiles;
import main.game.piles.piles.PilesFactory;
import main.game.score.CriteriaFactory;
import main.game.score.ICriteria;

/**
 * Factory class that select the factories for the game.
 */
public class FactorySetup {    
    private GameState gameState;

    /**
     * Constructor that creates the FactorySetup object.
     * @param gameState The current state of the game.
     */
    public FactorySetup(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Gets the card factory for the game.
     * @return ICardFactory The card factory for the game.
     */
    public ICardFactory getCardFactory() {
        return CardFactory.createCardFactory(gameState);
    }

    /**
     * Gets the score factory for the game.
     * @return ICriteria The score factory for the game.
     */
    public ICriteria getScoreFactory() {
        return CriteriaFactory.createCriteria(gameState.getGameMode());
    }

    /**
     * Gets the turn factory for the game.
     * @return ITurnLogic The turn factory for the game.
     */
    public ITurnLogic getTurnFactory() {
        return TurnFactory.createTurnLogic(gameState.getGameMode());
    }

    /**
     * Gets the piles factory for the game.
     * @return ICreatePiles The piles factory for the game.
     */
    public ICreatePiles getPilesFactory() {
        return PilesFactory.createPilesFactory(gameState.getGameMode());
    }

     
}
