package main.game.display;


import main.game.piles.Old.PileManager;

public interface IMarket {
    /**
     * Prints the market
     * @return a string representation of the market
     */
    String printMarket(PileManager pileManager);
    
}
