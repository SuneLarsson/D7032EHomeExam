package main.game.gamelogic;

import main.game.setupgame.GameState;

public interface IGameLogic {
    void gameLoop(GameState gameState, Integer turnlimit);
    void endGame(GameState gameState);

}