package main.game.players;



public interface IHumanPlayer extends IPlayer {
    void sendMessage(Object message);
    String readMessage();
    // IPlayerActions getActions(GameState gameState, IPlayer player);
}