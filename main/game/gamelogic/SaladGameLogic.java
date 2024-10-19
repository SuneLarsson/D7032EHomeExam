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
		// int playerID = 0;
		ArrayList<IPlayer> tiedPlayers = new ArrayList<>();
		for(IPlayer player : players) {
			if(player.getScore() > maxScore) {
				maxScore = player.getScore();
				tiedPlayers.clear();
				tiedPlayers.add(player);
				// playerID = player.getPlayerID();
			} else if(player.getScore() == maxScore) {
				tiedPlayers.add(player);
			}
		}

		// If there are ties, determine the winner based on turn order
		int winnerID = -1;
		if (tiedPlayers.size() > 1) {
			int startingPlayerID = gameState.getStartPlayer();

			// Track the last player in the tied group
			winnerID = -1;

			// Iterate through players to find the last one who played among tied players
			for (int i = startingPlayerID; i < players.size()+startingPlayerID; i++) {
				int playerToCheck = i;
				if (i >= players.size()) {
					playerToCheck = i - players.size();
				}
				if (tiedPlayers.contains(players.get(playerToCheck))) {
					winnerID = players.get(playerToCheck).getPlayerID();
				}
	
			}
		} else {
			winnerID = tiedPlayers.get(0).getPlayerID(); // No ties, the one with the max score is the winner
		}




		for(IPlayer player : players) {
			if(player.getPlayerID() == winnerID ) {
                String winnerMessage = "\nCongratulations! You are the winner with a score of " + maxScore;
                if (player instanceof IHumanPlayer) {
                    IHumanPlayer humanPlayer = (IHumanPlayer) player;
                    humanPlayer.sendMessage(winnerMessage);   
                }
			} else {
                String looserMessage = "\nSorry, you lost. The winner is player " + winnerID + " with a score of " + maxScore;
                if (player instanceof IHumanPlayer) {
                    IHumanPlayer humanPlayer = (IHumanPlayer) player;
                    humanPlayer.sendMessage(looserMessage);   
                }
			}
		}
    }
}
