package main.game.players.actions;

import main.game.display.HandDisplay;

import main.game.piles.PileManager;
import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;
import main.game.setupgame.GameState;

public class SaladHumanActions implements IPlayerActions {
    private IHumanPlayer humanPlayer;
    private GameState gameState;
    private PileManager pileManager;
    private HandDisplay handDisplay;



    public SaladHumanActions(IPlayer thisPlayer, GameState gameState) {
        this.gameState = gameState;
        this.humanPlayer = (IHumanPlayer) thisPlayer;
        this.pileManager = gameState.getPileManager();
        this.handDisplay = new HandDisplay();
    }

    @Override
    public boolean drawCardFromPile(int pileIndex){
        if(pileIndex < 0 || pileIndex > pileManager.getPiles().size()-1) {
            humanPlayer.sendMessage("\nInvalid choice. Please choose a pile between 0 and "+ (pileManager.getPiles().size()-1) +".\n");
            return false;
        } else if(pileManager.getPile(pileIndex).getPileCard() == null) {
            humanPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
            return false;
        } else {
            humanPlayer.addCard(pileManager.getPile(pileIndex).buyPileCard());
            humanPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
            return true;
        }
    }
    @Override
    public boolean takeFromMarket(String pileChoice){
        int takenVeggies = 0;
        int availableVeggies = gameState.availableMarketCards();
        if (availableVeggies > 1 && pileChoice.length() < 2) {
            humanPlayer.sendMessage("\nYou need to take two vegetables from the market.\nTry again!\n");
            return false;
        } 
        if (pileChoice.length() > 1) {
            if (pileChoice.charAt(0) == (pileChoice.charAt(1))) {
                humanPlayer.sendMessage("\nYou can't take the same vegetable twice.\nTry again!\n");
                return false;
            }
     
        } 
        boolean validChoice = false;
        for(int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
            if(Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
                humanPlayer.sendMessage("\nInvalid choice ("  +pileChoice.charAt(charIndex) + "). Please choose up to two veggie cards from the market.\n");
                return false;
            }
            int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
            int marketIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2:-1;
            int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;

            if(pileManager.getPile(marketIndex).getMarketCard(veggieIndex) == null) {
                humanPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
                return false;
            } else {
                if(takenVeggies == 2) {
                    return true;
                } else {
                    humanPlayer.addCard(pileManager.getPile(marketIndex).buyMarketCard(veggieIndex));
                    takenVeggies++;
                    validChoice = true;

                    //thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
                }
            }
        }

        return validChoice;
    
    }

    // Rule 8: If the player has a criteria card in their hand, they can flip it to a veggie card
    public boolean flipCriteriaCard(GameState gameState) {
        humanPlayer.sendMessage("\n"+handDisplay.displayHand(humanPlayer.getHand(), gameState)+"\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
        String choice = humanPlayer.readMessage();
        if(choice.matches("\\d")) {
            int cardIndex = Integer.parseInt(choice);
            if (!humanPlayer.getHand().get(cardIndex).isPointSideUp()) {
                humanPlayer.sendMessage("\nYou can't flip a veggie card to a criteria card.\n");
                return false;
                
            }else{
                humanPlayer.getHand().get(cardIndex).flip();
                return true;
            }
       }else{
            humanPlayer.sendMessage("\nInvalid choice. Please choose a valid card index.\n");
            return true;
       }

    }



    
}
