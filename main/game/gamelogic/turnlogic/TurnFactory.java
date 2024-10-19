package main.game.gamelogic.turnlogic;

public class TurnFactory {
    public static ITurnLogic createTurnLogic(String gameMode) {
        if (gameMode.equals("POINTSALAD")) {
            return new SaladTurnLogic();
        } else {
            throw new IllegalArgumentException("Error: Game mode not recognized");
        }
    }
    
}
