package main.game.testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import main.game.card.Card;
import main.game.card.SaladCard;
import main.game.display.HandDisplay;
import main.game.display.SendMessage;
import main.game.gamelogic.SaladGameLogic;
import main.game.network.Server;
import main.game.piles.SetupPiles;
import main.game.players.BotPlayer;
import main.game.players.IPlayer;
import main.game.setupgame.CreatePlayers;
import main.game.setupgame.GameState;
import main.game.setupgame.SaladSettings;
import main.game.score.*;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


// 13. Calculate the score for each player according to the point cards in hand.
// 14. Announce the winner with the highest score

public class PointSaladScoreTest {
    private GameState gameState;
    private SaladGameLogic saladGameLogic;
    private Server server;
    private ArrayList<Card> pointCards = new ArrayList<>();
    private ArrayList<Card> vegetableCards = new ArrayList<>();
    private PointSaladCriteria scoreCriteria;
    private ArrayList<Card> tempHand;
    private IPlayer player0;
    private IPlayer player1;

    @BeforeEach
    private void createPointCards(){
        // ID 1
        pointCards.add(new SaladCard("PEPPER", "MOST LETTUCE = 10", true));
        // ID 2
        pointCards.add(new SaladCard("LETTUCE", "FEWEST PEPPER = 7", true));
        // ID 3
        pointCards.add(new SaladCard("CARROT", "CABBAGE: EVEN=7, ODD=3", true));
        // ID 4
        pointCards.add(new SaladCard("CABBAGE", "2 / CARROT", true));
        // ID 5
        pointCards.add(new SaladCard("ONION", "TOMATO + TOMATO = 5", true));
        // ID 6, 7
        pointCards.add(new SaladCard("TOMATO", "CARROT + LETTUCE = 5", true));
        // ID 8, 9
        pointCards.add(new SaladCard("PEPPER", "1 / LETTUCE,  1 / ONION", true));
        // ID 10
        pointCards.add(new SaladCard("LETTUCE", "3 / PEPPER,  -2 / CABBAGE", true));
        // ID 11
        pointCards.add(new SaladCard("CARROT", "CABBAGE + CABBAGE + CABBAGE = 8", true));
        // ID 12, 13
        pointCards.add(new SaladCard("ONION", "ONION + TOMATO + PEPPER = 8", true));
        // ID 14, 15, 16, 17
        pointCards.add(new SaladCard("CABBAGE", "2/CARROT,  1/PEPPER,  -2/CABBAGE", true));
        // ID 18_1
        pointCards.add(new SaladCard("PEPPER", "MOST TOTAL VEGETABLE = 10", true));
        // ID 18_2
        pointCards.add(new SaladCard("LETTUCE", "FEWEST TOTAL VEGETABLE = 7", true));
        // ID 18_3
        pointCards.add(new SaladCard("CARROT", "5 / VEGETABLE TYPE >=3", true));
        // ID 18_4
        pointCards.add(new SaladCard("CABBAGE", "5 / MISSING VEGETABLE TYPE", true));
        // ID 18_5
        pointCards.add(new SaladCard("ONION", "3 / VEGETABLE TYPE >=2", true));
        // ID 18_6
        pointCards.add(new SaladCard("TOMATO", "COMPLETE SET = 12", true));

    }

    @BeforeEach
    private void createVegetableCards(){
        vegetableCards.add(new SaladCard("PEPPER", "MOST ONION = 10", false)); //0
        vegetableCards.add(new SaladCard("LETTUCE", "MOST ONION = 10", false)); //1
        vegetableCards.add(new SaladCard("CARROT", "MOST ONION = 10", false)); //2
        vegetableCards.add(new SaladCard("CABBAGE", "MOST ONION = 10", false)); //3
        vegetableCards.add(new SaladCard("ONION", "MOST ONION = 10", false)); //4
        vegetableCards.add(new SaladCard("TOMATO", "MOST ONION = 10", false)); //5
    }
        

