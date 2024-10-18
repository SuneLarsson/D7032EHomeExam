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
import main.game.players.IPlayer;
import main.game.players.actions.SaladBotActions;
import main.game.players.actions.SaladHumanActions;
import main.game.setupgame.CreatePlayers;
import main.game.setupgame.GameState;
import main.game.setupgame.SaladSettings;
import main.game.score.*;

import static org.junit.jupiter.api.Assertions.*;


import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PointSaladScoreTest {
    private GameState gameState;
    private SaladGameLogic SaladGameLogic;
    private PileManager pileManager;
    private Server server;
    private ArrayList<Card> pointCards = new ArrayList<>();
    private ArrayList<Card> vegetableCards = new ArrayList<>();

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
        vegetableCards.add(new SaladCard("PEPPER", "MOST ONION = 10", false));
        vegetableCards.add(new SaladCard("LETTUCE", "MOST ONION = 10", false));
        vegetableCards.add(new SaladCard("CARROT", "MOST ONION = 10", false));
        vegetableCards.add(new SaladCard("CABBAGE", "MOST ONION = 10", false));
        vegetableCards.add(new SaladCard("ONION", "MOST ONION = 10", false));
        vegetableCards.add(new SaladCard("TOMATO", "MOST ONION = 10", false));
    }

// 13. Calculate the score for each player according to the point cards in hand.
// 14. Announce the winner with the highest score
    
    @BeforeEach
    void setUp() {
        gameState = new GameState("PointSalad", new Scanner(System.in));
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
        SaladGameLogic = new SaladGameLogic(gameState);
        pileManager = gameState.getPileManager();
    }

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

    @AfterEach
    void tearDown() {
        gameState = null;
        pileManager = null;
        pointCards = null;
        server.close();
    }

    // If two players are tied for a scoring condition on a card (e.g., most onions), then the player with
    // the point card scores the victory points. 
    // todo check if someone else can get points from this card
    @Test
    void scoreCriteriaID1(){
        // PLAYER 1 has 2 LETTUCE cards and 1 score card for most LETTUCE = 10
        gameState.getPlayers().get(0).addCard(pointCards.get(0));
        gameState.getPlayers().get(0).addCard(vegetableCards.get(1));
        gameState.getPlayers().get(0).addCard(vegetableCards.get(1));
        // PLAYER 2 has 1 LETTUCE card
        gameState.getPlayers().get(1).addCard(vegetableCards.get(1));
        PointSaladCriteria scoreCriteria = new PointSaladCriteria();
        assertEquals(10, scoreCriteria.calculateScore(gameState.getPlayers().get(0), gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
        // Add another 2 LETTUCE card to PLAYER 2
        gameState.getPlayers().get(1).addCard(vegetableCards.get(1));
        gameState.getPlayers().get(1).addCard(vegetableCards.get(1));
        assertEquals(0, scoreCriteria.calculateScore(gameState.getPlayers().get(0), gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
        assertEquals(0, scoreCriteria.calculateScore(gameState.getPlayers().get(1), gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
    }

    @Test
    void scoreCriteriaID2(){
        // PLAYER 1 has 2 PEPPER cards and 1 score card for FEWEST PEPPER = 7
        setupGameWithPlayers(0, 2);
        IPlayer player1 = gameState.getPlayers().get(0);
        IPlayer player2 = gameState.getPlayers().get(1);
        player1.addCard(pointCards.get(1));
        player1.addCard(vegetableCards.get(0));
        player1.addCard(vegetableCards.get(0));
        // PLAYER 2 has 1 PEPPER card
        player2.addCard(vegetableCards.get(0));

        CountResources countResources;
        countResources = new CountResources();
        PointSaladCriteria scoreCriteria = new PointSaladCriteria();
        assertEquals(0, scoreCriteria.calculateScore(player1, gameState.getPlayers(), gameState.getSettings().getCardTypes(), player1.getHand()));
        // Add another 2 PEPPER card to PLAYER 2
        player2.addCard(vegetableCards.get(0));
        player2.addCard(vegetableCards.get(0));

        int player1Cards = countResources.countResource(player1.getHand(), "PEPPER");
        int player2Cards = countResources.countResource(player2.getHand(), "PEPPER");
        // assertEquals(player0, player1);
        System.out.println(gameState.getSettings().getCardTypes());
        PointSaladCriteria scoreCriteria2 = new PointSaladCriteria();
        assertEquals(7, scoreCriteria2.calculateScore(player1, gameState.getPlayers(), gameState.getSettings().getCardTypes(), player1.getHand()));
        // assertEquals(0, scoreCriteria.calculateScore(player2, gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
    }

    @Test
    void scoreCriteriaID3(){
        // PLAYER 1 has 2 CARROT cards and 1 score card for CABBAGE: EVEN=7, ODD=3
        gameState.getPlayers().get(0).addCard(pointCards.get(2));
        gameState.getPlayers().get(0).addCard(vegetableCards.get(2));
        gameState.getPlayers().get(0).addCard(vegetableCards.get(2));
        // PLAYER 2 has 1 CARROT card
        gameState.getPlayers().get(1).addCard(vegetableCards.get(2));
        PointSaladCriteria scoreCriteria = new PointSaladCriteria();
        assertEquals(7, scoreCriteria.calculateScore(gameState.getPlayers().get(0), gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
        // Add another CARROT card to PLAYER 2
        gameState.getPlayers().get(1).addCard(vegetableCards.get(2));
        assertEquals(0, scoreCriteria.calculateScore(gameState.getPlayers().get(0), gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
        assertEquals(0, scoreCriteria.calculateScore(gameState.getPlayers().get(1), gameState.getPlayers(), gameState.getSettings().getCardTypes(), null));
    }

}
