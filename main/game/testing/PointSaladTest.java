package main.game.testing;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import main.game.card.Card;
import main.game.card.SaladCard;
import main.game.gamelogic.PointSaladGame;
import main.game.network.Server;
import main.game.piles.PileManager;
import main.game.piles.SetupPiles;
import main.game.piles.pile.Pile;
import main.game.players.BotPlayer;
import main.game.players.IHumanPlayer;
import main.game.players.actions.SaladBotActions;
import main.game.players.actions.SaladHumanActions;
import main.game.setupgame.CreatePlayers;
import main.game.setupgame.GameState;
import main.game.setupgame.SaladSettings;

import static org.junit.jupiter.api.Assertions.*;


import java.util.HashSet;
import java.util.Set;
import java.io.ByteArrayInputStream;


class PointSaladTest {
    // private PointSaladGame game;
    private GameState gameState;
    // private ICreatePiles setPiles;
    // private PointSaladGame pointSaladGame;
    private PileManager pileManager;
    private Server server;
    private ArrayList<Card> cardsArray = new ArrayList<>();



    // @BeforeAll
    // static void setUpBeforeClass() {
    //     JsonReader jsonReader = new JsonReader();
    //     cardsArray = jsonReader.jsonData("./src/PointSaladManifest.json");
    // }

    private void setupGameWithPlayers(int numPlayers, int numBots) {
        gameState = null;
        pileManager = null;
        server.close();
        gameState = new GameState("PointSalad");
        gameState.setSettings(new SaladSettings());
        gameState.setNumPlayers(numPlayers);
        gameState.setNumberOfBots(numBots);
        try {
            server = new Server(gameState);
            new CreatePlayers(gameState, server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new SetupPiles(gameState);
        gameState.setCurrentPlayer(gameState.getSettings().startingPlayerRule(gameState.getNumPlayers()));
        new PointSaladGame(gameState);
        pileManager = gameState.getPileManager();
    }

    // Utility method to simulate user input
    private void simulateInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);  // Redirect System.in to the input
    }


    
    private void createCards(){
        // 1
        cardsArray.add(new SaladCard("PEPPER", "MOST LETTUCE = 10", true));
        // 2
        cardsArray.add(new SaladCard("LETTUCE", "FEWEST PEPPER = 7", true));
        // 3
        cardsArray.add(new SaladCard("CARROT", "CABBAGE: EVEN=7, ODD=3", true));
        // 4
        cardsArray.add(new SaladCard("CABBAGE", "2 / CARROT", true));
        // 5
        cardsArray.add(new SaladCard("ONION", "TOMATO + TOMATO = 5", true));
        // 6
        cardsArray.add(new SaladCard("TOMATO", "CARROT + LETTUCE = 5", true));
        // 7
        cardsArray.add(new SaladCard("PEPPER", "1 / LETTUCE,  1 / ONION", true));
        // 8
        cardsArray.add(new SaladCard("LETTUCE", "3 / PEPPER,  -2 / CABBAGE", true));
        // 9
        cardsArray.add(new SaladCard("CARROT", "CABBAGE + CABBAGE + CABBAGE = 8", true));
        // 10
        cardsArray.add(new SaladCard("CABBAGE", "2/CARROT,  1/PEPPER,  -2/CABBAGE", true));
        // 11
        cardsArray.add(new SaladCard("ONION", "ONION + TOMATO + PEPPER = 8", true));
        // 12
        cardsArray.add(new SaladCard("PEPPER", "MOST TOTAL VEGETABLE = 10", true));
        // 13
        cardsArray.add(new SaladCard("LETTUCE", "FEWEST TOTAL VEGETABLE = 7", true));
        // 14
        cardsArray.add(new SaladCard("CARROT", "5 / VEGETABLE TYPE >=3", true));
        // 15
        cardsArray.add(new SaladCard("CABBAGE", "5 / MISSING VEGETABLE TYPE", true));
        // 16
        cardsArray.add(new SaladCard("ONION", "3 / VEGETABLE TYPE >=2", true));
        // 17
        cardsArray.add(new SaladCard("TOMATO", "COMPLETE SET = 12", true));
        // 18
        cardsArray.add(new SaladCard("TOMATO", "MOST ONION = 10", false));
    }