    @BeforeEach
    void setUp() {
        gameState = new GameState("POINTSALAD", new Scanner(System.in));
        gameState.setSettings(new SaladSettings());
        gameState.setNumPlayers(1);
        gameState.setNumberOfBots(5);
        try {
            server = new Server(gameState);
            new CreatePlayers(gameState, server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new SetupPiles(gameState);
        gameState.setCurrentPlayer(gameState.getSettings().startingPlayerRule(gameState.getNumPlayers()));
        saladGameLogic = new SaladGameLogic(gameState);  
        scoreCriteria = new PointSaladCriteria();
        tempHand = new ArrayList<>();
        player0 = gameState.getPlayers().get(0);
        player1 = gameState.getPlayers().get(1);
    }

    private void setupGameWithPlayers(int numPlayers, int numBots) {
        gameState = null;
        scoreCriteria = null;
        tempHand = null;
        player0 = null;
        player1 = null;
        server.close();

        gameState = new GameState("POINTSALAD", new Scanner(System.in));
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
        saladGameLogic = new SaladGameLogic(gameState);
        scoreCriteria = new PointSaladCriteria();
        tempHand = new ArrayList<>();
        player0 = gameState.getPlayers().get(0);
        player1 = gameState.getPlayers().get(1);
    }

    @AfterEach
    void tearDown() {
        gameState = null;
        pointCards = null;
        vegetableCards = null;
        scoreCriteria = null;
        tempHand = null;
        player0 = null;
        player1 = null;
        server.close();

    }

    // If two players are tied for a scoring condition on a card (e.g., most onions), then the player with
    // the point card scores the victory points. 
    @Test
    void scoreCriteriaID1(){
        // Player 0 has 2 LETTUCE cards and 1 score card for most LETTUCE = 10
        player0.addCard(pointCards.get(0));
        player0.addCard(vegetableCards.get(1));
        player0.addCard(vegetableCards.get(1));
        // PLAYER 2 has 1 LETTUCE card
        player1.addCard(vegetableCards.get(1));
        assertEquals(10, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 LETTUCE cards and 1 score card for most LETTUCE = 10 and Player 2 has 1 LETTUCE card");
        // Add another 2 LETTUCE card to PLAYER 2
        player1.addCard(vegetableCards.get(1));
        player1.addCard(vegetableCards.get(1));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 LETTUCE cards and 1 score card for most LETTUCE = 10 and Player 2 has 3 LETTUCE cards");
        assertEquals(0, scoreCriteria.calculateScore(player1, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 2 has 3 LETTUCE cards no score card");
    }

    @Test
    void scoreCriteriaID2(){
        // Player 0 has 2 PEPPER cards and 1 score card for FEWEST PEPPER = 7
        setupGameWithPlayers(0, 2);
        player0.addCard(pointCards.get(1));
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 PEPPER cards and 1 score card for FEWEST PEPPER = 7");
        player0.addCard(vegetableCards.get(0));
        player0.addCard(vegetableCards.get(0));
        // PLAYER 1 has 1 PEPPER card
        player1.addCard(vegetableCards.get(0));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 PEPPER cards and 1 score card for FEWEST PEPPER = 7 and Player 2 has 1 PEPPER card");
        // Add another 2 PEPPER card to PLAYER 2
        player1.addCard(vegetableCards.get(0));
        player1.addCard(vegetableCards.get(0));

        // assertEquals(player0, player1);
        System.out.println(gameState.getPlayers().size());
        // PointSaladCriteria scoreCriteria2 = new PointSaladCriteria();
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 PEPPER cards and 1 score card for FEWEST PEPPER = 7 and Player 2 has 3 PEPPER cards");
        // assertEquals(0, scoreCriteria.calculateScore(player2, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
    }

    @Test
    void scoreCriteriaID3(){
        // Player 0 has 0 CABBAGE cards and 1 score card for CABBAGE: EVEN=7, ODD=3
        player0.addCard(pointCards.get(2));
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 CABBAGE cards and 1 score card for CABBAGE: EVEN=7, ODD=3");
        player0.addCard(vegetableCards.get(3));
        assertEquals(3, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CABBAGE cards and 1 score card for CABBAGE: EVEN=7, ODD=3");
        player0.addCard(vegetableCards.get(3));
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 CABBAGE cards and 1 score card for CABBAGE: EVEN=7, ODD=3");
    }

    @Test
    void scoreCriteriaID4(){
        // Player 0 has 2 CARROT cards and 1 score card for 2 / CARROT
        player0.addCard(pointCards.get(3));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 CARROT cards and 1 score card for 2 / CARROT");
        player0.addCard(vegetableCards.get(2));
        assertEquals(2, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CARROT cards and 1 score card for 2 / CARROT");
        for(int i = 1; i < 18; i++){
            player0.addCard(vegetableCards.get(2));
        }
        assertEquals(36, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 18 CARROT cards and 1 score card for 2 / CARROT");
    }

    @Test
    void scoreCriteriaID5(){
        // Player 0 has 2 TOMATO cards and 1 score card for TOMATO + TOMATO = 5
        player0.addCard(pointCards.get(4));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 TOMATO cards and 1 score card for TOMATO + TOMATO = 5");
        player0.addCard(vegetableCards.get(5));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 TOMATO cards and 1 score card for TOMATO + TOMATO = 5");
        player0.addCard(vegetableCards.get(5));
        assertEquals(5, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 TOMATO cards and 1 score card for TOMATO + TOMATO = 5");
        for(int i = 0; i < 2; i++){
            player0.addCard(vegetableCards.get(5));
        }
        assertEquals(10, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 4 TOMATO cards and 1 score card for TOMATO + TOMATO = 5");
    }

    @Test
    void scoreCriteriaID6And7(){
        // Player 0 has 2 CARROT cards and 2 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5
        player0.addCard(pointCards.get(5));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 CARROT cards and 0 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5");
        player0.addCard(vegetableCards.get(2));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CARROT cards and 0 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5");
        player0.addCard(vegetableCards.get(1));
        assertEquals(5, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CARROT cards and 1 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5");
        player0.addCard(vegetableCards.get(2));
        player0.addCard(vegetableCards.get(1));
        assertEquals(10, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 CARROT cards and 2 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5");
    }

    @Test
    void scoreCriteriaID8And9(){
        // Player 0 has 1 LETTUCE cards and 1 ONION cards and 1 score card for 1 / LETTUCE,  1 / ONION
        player0.addCard(pointCards.get(6));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 LETTUCE cards and 0 ONION cards and 1 score card for 1 / LETTUCE,  1 / ONION");
        player0.addCard(vegetableCards.get(1));
        assertEquals(1, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 LETTUCE cards and 0 ONION cards and 1 score card for 1 / LETTUCE,  1 / ONION");
        tempHand.add(pointCards.get(6));
        tempHand.add(vegetableCards.get(4));
        assertEquals(1, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), tempHand), "TempHand has 0 LETTUCE cards and 1 ONION cards and 1 score card for 1 / LETTUCE,  1 / ONION");
        player0.addCard(vegetableCards.get(4));
        assertEquals(2, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 LETTUCE cards and 1 ONION cards and 1 score card for 1 / LETTUCE,  1 / ONION");
        player0.addCard(vegetableCards.get(1));
        player0.addCard(vegetableCards.get(4));
        assertEquals(4, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 LETTUCE cards and 2 ONION cards and 1 score card for 1 / LETTUCE,  1 / ONION");  
    }

    @Test
    void scoreCriteriaID10(){
        // Player 0 has 3 PEPPER cards and 1 CABBAGE cards and 1 score card for 3 / PEPPER,  -2 / CABBAGE
        player0.addCard(pointCards.get(7));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 PEPPER cards and 0 CABBAGE cards and 1 score card for 3 / PEPPER,  -2 / CABBAGE");
        player0.addCard(vegetableCards.get(0));
        assertEquals(3, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 PEPPER cards and 0 CABBAGE cards and 1 score card for 3 / PEPPER,  -2 / CABBAGE");
        player0.addCard(vegetableCards.get(0));
        player0.addCard(vegetableCards.get(0));
        assertEquals(9, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 3 PEPPER cards and 0 CABBAGE cards and 1 score card for 3 / PEPPER,  -2 / CABBAGE");
        player0.addCard(vegetableCards.get(3));
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 3 PEPPER cards and 1 CABBAGE cards and 1 score card for 3 / PEPPER,  -2 / CABBAGE");
        player0.addCard(vegetableCards.get(3));
        assertEquals(5, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 3 PEPPER cards and 2 CABBAGE cards and 1 score card for 3 / PEPPER,  -2 / CABBAGE");
        tempHand.add(pointCards.get(7));
        tempHand.add(vegetableCards.get(3));
        assertEquals(-2, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), tempHand), "TempHand has 3 PEPPER cards and 2 CABBAGE cards and 1 score card for 3 / PEPPER,  -2 / CABBAGE");
    }

    @Test
    void scoreCriteriaID11(){
        // Player 0 has 3 CABBAGE cards and 1 score card for CABBAGE + CABBAGE + CABBAGE = 8
        player0.addCard(pointCards.get(8));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 CABBAGE cards and 1 score card for CABBAGE + CABBAGE + CABBAGE = 8");
        player0.addCard(vegetableCards.get(3));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CABBAGE cards and 1 score card for CABBAGE + CABBAGE + CABBAGE = 8");
        player0.addCard(vegetableCards.get(3));
        player0.addCard(vegetableCards.get(3));
        assertEquals(8, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 3 CABBAGE cards and 1 score card for CABBAGE + CABBAGE + CABBAGE = 8");
        player0.addCard(vegetableCards.get(3));
        assertEquals(8, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 4 CABBAGE cards and 1 score card for CABBAGE + CABBAGE + CABBAGE = 8");
        player0.addCard(vegetableCards.get(3));
        player0.addCard(vegetableCards.get(3));
        assertEquals(16, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 6 CABBAGE cards and 1 score card for CABBAGE + CABBAGE + CABBAGE = 8");
    }

    @Test
    void scoreCriteriaID12And13(){
        // Player 0 has 2 ONION cards and 1 TOMATO cards and 1 PEPPER cards and 1 score card for ONION + TOMATO + PEPPER = 8
        player0.addCard(pointCards.get(9));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 ONION cards and 0 TOMATO cards and 0 PEPPER cards and 1 score card for ONION + TOMATO + PEPPER = 8");
        player0.addCard(vegetableCards.get(4));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 ONION cards and 0 TOMATO cards and 0 PEPPER cards and 1 score card for ONION + TOMATO + PEPPER = 8");
        player0.addCard(vegetableCards.get(5));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 ONION cards and 1 TOMATO cards and 0 PEPPER cards and 1 score card for ONION + TOMATO + PEPPER = 8");
        player0.addCard(vegetableCards.get(0));
        assertEquals(8, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 ONION cards and 1 TOMATO cards and 1 PEPPER cards and 1 score card for ONION + TOMATO + PEPPER = 8");
        player0.addCard(vegetableCards.get(0));
        player0.addCard(vegetableCards.get(4));
        player0.addCard(vegetableCards.get(5));
        assertEquals(16, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 ONION cards and 2 TOMATO cards and 2 PEPPER cards and 1 score card for ONION + TOMATO + PEPPER = 8");
    }

    @Test
    void scoreCriteriaID14To17(){
        // Player 0 has 2 CARROT cards and 1 PEPPER cards and 1 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE
        player0.addCard(pointCards.get(10));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 CARROT cards and 0 PEPPER cards and 0 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
        player0.addCard(vegetableCards.get(2));
        assertEquals(2, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CARROT cards and 0 PEPPER cards and 0 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
        player0.addCard(vegetableCards.get(0));
        assertEquals(3, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CARROT cards and 1 PEPPER cards and 0 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
        player0.addCard(vegetableCards.get(0));
        player0.addCard(vegetableCards.get(2));
        assertEquals(6, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 CARROT cards and 2 PEPPER cards and 0 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
        player0.addCard(vegetableCards.get(3));
        assertEquals(4, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 CARROT cards and 2 PEPPER cards and 1 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
    }

    @Test
    void scoreCriteriaID14To17PartCriteria(){
        tempHand.add(pointCards.get(10));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), tempHand), "TempHand has 0 CARROT cards and 0 PEPPER cards and 0 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
        tempHand.add(vegetableCards.get(2));
        assertEquals(2, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), tempHand), "TempHand has 1 CARROT cards and 0 PEPPER cards and 0 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
        tempHand=null;
        tempHand = new ArrayList<>();
        tempHand.add(pointCards.get(10));
        tempHand.add(vegetableCards.get(0));
        assertEquals(1, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), tempHand), "TempHand has 0 CARROT cards and 1 PEPPER cards and 0 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
        tempHand=null;
        tempHand = new ArrayList<>();
        tempHand.add(pointCards.get(10));
        tempHand.add(vegetableCards.get(3));
        assertEquals(-2, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), tempHand), "TempHand has 0 CARROT cards and 0 PEPPER cards and 1 CABBAGE cards and 1 score card for 2/CARROT,  1/PEPPER,  -2/CABBAGE");
    }

    @Test
    void scoreCriteriaID18_1(){
        // Player 0 has score card for MOST TOTAL VEGETABLE = 10
        player0.addCard(pointCards.get(11));
        player0.addCard(vegetableCards.get(0));
        assertEquals(10, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 vegetable cards and 1 score card for MOST TOTAL VEGETABLE = 10");
        player1.addCard(vegetableCards.get(2));
        assertEquals(10, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 vegetable cards and 1 score card for MOST TOTAL VEGETABLE = 10 and Player 1 has 1 vegetable cards");
        player1.addCard(vegetableCards.get(0));
        // player0.addCard(vegetableCards.get(0));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 vegetable cards and 1 score card for MOST TOTAL VEGETABLE = 10 and Player 1 has 2 vegetable cards");
        player0.addCard(vegetableCards.get(0));
        player0.addCard(vegetableCards.get(2));
        assertEquals(10, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 3 vegetable cards and 1 score card for MOST TOTAL VEGETABLE = 10 and Player 1 has 2 vegetable cards");
    }

    @Test
    void scoreCriteriaID18_2(){
        // Player 0 has score card for FEWEST TOTAL VEGETABLE = 7
        player0.addCard(pointCards.get(12));
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 vegetable cards and 1 score card for FEWEST TOTAL VEGETABLE = 7");
        player0.addCard(vegetableCards.get(0));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 vegetable cards and 1 score card for FEWEST TOTAL VEGETABLE = 7, Player 1-5 have 0 vegetable cards");
        for(int i = 1; i < 6; i++){
            gameState.getPlayer(i).addCard(vegetableCards.get(0));
            gameState.getPlayer(i).addCard(vegetableCards.get(5));
        }
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 vegetable cards and 1 score card for FEWEST TOTAL VEGETABLE = 7, Player 1-5 have 2 vegetable cards each");
    }

    @Test
    void scoreCriteriaID18_3(){
        // Player 0 has score card for 5 / VEGETABLE TYPE >=3
        player0.addCard(pointCards.get(13));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 vegetable cards and 1 score card for 5 / VEGETABLE TYPE >=3");
        for(int i = 0; i < 3; i++){
            player0.addCard(vegetableCards.get(0));
        }
        assertEquals(5, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 3 peppers and 1 score card for 5 / VEGETABLE TYPE >=3");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                player0.addCard(vegetableCards.get(i));
            }
            
        }
        assertEquals(15, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 6 peppers, 3 lettuce, 3 carrot and 1 score card for 5 / VEGETABLE TYPE >=3");
        for(int i = 0; i < 3; i++){
            for(int j = 3; j < 6; j++){
                player0.addCard(vegetableCards.get(j));
            }
            
        }
        assertEquals(30, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 6 peppers, 3 lettuce, 3 carrot, 3 cabbage, 3 onion, 3 tomato and 1 score card for 5 / VEGETABLE TYPE >=3");
    }

    @Test
    void scoreCriteriaID18_4(){
        // Player 0 has score card for 5 / MISSING VEGETABLE TYPE
        player0.addCard(pointCards.get(14));
        assertEquals(30, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 vegetable cards and 1 score card for 5 / MISSING VEGETABLE TYPE");
        for(int i = 0; i < 5; i++){
            player0.addCard(vegetableCards.get(i));
        }
        assertEquals(5, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 5 different vegetable cards and 1 score card for 5 / MISSING VEGETABLE TYPE");
        player0.addCard(vegetableCards.get(5));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has all 6 different vegetable cards and 1 score card for 5 / MISSING VEGETABLE TYPE");
    }

    @Test
    void scoreCriteriaID18_5(){
        // Player 0 has score card for 3 / VEGETABLE TYPE >=2
        player0.addCard(pointCards.get(15));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 vegetable cards and 1 score card for 3 / VEGETABLE TYPE >=2");
        for(int i = 0; i < 2; i++){
            player0.addCard(vegetableCards.get(0));
        }
        assertEquals(3, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 peppers and 1 score card for 3 / VEGETABLE TYPE >=2");
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                player0.addCard(vegetableCards.get(j));
            }
            
        }
        assertEquals(9, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 4 peppers, 2 lettuce, 2 carrot and 1 score card for 3 / VEGETABLE TYPE >=2");
        for(int i = 0; i < 2; i++){
            for(int j = 3; j < 6; j++){
                player0.addCard(vegetableCards.get(j));
            }
            
        }
        assertEquals(18, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 4 peppers, 2 lettuce, 2 carrot, 2 cabbage, 2 onion, 2 tomato and 1 score card for 3 / VEGETABLE TYPE >=2");
    }

    @Test
    void scoreCriteriaID18_6(){
        // Player 0 has score card for COMPLETE SET = 12
        player0.addCard(pointCards.get(16));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 vegetable cards and 1 score card for COMPLETE SET = 12");
        for(int i = 0; i < 6; i++){
            player0.addCard(vegetableCards.get(i));
        }
        assertEquals(12, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 6 different vegetable cards and 1 score card for COMPLETE SET = 12");
        for(int i = 0; i < 6; i++){
            player0.addCard(vegetableCards.get(i));
        }
        assertEquals(12, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 of each 6 different vegetable cards and 1 score card for COMPLETE SET = 12");
    }

    @Test
    void scoreMultipleCriterias(){
        // Player 0 has 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / carrot
        player0.addCard(pointCards.get(5));
        player0.addCard(pointCards.get(3));
        assertEquals(0, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 0 CARROT cards and 0 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / CARROT");
        player0.addCard(vegetableCards.get(2));
        assertEquals(2, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CARROT cards and 0 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / CARROT");
        player0.addCard(vegetableCards.get(1));
        assertEquals(7, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 1 CARROT cards and 1 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / CARROT");
        player0.addCard(vegetableCards.get(2));
        player0.addCard(vegetableCards.get(1));
        assertEquals(14, scoreCriteria.calculateScore(player0, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null), "Player 0 has 2 CARROT cards and 2 LETTUCE cards and 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / CARROT");
    }

    // Rule 14. Announce the winner with the highest score
    @Test
    void announceYouWinner(){
        // Player 0 has 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / carrot
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        player0.addCard(pointCards.get(5));
        player0.addCard(pointCards.get(3));
        player0.addCard(vegetableCards.get(2));
        player0.addCard(vegetableCards.get(2));
        player0.addCard(vegetableCards.get(1));
        player0.addCard(vegetableCards.get(1));

        for(int i = 1; i < 6; i++){
            gameState.getPlayer(i).addCard(pointCards.get(i));
            for(int j = 0; j < 6; j++){
                gameState.getPlayer(i).addCard(vegetableCards.get(j));
            }
        }
        saladGameLogic.endGame(gameState);
        String expectedWinnerMessage = "Congratulations! You are the winner with a score of 14";
        assertTrue(outputStream.toString().contains(expectedWinnerMessage));
        // assertEquals(outputStream.toString(), expectedWinnerMessage);
    }

    @Test
    void announceYouLoser(){
        // Player 0 has 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / carrot
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        for(int i = 1; i < 6; i++){
            gameState.getPlayer(i).addCard(pointCards.get(i));
            for(int j = 0; j < 6; j++){
                gameState.getPlayer(i).addCard(vegetableCards.get(j));
                gameState.getPlayer(i).addCard(vegetableCards.get(j));
                gameState.getPlayer(i).addCard(vegetableCards.get(j));
            }
        }
        saladGameLogic.endGame(gameState);
        String expectedLoserMessage = "Sorry, you lost. The winner is player 5 with a score of 15";
        assertTrue(outputStream.toString().contains(expectedLoserMessage));
    }

    // if two players are tied for a scoring condition on a card (e.g., most onions), then the player last in turn order wins the tie.
    @Test
    void anonunceTieWinner(){
        // Player 0 has 1 score card for CARROT + LETTUCE = 5 and 1 score card for 2 / carrot
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        player0.addCard(pointCards.get(5));
        player0.addCard(pointCards.get(3));
        player0.addCard(vegetableCards.get(2));
        player0.addCard(vegetableCards.get(2));
        player0.addCard(vegetableCards.get(1));
        player0.addCard(vegetableCards.get(1));


        player1.addCard(pointCards.get(5));
        player1.addCard(pointCards.get(3));
        player1.addCard(vegetableCards.get(2));
        player1.addCard(vegetableCards.get(2));
        player1.addCard(vegetableCards.get(1));
        player1.addCard(vegetableCards.get(1));

        gameState.setStartPlayer(0);

        saladGameLogic.endGame(gameState);
        String expectedLoserMessage = "Sorry, you lost. The winner is player 1 with a score of 14";
        // assertEquals(outputStream.toString(), expectedLoserMessage);
        assertTrue(outputStream.toString().contains(expectedLoserMessage));

        gameState.setStartPlayer(1);
        saladGameLogic.endGame(gameState);
        String expectedWinnerMessage = "Congratulations! You are the winner with a score of 14";
        // assertEquals(outputStream.toString(), expectedWinnerMessage);
        assertTrue(outputStream.toString().contains(expectedWinnerMessage));
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
}
