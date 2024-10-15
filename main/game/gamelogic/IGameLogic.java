package main.game.gamelogic;

public interface IGameLogic {
    void initializeGame();
    void startGame();
    void endGame();
    void makeMove(int playerId, int move);
    boolean isGameOver();
    int getWinner();
}