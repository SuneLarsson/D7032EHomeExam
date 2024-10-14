package main.game.gamelogic;

import main.game.card.Card;
import main.game.display.HandDisplay;
import main.game.display.SaladMarket;
import main.game.display.SendMessage;
import main.game.piles.Pile;
import main.game.piles.PileManager;
import main.game.piles.SetPiles;
import main.game.players.BotPlayer;
import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;
import main.game.score.PointSaladCriteria;
import main.game.setupgame.GameState;
import java.util.ArrayList;

public class PointSaladGame {
	private PileManager pileManager;
	private SaladMarket saladMarket;
	private SendMessage SendMessage;
	private HandDisplay HandDisplay;

	public PointSaladGame(GameState gameState) {
		this.pileManager = gameState.getPileManager();
		this.saladMarket = new SaladMarket();
		this.SendMessage = new SendMessage();
		this.HandDisplay = new HandDisplay();
	}

    public void gameLoop(GameState gameState) {
        boolean keepPlaying = true;
        // PileManager pileManager = gameState.getPileManager();
        // SaladMarket saladMarket = new SaladMarket();
        // SendMessage SendMessage = new SendMessage();
		// HandDisplay HandDisplay = new HandDisplay();
        // int currentPlayer = gameState.getCurrentPlayer();

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
			if(!(thisPlayer instanceof BotPlayer)) {
                // Human player logic
                IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
				humanPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
				humanPlayer.sendMessage(HandDisplay.displayHand(humanPlayer.getHand(), SetPiles.getCardType()));
				humanPlayer.sendMessage("\nThe piles are: ");
			
				humanPlayer.sendMessage(saladMarket.printMarket(pileManager));
				boolean validChoice = false;
				while(!validChoice) {
					humanPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
					String pileChoice = humanPlayer.readMessage();
					if(pileChoice.matches("\\d")) {
						int pileIndex = Integer.parseInt(pileChoice);
						if(pileManager.getPile(pileIndex).getPileCard() == null) {
							humanPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
							continue;
						} else {
							humanPlayer.addCard(pileManager.getPile(pileIndex).buyPileCard());
							humanPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
							validChoice = true;
						}
					} else {
						int takenVeggies = 0;
						for(int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
							if(Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
								humanPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
								validChoice = false;
								break;
							}
							int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
							int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2:-1;
							int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
                        //VEGGIE CARD behövs
                            if(pileManager.getPile(pileIndex).getMarketCard(veggieIndex) == null) {
								humanPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
								validChoice = false;
								break;
							} else {
								if(takenVeggies == 2) {
									validChoice = true;
									break;
								} else {
									humanPlayer.addCard(pileManager.getPile(pileIndex).buyMarketCard(veggieIndex));
									takenVeggies++;
									//thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
									validChoice = true;
								}
							}
						}

					}
				}
				//Check if the player has any criteria cards in their hand
				boolean criteriaCardInHand = false;
				for(Card card : thisPlayer.getHand()) {
					if(card.isPointSideUp()) {
						criteriaCardInHand = true;
						break;
					}
				}
				if(criteriaCardInHand) {
					//Give the player an option to turn a criteria card into a veggie card
					humanPlayer.sendMessage("\n"+HandDisplay.displayHand(humanPlayer.getHand(), SetPiles.getCardType())+"\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
					String choice = humanPlayer.readMessage();
					if(choice.matches("\\d")) {
						int cardIndex = Integer.parseInt(choice);
                        humanPlayer.getHand().get(cardIndex).flip();
						// humanPlayer.getHand().get(cardIndex).isPointSideUp() = false;
					}
				}
				humanPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
				SendMessage.sendToAllPlayers("Player " + humanPlayer.getPlayerID() + "'s hand is now: \n"+HandDisplay.displayHand(humanPlayer.getHand(), SetPiles.getCardType())+"\n", gameState.getPlayers());	

			} else {
				// Bot logic
				// The Bot will randomly decide to take either one point card or two veggie cards 
				// For point card the Bot will always take the point card with the highest score
				// If there are two point cards with the same score, the bot will take the first one
				// For Veggie cards the Bot will pick the first one or two available veggies
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

                            int score = PointSaladCriteria.calculateScore(thisPlayer, gameState.getPlayers(), SetPiles.getCardType(), tempHand);
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
								int score = PointSaladCriteria.calculateScore(thisPlayer, gameState.getPlayers(), SetPiles.getCardType(), tempHand);
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
                SendMessage.sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n"+HandDisplay.displayHand(thisPlayer.getHand(), SetPiles.getCardType())+"\n", gameState.getPlayers());	

			}
			
			if(gameState.getCurrentPlayer() == gameState.getPlayers().size()-1) {
				gameState.setCurrentPlayer(0);
                // currentPlayer = 0;
			} else {
                gameState.setCurrentPlayer(gameState.getCurrentPlayer()+1);
				// currentPlayer++;
			}
		}
    }

}
