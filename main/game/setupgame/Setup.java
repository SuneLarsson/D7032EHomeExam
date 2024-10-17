package main.game.setupgame;

import main.game.card.ICardFactory;
import main.game.card.SaladCardFactory;
import main.game.gamelogic.turnlogic.ITurnLogic;
import main.game.gamelogic.turnlogic.SaladTurnLogic;
import main.game.piles.piles.CreateSaladPiles;
import main.game.piles.piles.ICreatePiles;
import main.game.players.actions.IPlayerActions;
import main.game.score.ICriteria;
import main.game.score.PointSaladCriteria;

public class Setup {    
    private GameState gameState;

    public Setup(GameState gameState) {
        this.gameState = gameState;
    }

    public ICardFactory getCardFactory() {
        if (gameState.getGameMode().equals("PointSalad")) {
            return new SaladCardFactory();
        } else {
            System.out.println("Error: Game mode not recognized");
            return null;
        }
    }

    public ICriteria getScoreCriteria() {
        if (gameState.getGameMode().equals("PointSalad")) {
            return new PointSaladCriteria();
        } else {
            System.out.println("Error: Game mode not recognized");
            return null;
        }
    }


    public ITurnLogic getTurnLogic() {
        if (gameState.getGameMode().equals("PointSalad")) {
            return new SaladTurnLogic();
        } else {
            System.out.println("Error: Game mode not recognized");
            return null;
        }
    }

    public ICreatePiles getCreatePiles() {
        if (gameState.getGameMode().equals("PointSalad")) {
            return new CreateSaladPiles();
        } else {
            System.out.println("Error: Game mode not recognized");
            return null;
        }
    }


     
}
