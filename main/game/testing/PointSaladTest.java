package main.game.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.game.PointGame;
import main.game.gamelogic.PointSaladGame;
import main.game.network.Server;
import main.game.piles.PileManager;
import main.game.piles.SetupPiles;
import main.game.piles.pile.Pile;
import main.game.piles.piles.ICreatePiles;
import main.game.setupgame.CreatePlayers;
import main.game.setupgame.GameState;
import main.game.setupgame.SaladSettings;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

class PointSaladTest {
    private PointSaladGame game;
    private GameState gameState;
    private ICreatePiles setPiles;
    private Server server;
    private PointSaladGame pointSaladGame;
    private PileManager pileManager;

    @BeforeEach
    void setUp() {
        gameState = new GameState("PointSalad");
        gameState.setSettings(new SaladSettings());
        gameState.setNumPlayers(1);
        gameState.setNumberOfBots(1);
        new SetupPiles(gameState);
        // pileManager = gameState.getPileManager();
        // setPiles.createPiles();
        try {
            CreatePlayers createPlayers = new CreatePlayers(gameState);
            // Server server = new Server(gameState);

        } catch (Exception e) {
            e.printStackTrace();
        }
        gameState.setCurrentPlayer(gameState.getSettings().startingPlayerRule(gameState.getNumPlayers()));
        pointSaladGame = new PointSaladGame(gameState);
        pileManager = gameState.getPileManager();
    }

    @Test
    void testRule4() {
        // Rule 4: Shuffle the cards and create three roughly equal draw piles with roughly equal draw piles with point card sides visible.
        // Todo check shuffle
        int pileSize = pileManager.getPile(0).getPileSize();
        for(Pile pile : pileManager.getPiles()) {
            if(pile.getPileSize() == pileSize) {
            } else if (pile.getPileSize() == pileSize + 1) { 
            } else if (pile.getPileSize() == pileSize - 1) {
            } else {
                fail("Pile sizes are not roughly equal");
            }
        }
    }

    // Rule 5: Flip over two cards from each draw pile to form the vegetable market.
    @Test
    void testRule5() {
        for(Pile pile : pileManager.getPiles()) {
            // pile.getMarketCard(0).isPointSideUp();
            // pile.getMarketCard(1).isPointSideUp();
            assertEquals(false, pile.getMarketCard(0).isPointSideUp());
            assertEquals(false, pile.getMarketCard(1).isPointSideUp());
        }
    }

    // Rule 6: Randomly choose a start player
    @Test
    void testRule6() {
        int startPlayer;
        int numPlayers;
        Set<Integer> uniqueStartPlayers;
        
        // Test for 2 players
        numPlayers = 2;
        Set<Integer> uniqueStart2Players = new HashSet<Integer>();
        for (int i = 0; i < 1000; i++) {
            startPlayer = gameState.getSettings().startingPlayerRule(numPlayers);
            assertTrue(startPlayer >= 0 && startPlayer < numPlayers, "Starting player should be between 0 and " + (numPlayers - 1));
            uniqueStart2Players.add(startPlayer);
        }
        assertTrue(uniqueStart2Players.size() > 1, "Starting player should be different for each game");
        // Test for 6 players
        numPlayers = 6;
        uniqueStartPlayers = new HashSet<Integer>();
        for (int i = 0; i < 1000; i++) {
            startPlayer = gameState.getSettings().startingPlayerRule(numPlayers);
            assertTrue(startPlayer >= 0 && startPlayer < numPlayers, "Starting player should be between 0 and " + (numPlayers - 1));
            uniqueStartPlayers.add(startPlayer);
        }
        assertTrue(uniqueStartPlayers.size() == 6, "Starting player should be different for each game");
    }

    // Rule 7: On a player’s turn the player may draft one or more cards and add to the player’s hand. Either:
    // a. One point card from the top of any of the draw piles, or
    // b. Two veggie cards from those available in the veggie market.
    //TODO: Implement test for Rule 7

    // Rule 8: The player may opt to turn a point card to its vegetable side (but not the other way around).
    // TODO Implement test for Rule 8

    // Rule 9: Show the hand to the other players.
    // TODO Implement test for Rule 9



    

//     Rules:
// 1. There can be between 2 and 6 players.
// 2. The deck consists of 108 cards (as specified in the PointSaladManifest.json file published in Canvas). A card
// has two sides, one with the criteria consisting of scoring rules, the other with one of six different vegetables
// (Pepper, Lettuce, Carrot, Cabbage, Onion, Tomato). There are 18 of each vegetable.
// 3. Form the deck so that
// a. 2 Players: Use 6 random vegetable of each (36 cards in total)
// b. 3 Players: Use 9 random vegetable of each (54 cards in total)
// c. 4 Players: Use 12 random vegetable of each (72 cards in total)
// d. 5 Players: Use 15 random vegetable of each (90 cards in total)
// e. 6 Players: Use the entire deck
// Do not reveal which cards were removed
// 4. Shuffle the cards and create three roughly equal draw piles with roughly equal draw piles with point card sides
// visible.
// 5. Flip over two cards from each draw pile to form the vegetable market.
// 6. Randomly choose a start player
// 7. On a player’s turn the player may draft one or more cards and add to the player’s hand. Either:
// a. One point card from the top of any of the draw piles, or
// b. Two veggie cards from those available in the veggie market.
// 8. The player may opt to turn a point card to its vegetable side (but not the other way
// around).
// 9. Show the hand to the other players.
// 10. Replace the market with cards from the top of the corresponding point card draw pile
// 11. If a draw pile runs out of cards, then draw cards from the bottom of the draw pile with
// the most cards instead.
// 12. Continue step 7-12 for the next player until there are no more cards in the Point
// Salad Market
// 13. Calculate the score for each player according to the point cards in hand.
// 14. Announce the winner with the highest score

}