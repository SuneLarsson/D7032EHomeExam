package main.game.setupgame;

import main.game.card.CardFactory;
import main.game.card.ICardFactory;
import main.game.gamelogic.turnlogic.ITurnLogic;
import main.game.gamelogic.turnlogic.TurnFactory;
import main.game.piles.piles.ICreatePiles;
import main.game.piles.piles.PilesFactory;
import main.game.score.CriteriaFactory;
import main.game.score.ICriteria;


public class Setup {    
    private GameState gameState;

    public Setup(GameState gameState) {
        this.gameState = gameState;
    }

    public ICardFactory getCardFactory() {
        return CardFactory.createCardFactory(gameState);
    }

    public ICriteria getScoreFactory() {
        return CriteriaFactory.createCriteria(gameState.getGameMode());
    }

    public ITurnLogic getTurnFactory() {
        return TurnFactory.createTurnLogic(gameState.getGameMode());
    }

    public ICreatePiles getPilesFactory() {
        return PilesFactory.createPilesFactory(gameState.getGameMode());
    }

     
}
