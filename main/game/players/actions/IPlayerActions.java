package main.game.players.actions;


import main.game.players.IPlayer;
import main.game.setupgame.GameState;

public interface IPlayerActions {
    void turnAction(IPlayer player, GameState gameState) ;
}
