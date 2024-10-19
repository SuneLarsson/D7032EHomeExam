package main.game.piles.piles;

public class PilesFactory {
    public static ICreatePiles createPilesFactory(String gameMode) {
        if (gameMode.equals("POINTSALAD")) {
            return new CreateSaladPiles();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
    
}
