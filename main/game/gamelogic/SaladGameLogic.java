package main.game.gamelogic;

import java.util.ArrayList;

import main.game.display.HandDisplay;
import main.game.display.SendMessage;
import main.game.piles.PileManager;
import main.game.piles.pile.Pile;
import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;
import main.game.score.CalculatePoints;
import main.game.setupgame.GameState;


public class SaladGameLogic implements IGameLogic {
	private PileManager pileManager;
	// private SendMessage sendMessage;
	// private HandDisplay handDisplay;

	public SaladGameLogic(GameState gameState) {
		this.pileManager = gameState.getPileManager();
		// this.sendMessage = new SendMessage();
		// this.handDisplay = new HandDisplay();
	}

    public void gameLoop(GameState gameState, Integer turnlimit) {
        boolean keepPlaying = true;
		int i = 0;
		while(keepPlaying && (i < turnlimit || turnlimit == -1)) {
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
			
			// //Rule 9 - Display the player's hand to all players
			// sendPlayerHandToAllPlayers(thisPlayer, gameState);
            // // SendMessage.sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n"+HandDisplay.displayHand(thisPlayer.getHand(), gameState)+"\n", gameState.getPlayers());	

			
			if(gameState.getCurrentPlayer() == gameState.getPlayers().size()-1) {
				gameState.setCurrentPlayer(0);
			} else {
                gameState.setCurrentPlayer(gameState.getCurrentPlayer()+1);
			}
			i++;
		}
    }


	public void endGame(GameState gameState) {
        SendMessage sendToAll = new SendMessage();
        HandDisplay handDisplay = new HandDisplay();
        ArrayList<IPlayer> players = gameState.getPlayers();
        CalculatePoints calculatePoints = new CalculatePoints();
		sendToAll.sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"), players);
        calculatePoints.scorePoints(gameState);
		for(IPlayer player : players) {

			sendToAll.sendToAllPlayers("Player " + player.getPlayerID() + "'s hand is: \n"+ handDisplay.displayHand(player.getHand(), gameState), players);
			sendToAll.sendToAllPlayers("\nPlayer " + player.getPlayerID() + "'s score is: " + player.getScore(), players);
		}

		int maxScore = 0;
		int playerID = 0;
		for(IPlayer player : players) {
			if(player.getScore() > maxScore) {
				maxScore = player.getScore();
				playerID = player.getPlayerID();
			}
		}
		for(IPlayer player : players) {
			if(player.getPlayerID() == playerID ) {
                String winnerMessage = "\\nCongratulations! You are the winner with a score of " + maxScore;
                if (player instanceof IHumanPlayer) {
                    IHumanPlayer humanPlayer = (IHumanPlayer) player;
                    humanPlayer.sendMessage(winnerMessage);   
                }
			} else {
                String looserMessage = "\nSorry, you lost. The winner is player " + playerID + " with a score of " + maxScore;
                if (player instanceof IHumanPlayer) {
                    IHumanPlayer humanPlayer = (IHumanPlayer) player;
                    humanPlayer.sendMessage(looserMessage);   
                }
			}
		}
    }
}
