package main.piles.piles;

import java.util.ArrayList;

import main.card.Card;
import main.game.gameState.GameState;
import main.piles.PileManager;
import main.piles.pile.Pile;
import main.piles.pile.SaladPile;

import java.util.Map;
import java.util.Collections;

/**
 * Creates the piles for the PointSalad game.
 * Selects the amount of each card type based on the number of players.
 * Shuffles the deck and divides it into 3 piles.
 * Shuffles the piles.
 * Adds the piles to the pile manager.
 */

public class CreateSaladPiles implements ICreatePiles {

    @Override
    public void createPiles(GameState gameState, Map<String, ArrayList<Card>> decks) {
        try {
            shufflePiles(decks);
            ArrayList<Card> combineDeck = new ArrayList<>();
            int numberOfPlayers = gameState.getPlayers().size();
            for (ArrayList<Card> deck : decks.values()) {
                int j = 0;
                for (int i = 0; i < gameState.getSettings().getAmountOfEachCardType(numberOfPlayers).get(j); i++) {
                    combineDeck.add(deck.remove(0));
                }
            }
            shuffleDeck(combineDeck);
            
            //divide the deck into 3 piles
            PileManager pileManager = gameState.getPileManager();
            Pile pile1 = new SaladPile(pileManager, 0);
            Pile pile2 = new SaladPile(pileManager, 1);
            Pile pile3 = new SaladPile(pileManager, 2);
    ;
            for (int i = 0; i < combineDeck.size(); i++) {
                if (i % 3 == 0) {
                    pile1.addCard(combineDeck.get(i));
                } else if (i % 3 == 1) {
                    pile2.addCard(combineDeck.get(i));
                } else {
                    pile3.addCard(combineDeck.get(i));
                }
            }
    
            pileManager.addPile(pile1);
            pileManager.addPile(pile2);
            pileManager.addPile(pile3);
    
        } catch (Exception e) {
            System.out.println("Error in createPiles");
            
            throw new IllegalArgumentException("Error in createPiles");
        }
 
    }

    private void shufflePiles(Map<String, ArrayList<Card>> decks) {
        for (ArrayList<Card> deck : decks.values()) {
            shuffleDeck(deck);
        }
    }

    private void shuffleDeck(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }
}
