package main.game.players.actions;

import main.game.card.Card;
import main.game.display.HandDisplay;
import main.game.display.IMarket;
import main.game.display.SaladMarket;
import main.game.display.SendMessage;
import main.game.piles.PileManager;
import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;
import main.game.setupgame.GameState;

public class HumanAction implements IPlayerActions {
    @Override
    public void turnAction(IPlayer thisPlayer, GameState gameState) {
        IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
        PileManager pileManager = gameState.getPileManager();
        HandDisplay handDisplay = new HandDisplay();
        IMarket saladMarket = new SaladMarket();
        
        // Rule 7. On a player’s turn the player may draft one or more cards and add to the player’s hand. Either:
            // a. One point card from the top of any of the draw piles, or
            // b. Two veggie cards from those available in the veggie market
        // TODO Split rule 7 a/b into separate methods

  
        humanPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        humanPlayer.sendMessage(handDisplay.displayHand(humanPlayer.getHand(), gameState));
        humanPlayer.sendMessage("\nThe piles are: ");
    
        humanPlayer.sendMessage(saladMarket.printMarket(pileManager));
        boolean validChoice = false;
        while(!validChoice) {
            humanPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
            String pileChoice = humanPlayer.readMessage();
            if(pileChoice.matches("\\d")) {
                System.out.println("pileChoice: " + pileChoice);
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
        // Rule 8 The player may opt to turn a point card to its vegetable side (but not the other way around).
        //Todo Create a method for this
        if(criteriaCardInHand) {
            //Give the player an option to turn a criteria card into a veggie card
            humanPlayer.sendMessage("\n"+handDisplay.displayHand(humanPlayer.getHand(), gameState)+"\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
            String choice = humanPlayer.readMessage();
            if(choice.matches("\\d")) {
                int cardIndex = Integer.parseInt(choice);
                humanPlayer.getHand().get(cardIndex).flip();
        }
        }
        humanPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");

    }
    
}
