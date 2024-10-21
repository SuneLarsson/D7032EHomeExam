package main.game.players.actions;

import main.game.game.gameState.GameState;
import main.game.piles.PileManager;
import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;

import java.util.ArrayList;


/**
 * Class for the actions of a human player for PointSalad game
 */
public class SaladHumanActions implements IPlayerActions {
    private IHumanPlayer humanPlayer;
    private GameState gameState;
    private PileManager pileManager;


    /**
     * Constructor for SaladHumanActions
     * @param thisPlayer the human player
     * @param gameState the current game state
     */
    public SaladHumanActions(IPlayer thisPlayer, GameState gameState) {
        this.gameState = gameState;
        this.humanPlayer = (IHumanPlayer) thisPlayer;
        this.pileManager = gameState.getPileManager();
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
        int availableVeggies = availableMarketCards();
        if (pileChoice.length() > 2){
            pileChoice = pileChoice.substring(0, 2);
        }
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
        ArrayList<Integer> marketIndexes = new ArrayList<>();
        ArrayList<Integer> veggieIndexes = new ArrayList<>();
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
                    marketIndexes.add(marketIndex);
                    veggieIndexes.add(veggieIndex);
                    // humanPlayer.addCard(pileManager.getPile(marketIndex).buyMarketCard(veggieIndex));
                    takenVeggies++;
                    validChoice = true;

                    //thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
                }
            }
        }
        for(int i = 0; i < marketIndexes.size(); i++) {
            humanPlayer.addCard(pileManager.getPile(marketIndexes.get(i)).buyMarketCard(veggieIndexes.get(i)));
        }

        return validChoice;
    
    }

    // Rule 8: If the player has a criteria card in their hand, they can flip it to a veggie card
    /**
     * Flips a criteria card to a veggie card
     * @param choice the index of the card to flip
     * @return boolean true if the card was flipped, false if the input was invalid
     */
    public boolean flipCriteriaCard(String choice) {
        if(choice.matches("\\d+")) {
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
    
    private int availableMarketCards(){
        int marketCards = 0;
        for (int i = 0; i < pileManager.getPiles().size(); i++) {
            for (int j = 0; j < 2; j++) {
                if (pileManager.getPile(i).getMarketCard(j) != null) {
                    marketCards++;
                }
            }
        }
        return marketCards;
    }



    
}