    @BeforeEach
    void setUp() {
        gameState = new GameState("PointSalad");
        gameState.setSettings(new SaladSettings());
        gameState.setNumPlayers(1);
        gameState.setNumberOfBots(1);
        try {
            server = new Server(gameState);
            new CreatePlayers(gameState, server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new SetupPiles(gameState);
        gameState.setCurrentPlayer(gameState.getSettings().startingPlayerRule(gameState.getNumPlayers()));
        new PointSaladGame(gameState);
        pileManager = gameState.getPileManager();
    }

    @AfterEach
    void tearDown() {
        gameState = null;
        pileManager = null;
        server.close();
    }

    // Rule 1: There can be between 2 and 6 players.
    // TODO: Implement test for Rule 1
    @Test
    void testRule1() {
        // assertThrows(IllegalArgumentException.class, () -> {
        //     gameState.setNumPlayers(1); // Should throw an exception
        // });
        
        // assertThrows(IllegalArgumentException.class, () -> {
        //     gameState.setNumPlayers(7); // Should throw an exception
        // });
        
        // // Valid cases
        // gameState.setNumPlayers(2);
        // assertEquals(2, gameState.getNumPlayers());
        
        // gameState.setNumPlayers(6);
        // assertEquals(6, gameState.getNumPlayers());
    }

    // Rule 2: The deck consists of 108 cards (as specified in the PointSaladManifest.json file published in Canvas). A card
    // has two sides, one with the criteria consisting of scoring rules, the other with one of six different vegetables
    // (Pepper, Lettuce, Carrot, Cabbage, Onion, Tomato). There are 18 of each vegetable.
    private ArrayList<Integer> countPilar() {
        ArrayList<Integer> vegetableCounts = new ArrayList<>();
        int numPepper = 0;
        int numLettuce = 0;
        int numCarrot = 0;
        int numCabbage = 0;
        int numOnion = 0;
        int numTomato = 0;
        // int pileSize = pileManager.getPile(1).getPileSize();
        for(Pile pile : pileManager.getPiles()) {
            for(int i = 0; i < pile.getPileSize(); i++) {
                    switch(pile.getCards().get(i).getResourceSide()) {
                        case "PEPPER":
                            numPepper++;
                            break;
                        case "LETTUCE":
                            numLettuce++;
                            break;
                        case "CARROT":
                            numCarrot++;
                            break;
                        case "CABBAGE":
                            numCabbage++;
                            break;
                        case "ONION":
                            numOnion++;
                            break;
                        case "TOMATO":
                            numTomato++;
                            break;
                }
            }
        }

        for(Pile pile : pileManager.getPiles()) {
            for(int i = 0; i < 2; i++) {
                switch(pile.getMarketCard(i).getResourceSide()) {
                    case "PEPPER":
                        numPepper++;
                        break;
                    case "LETTUCE":
                        numLettuce++;
                        break;
                    case "CARROT":
                        numCarrot++;
                        break;
                    case "CABBAGE":
                        numCabbage++;
                        break;
                    case "ONION":
                        numOnion++;
                        break;
                    case "TOMATO":
                        numTomato++;
                        break;
                }
            }
        }

        vegetableCounts.add(numPepper);
        vegetableCounts.add(numLettuce);
        vegetableCounts.add(numCarrot);
        vegetableCounts.add(numCabbage);
        vegetableCounts.add(numOnion);
        vegetableCounts.add(numTomato);

        return vegetableCounts;
    }

    @Test
    void testRule2() {
        setupGameWithPlayers(1, 5);
        // Check that there are 18 of each vegetable
        

        ArrayList<Integer> vegetableCounts = countPilar();

        assertEquals(18, vegetableCounts.get(0));
        assertEquals(18, vegetableCounts.get(1));
        assertEquals(18, vegetableCounts.get(2));
        assertEquals(18, vegetableCounts.get(3));
        assertEquals(18, vegetableCounts.get(4));
        assertEquals(18, vegetableCounts.get(5));
    }

    // Rule 3: Form the deck so that 
    // a. 2 Players: Use 6 random vegetable of each (36 cards in total)
    // b. 3 Players: Use 9 random vegetable of each (54 cards in total)
    // c. 4 Players: Use 12 random vegetable of each (72 cards in total)
    // d. 5 Players: Use 15 random vegetable of each (90 cards in total)
    // e. 6 Players: Use the entire deck Tested in testRule2
    // Do not reveal which cards were removed
    @Test
    void testRule3a() {
        // 2 Players
        setupGameWithPlayers(1, 1);
        ArrayList<Integer> vegetableCounts = countPilar();
        assertEquals(36, vegetableCounts.get(0) + vegetableCounts.get(1) + vegetableCounts.get(2) + vegetableCounts.get(3) + vegetableCounts.get(4) + vegetableCounts.get(5));
    }

    @Test
    void testRule3c() {
        // 4 Players
        setupGameWithPlayers(1, 3);
        ArrayList<Integer> vegetableCounts = countPilar();
        assertEquals(72, vegetableCounts.get(0) + vegetableCounts.get(1) + vegetableCounts.get(2) + vegetableCounts.get(3) + vegetableCounts.get(4) + vegetableCounts.get(5));
    }



    // Rule 4: Shuffle the cards and create three roughly equal draw piles with roughly equal draw piles with point card sides visible.
    // a. Check that piles are shuffled
    // b. Check that piles are roughly equal

    
    @Test
    void testRule4a() {
        ArrayList<Pile> piles = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            setupGameWithPlayers(1, 5);
            piles.add(pileManager.getPile(0));
        }
        if (piles.get(0).equals(piles.get(1))) {
            fail("Piles are not shuffled");
            
        }
    }
    @Test
    void testRule4b() {
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
    @Test
    void testRule7aHuman() {
        IHumanPlayer humanPlayer = (IHumanPlayer) gameState.getPlayer(0);
        SaladHumanActions humanActions = new SaladHumanActions(humanPlayer, gameState);
        for(int i = 0; i <pileManager.getPiles().size(); i++) {
            int handSize = humanPlayer.getHand().size();
            humanActions.drawCardFromPile(i);
            int handSizeAfterDraw = humanPlayer.getHand().size();
            assertTrue(handSize < handSizeAfterDraw, "Hand size should increase after drawing a card");
        }
    }

    @Test
    void testRule7aBot() {
        BotPlayer BotPlayer = (BotPlayer) gameState.getPlayer(1);
        SaladBotActions botActions = new SaladBotActions(BotPlayer, gameState);
        for(int i = 0; i <pileManager.getPiles().size(); i++) {
            int handSize = BotPlayer.getHand().size();
            botActions.drawCardFromPile(i);
            int handSizeAfterDraw = BotPlayer.getHand().size();
            assertTrue(handSize < handSizeAfterDraw, "Hand size should increase after drawing a card");
        }
    }

    @Test
    void testRule7bHuman() {
        IHumanPlayer humanPlayer = (IHumanPlayer) gameState.getPlayer(0);
        SaladHumanActions humanActions = new SaladHumanActions(humanPlayer, gameState);
        int handSize = humanPlayer.getHand().size();
        humanActions.takeFromMarket("AB");
        int handSizeAfterDraw = humanPlayer.getHand().size();
        assertTrue(handSize < handSizeAfterDraw, "Hand size should increase after drawing a card");
    }

    @Test
    void testRule7bBot() {
        BotPlayer BotPlayer = (BotPlayer) gameState.getPlayer(1);
        SaladBotActions botActions = new SaladBotActions(BotPlayer, gameState);
        int handSize = BotPlayer.getHand().size();
        botActions.takeFromMarket("AB");
        int handSizeAfterDraw = BotPlayer.getHand().size();
        assertTrue(handSize < handSizeAfterDraw, "Hand size should increase after drawing a card");
    }

    // Rule 8: The player may opt to turn a point card to its vegetable side (but not the other way around).
    @Test
    void testRule8() {
        createCards();
        IHumanPlayer humanPlayer = (IHumanPlayer) gameState.getPlayer(0);
        SaladHumanActions humanActions = new SaladHumanActions(humanPlayer, gameState);
        humanPlayer.addCard(cardsArray.get(0));
        // boolean hasPointCardInHand = humanPlayer.getHand().get(0).isPointSideUp();
        // if(hasPointCardInHand) {
        simulateInput("0\n");
        humanActions.flipCriteriaCard(gameState);
        assertFalse(humanPlayer.getHand().get(0).isPointSideUp(), "Point card should be turned to veggie side");
        // }
        // simulateInput("0\n");
        // humanActions.flipCriteriaCard(gameState);
        // assertTrue(humanPlayer.getHand().get(0).isPointSideUp(), "Veggie card should not be turned to point side");

    }

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