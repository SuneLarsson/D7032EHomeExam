package main.game.testing;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import main.game.PointGame;
import main.game.card.Card;
import main.game.card.SaladCard;
import main.game.display.HandDisplay;
import main.game.display.SendMessage;
import main.game.gamelogic.SaladGameLogic;
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
import java.util.Scanner;
import java.util.Set;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class PointSaladTest {
    private GameState gameState;
    private SaladGameLogic SaladGameLogic;
    private PileManager pileManager;
    private Server server;
    private ArrayList<Card> cardsArray = new ArrayList<>();



    private void setupGameWithPlayers(int numPlayers, int numBots) {
        gameState = null;
        pileManager = null;
        server.close();

        gameState = new GameState("PointSalad", new Scanner(System.in));
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
        SaladGameLogic = new SaladGameLogic(gameState);
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
        gameState = new GameState("PointSalad", new Scanner(System.in));
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
        SaladGameLogic = new SaladGameLogic(gameState);
        pileManager = gameState.getPileManager();
    }

    @AfterEach
    void tearDown() {
        gameState = null;
        pileManager = null;
        server.close();
    }

    // Rule 1: There can be between 2 and 6 players.
    @Test
    void hej() {
    }

    @Test
    void testRule1() {
        String[] invalidArgs = {"1", "6", "PointSalad"};
        assertThrows(IllegalArgumentException.class, () -> {
            new PointGame(invalidArgs);
        }, "Should not be possible to create a game with 7 player");

        String[] invalidArgs2 = {"1", "0", "PointSalad"};
        assertThrows(IllegalArgumentException.class, () -> {
            new PointGame(invalidArgs2);
        }, "Should not be possible to create a game with 1 player");


        String[] validArgs = {"1", "1", "PointSalad"};
        try {
            new PointGame(validArgs);
        } catch (IllegalArgumentException e) {
            fail("Should be possilbe to play with 2 players.");
        } catch (Exception e) {
            // Ignore other exceptions tested elsewhere
        }

        String[] validArgs2 = {"1", "5", "PointSalad"};
        try {
            new PointGame(validArgs2);
        } catch (IllegalArgumentException e) {
            fail("Should be possilbe to play with 6 players.");
        } catch (Exception e) {
            // Ignore other exceptions tested elsewhere
        }
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
    void testRule2CardShouldHaveTwoSides() {
        setupGameWithPlayers(1, 5);
        for(Pile pile : pileManager.getPiles()) {
            for(int i = 0; i < pile.getPileSize(); i++) {
                assertNotNull(pile.getCards().get(i).getPointSide(), "Point side should exist with value");
                assertNotNull(pile.getCards().get(i).getResourceSide(), "Resource side should exist with value");
            }
        }
   }

    @Test
    void testRule2Decksize108Vegetables18() {
        setupGameWithPlayers(1, 5);
        // Check that there are 18 of each vegetable
        ArrayList<Integer> vegetableCounts = countPilar();

        assertEquals(18, vegetableCounts.get(0));
        assertEquals(18, vegetableCounts.get(1));
        assertEquals(18, vegetableCounts.get(2));
        assertEquals(18, vegetableCounts.get(3));
        assertEquals(18, vegetableCounts.get(4));
        assertEquals(18, vegetableCounts.get(5));

        assertEquals(108, vegetableCounts.get(0) + vegetableCounts.get(1) + vegetableCounts.get(2) + vegetableCounts.get(3) + vegetableCounts.get(4) + vegetableCounts.get(5));
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
    // c. Check that point card sides are visible

    
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
        if (pileSize < 1) {
            fail("Pile size is too small");
        }
        for(Pile pile : pileManager.getPiles()) {
            if(pile.getPileSize() == pileSize) {
            } else if (pile.getPileSize() == pileSize + 1) { 
            } else if (pile.getPileSize() == pileSize - 1) {
            } else {
                fail("Pile sizes are not roughly equal");
            }
        }
    }

    @Test
    void testRule4c() {
        for(Pile pile : pileManager.getPiles()) {
            for(int i = 0; i < pile.getPileSize(); i++) {
                assertTrue(pile.getCards().get(i).isPointSideUp(), "Point card side should be visible");
            }
        }
    }

    // Rule 5: Flip over two cards from each draw pile to form the vegetable market.
    @Test
    void testRule5() {
        for(Pile pile : pileManager.getPiles()) {
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
        assertTrue(uniqueStartPlayers.size() > 2, "Starting player should be different for each game");
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

        boolean result = humanActions.flipCriteriaCard("0");
        assertFalse(humanPlayer.getHand().get(0).isPointSideUp(), "Point card should be turned to veggie side");

        humanActions.flipCriteriaCard("0");
        assertFalse(humanPlayer.getHand().get(0).isPointSideUp(), "Veggie card should not be turned to point side");

    }

    // Rule 8: Not implemented for bots

    // Rule 9: Show the hand to the other players.
    @Test
    void testRule9DisplayHandTest() {
        createCards();
        IHumanPlayer humanPlayer = (IHumanPlayer) gameState.getPlayer(0);
        humanPlayer.addCard(cardsArray.get(0));
        humanPlayer.addCard(cardsArray.get(17));
        HandDisplay handDisplay = new HandDisplay();
        String hand = handDisplay.displayHand(humanPlayer.getHand(), gameState);
        System.out.println(hand);
        assertTrue(hand.contains("MOST LETTUCE = 10"), "Hand should be shown to other players");
        assertTrue(hand.contains("TOMATO"), "Hand should be shown to other players");
        // assertTrue(humanPlayer.getHand().get(0).isPointSideUp(), "Hand should be shown to other players");
    }

    @Test
    void testRule9DisplayToOther() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        BotPlayer botPlayer = (BotPlayer) gameState.getPlayer(1);
        SendMessage sendMessage = new SendMessage();
        HandDisplay handDisplay = new HandDisplay();
        String message = "Player " + botPlayer.getPlayerID() + "'s hand is now: \n"+handDisplay.displayHand(botPlayer.getHand(), gameState)+"\n";
        
        sendMessage.sendToAllPlayers(message, gameState.getPlayers());	

        // Trim to remove leading/trailing whitespace and newlines
        assertEquals(outputStream.toString().trim(), message.trim());
    }

    // Rule 10: Replace the market with cards from the top of the corresponding point card draw pile
    @Test
    void testRule10() {
        for(Pile pile : pileManager.getPiles()) {
            int pileSize = pile.getPileSize();
            Card card1 = pile.buyMarketCard(0);
            Card card2 = pile.buyMarketCard(1);

            int newPileSize = pile.getPileSize();
            Card newCard1 = pile.buyMarketCard(0);
            Card newCard2 = pile.buyMarketCard(1);
            assertNotEquals(card1, newCard1, "Market should be replaced with new cards");
            assertNotEquals(card2, newCard2, "Market should be replaced with new cards");
            assertEquals(pileSize - 2, newPileSize, "Market should be replaced with new cards from the top of the corresponding pile");
        }
    }

    // Rule 11: If a draw pile runs out of cards, then draw cards from the bottom of the draw pile with the most cards instead.
    @Test
    void testRule11() {
        ArrayList<Pile> piles = pileManager.getPiles();
        Pile pile = piles.get(0);
        int pileSize = pile.getPileSize();
        for(int i = 0; i < pileSize; i++) {
            pile.buyMarketCard(0);
        }
        int newPileSize = pile.getPileSize();
        assertEquals(0, newPileSize, "Pile should be empty");
        Card card1 = pile.getMarketCard(0);
        assertNotEquals(card1, null, "Should be card In market after drawing last card from empty pile");
        int biggestPileIndex = pileManager.getBiggestPileIndex(0);
        int biggestPileSize = piles.get(biggestPileIndex).getPileSize();
        pile.buyMarketCard(0);
        int biggestPileNewSize = piles.get(biggestPileIndex).getPileSize();
        Card card2 = pile.getMarketCard(0);
        assertNotEquals(card2, null, "Should be card In market after drawing from biggest pile");
        assertEquals(biggestPileSize - 1, biggestPileNewSize, "Card should be drawn from the bottom of the pile with the most cards");
    }


    // Rule 12: Continue step 7-12 
    // a. for the next player 
    // b. until there are no more cards in the Point Salad Market
    @Test
    void testRule12a() {
        setupGameWithPlayers(0, 5);
        gameState.setCurrentPlayer(0);
        SaladGameLogic.gameLoop(gameState, 1);
        int currentPlayer = gameState.getCurrentPlayer();
        assertNotEquals(0, currentPlayer, "Game should go to the next player");
    }

    @Test
    void testRule12b() {
        setupGameWithPlayers(0, 2);
        gameState.setCurrentPlayer(0);
        //only exists max 108 cards in the deck so the game should end before 200 turns
        SaladGameLogic.gameLoop(gameState, 200);
        for(Pile pile : pileManager.getPiles()) {
            assertTrue(pile.isEmpty(), "Pile " + pileManager.getPiles().indexOf(pile) +" not empty has size"+ pile.getPileSize());
            assertTrue(null == pile.getMarketCard(0), "Market" + pileManager.getPiles().indexOf(pile) + " index 0 not null");
            assertTrue(null == pile.getMarketCard(1), "Market" + pileManager.getPiles().indexOf(pile) + " index 1 not null");
        }
    }

    // Rule 13: Calculate the score for each player according to the point cards in hand.


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