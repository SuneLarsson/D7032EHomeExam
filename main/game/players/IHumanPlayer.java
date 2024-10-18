package main.game.players;

import main.game.setupgame.GameState;

public interface IHumanPlayer extends IPlayer {
    void sendMessage(Object message);
    String readMessage(GameState gameState);
    // IPlayerActions getActions(GameState gameState, IPlayer player);
}