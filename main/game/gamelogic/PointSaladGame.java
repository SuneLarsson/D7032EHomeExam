package main.game.gamelogic;

import main.game.display.HandDisplay;
import main.game.display.SendMessage;
import main.game.piles.Pile;
import main.game.piles.PileManager;
import main.game.players.IPlayer;
import main.game.setupgame.GameState;


public class PointSaladGame {
	private PileManager pileManager;
	private SendMessage SendMessage;
	private HandDisplay HandDisplay;

	public PointSaladGame(GameState gameState) {
		this.pileManager = gameState.getPileManager();
		this.SendMessage = new SendMessage();
		this.HandDisplay = new HandDisplay();
	}

    public void gameLoop(GameState gameState) {
        boolean keepPlaying = true;
		while(keepPlaying) {
			IPlayer thisPlayer = gameState.getPlayer(gameState.getCurrentPlayer());
			boolean stillAvailableCards = false;
			for(Pile p: pileManager.getPiles()) {
				if(!p.isEmpty()) {
					
					stillAvailableCards = true;
					break;
				}
			}
			if(!stillAvailableCards) {
				keepPlaying = false;
				break;
			}

			thisPlayer.takeTurn(gameState);
			
			//Rule 9 - Display the player's hand to all players
			//TODO: Implement this in the correct place to provide testing?
            SendMessage.sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n"+HandDisplay.displayHand(thisPlayer.getHand(), gameState)+"\n", gameState.getPlayers());	

			
			if(gameState.getCurrentPlayer() == gameState.getPlayers().size()-1) {
				gameState.setCurrentPlayer(0);
			} else {
                gameState.setCurrentPlayer(gameState.getCurrentPlayer()+1);
			}
		}
    }

}
