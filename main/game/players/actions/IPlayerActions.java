package main.game.players.actions;



public interface IPlayerActions {
    // void turnAction(IPlayer player, GameState gameState) ;
    boolean drawCardFromPile(int pileIndex);
    boolean takeFromMarket(String pileChoice);


}
