package main.game.players.actions;

import java.util.ArrayList;

import main.game.card.Card;
// import main.game.display.HandDisplay;
import main.game.piles.Pile;
import main.game.piles.PileManager;
import main.game.players.BotPlayer;
import main.game.players.IPlayer;
import main.game.score.PointSaladCriteria;
import main.game.setupgame.GameState;

public class BotAction implements IPlayerActions {
	private PointSaladCriteria pointSaladCriteria;

    @Override
    public void turnAction(IPlayer Player, GameState gameState) {	
		BotPlayer thisPlayer = (BotPlayer) Player;
		PileManager pileManager = gameState.getPileManager();
		// HandDisplay handDisplay = new HandDisplay();
		ArrayList<String> cardTypes = gameState.getCardTypes();


		boolean emptyPiles = false;
				// Random choice: 
				int choice = (int) (Math.random() * 2);
				if(choice == 0) {
					// Take a point card
					int highestPointCardIndex = 0;
					int highestPointCardScore = 0;
					for(int i = 0; i < pileManager.getPiles().size(); i++) {
						if(pileManager.getPile(i).getPileCard() != null) {
							ArrayList<Card> tempHand = new ArrayList<Card>();
							for(Card handCard : thisPlayer.getHand()) {
								tempHand.add(handCard);
							}
							tempHand.add(pileManager.getPile(i).getPileCard());
							pointSaladCriteria = new PointSaladCriteria();
                            int score = pointSaladCriteria.calculateScore(thisPlayer, gameState.getPlayers(), cardTypes, tempHand);
							// int score = calculateScore(tempHand, thisPlayer);
							if(score > highestPointCardScore) {
								highestPointCardScore = score;
								highestPointCardIndex = i;
							}
						}
					}
					if(pileManager.getPile(highestPointCardIndex).getPileCard() != null) {
						thisPlayer.addCard(pileManager.getPile(highestPointCardIndex).buyPileCard());
					} else {
						choice = 1; //buy veggies instead
						emptyPiles = true;
					}
				} else if (choice == 1) {
					// TODO: Check what Veggies are available and run calculateScore to see which veggies are best to pick
					int cardsPicked = 0;
					for(Pile pile : pileManager.getPiles()) {
						if(pile.getMarketCard(0) != null && cardsPicked < 2) {
							thisPlayer.addCard(pile.buyMarketCard(0));
							cardsPicked++;
						}
						if(pile.getMarketCard(1) != null && cardsPicked < 2) {
							thisPlayer.addCard(pile.buyMarketCard(1));
							cardsPicked++;
						}
					}
					if(cardsPicked == 0 && !emptyPiles) {
						// Take a point card instead of veggies if there are no veggies left
						int highestPointCardIndex = 0;
						int highestPointCardScore = 0;
						for(int i = 0; i < pileManager.getPiles().size(); i++) {
							if(pileManager.getPile(i).getPileCard() != null && pileManager.getPile(i).getPileCard().isPointSideUp()) {
								ArrayList<Card> tempHand = new ArrayList<Card>();
								for(Card handCard : thisPlayer.getHand()) {
									tempHand.add(handCard);
								}
								tempHand.add(pileManager.getPile(i).getPileCard());
								pointSaladCriteria = new PointSaladCriteria();
								int score = pointSaladCriteria.calculateScore(thisPlayer, gameState.getPlayers(), cardTypes, tempHand);
								if(score > highestPointCardScore) {
									highestPointCardScore = score;
									highestPointCardIndex = i;
								}
							}
						}
						if(pileManager.getPile(highestPointCardIndex).getPileCard() != null) {
							thisPlayer.addCard(pileManager.getPile(highestPointCardIndex).buyPileCard());
						}
					}
				}
				// sendToAllPlayers("Bot " + thisPlayer.getPlayerID() + "'s hand is now: \n"+displayHand(thisPlayer.getHand())+"\n");
    }
    
}
