package main.game.players;

import main.game.players.actions.IPlayerActions;
import main.game.setupgame.GameState;

public interface IHumanPlayer extends IPlayer {
    void sendMessage(Object message);
    String readMessage();
    // IPlayerActions getActions(GameState gameState, IPlayer player);
}