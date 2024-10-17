package main.game.gamelogic.turnlogic;

import main.game.players.IPlayer;
import main.game.setupgame.GameState;

public interface ITurnLogic {

    public void takeTurn(GameState gameState, IPlayer thisPlayer);
    public void startTurnPrint();
    public void endTurnPrint();
}