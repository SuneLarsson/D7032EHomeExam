package main.game.players.actions;

/**
 * Interface for player actions
 */
public interface IPlayerActions {
    // void turnAction(IPlayer player, GameState gameState) ;

    /**
     * Draw a card from the pile at pileIndex
     * @param pileIndex the index of the pile to draw from
     * @return boolean true if the card was drawn, false if the input was invalid
     */
    boolean drawCardFromPile(int pileIndex);

    /**
     * Draw a card from the Market
     * @param pileChoice Sting user input for which cards to draw
     * @return boolean true if the card was drawn, false if the input was invalid
     */
    boolean takeFromMarket(String pileChoice);


}
