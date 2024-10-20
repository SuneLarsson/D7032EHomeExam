package main.game;

import java.util.Scanner;

import main.game.gamelogic.IGameLogic;
import main.game.gamelogic.SaladGameLogic;
import main.game.network.Client;
import main.game.network.Server;
import main.game.piles.SetupPiles;
import main.game.setupgame.CreatePlayers;
import main.game.setupgame.GameState;
import main.game.setupgame.ISettings;
import main.game.setupgame.SaladSettings;

/**
 * Class that starts the game and sets up the game mode, settings, and players.
 */

public class PointGame {
    private Scanner in;

    public PointGame(String[] args) {
        String gameMode = "";
        GameState gameState;
        try {
            this.in = new Scanner(System.in);
        } catch (Exception s) {
            s.printStackTrace();
        }
        try {
            if (args.length == 0) {
                gameMode = gameMode();
                gameState = new GameState(gameMode, in);
                gameState.setSettings(selectSettings(gameState));
                System.out.println("Game mode: " + gameMode);
                selectPlayers(gameState);
                initGame(gameState);
            } else if (args.length == 1) { 
                try {
                    Client client = new Client(args[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }    
            } else if (args.length == 3) {
                //check if args[0] is a String (ip address) or an integer (number of players)
                if(args[0].matches("\\d+")) {
                    gameMode = String.valueOf(args[2]).toUpperCase();
                    gameState = new GameState(gameMode, in);
                    gameState.setSettings(selectSettings(gameState));
                    gameState.setNumPlayers(Integer.parseInt(args[0]));
                    gameState.setNumberOfBots(Integer.parseInt(args[1]));

                    if (!checkAmountOfPlayers(gameState)){
                        throw new IllegalArgumentException("Invalid number of players. Please try again.");
                    } else {
                        initGame(gameState);
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid setup parameters. Please try again.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
            throw e;
        }
    }

    private void initGame(GameState gameState) {
        try {
            Server server = new Server(gameState);
            new CreatePlayers(gameState, server);
        } catch (Exception e) {
            System.out.println("Error in initGame");
            e.printStackTrace();
        }
        new SetupPiles(gameState);
        gameState.setStartPlayer(gameState.getSettings().startingPlayerRule(gameState.getPlayers().size()));
        IGameLogic game = new SaladGameLogic(gameState);
        game.gameLoop(gameState, gameState.getSettings().getTurnLimit());
        game.endGame(gameState);
        in.close();
    }

    private boolean checkAmountOfPlayers(GameState gameState) {
        int numberPlayers = gameState.getNumPlayers();
        int numberOfBots = gameState.getNumberOfBots();
        if (numberPlayers + numberOfBots > gameState.getSettings().getMaxPlayers()) {
            return false;
        } else if (numberPlayers + numberOfBots < gameState.getSettings().getMinPlayers()) {
            return false;
        }
        return true;
    }

    private ISettings selectSettings(GameState gameState) {
        if (gameState.getGameMode().equals("POINTSALAD")) {
            return new SaladSettings();
        } else {
            System.out.println("Invalid game mode. Please try again.");
        }
        return null;
    }

    private String gameMode() {
        while (true) {
            String mode = "";
            System.out.println("Game modes implemented:\n 1. PointSalad \n E. Exit\n");
            System.out.println("Please enter the game mode: ");
            mode = in.nextLine();
            if (mode.equals("1")) {
                return "POINTSALAD";
            } else if (mode.equals("E")) {
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please try again.");
            }

        }
    }



    private void selectPlayers(GameState gameState) {
        int numberPlayers = 0;
        int numberOfBots = 0;
        int maxNumberOfPlayers = gameState.getSettings().getMaxPlayers();
        int minNumberOfPlayers = gameState.getSettings().getMinPlayers();
     
        while (true) {
            System.out.println("Please enter the number of players (1-" + maxNumberOfPlayers + "): ");
            numberPlayers = in.nextInt();
            if (numberPlayers < 1 || numberPlayers > maxNumberOfPlayers) {
                System.out.println("Invalid number of players. Please try again.");
            } else {
                gameState.setNumPlayers(numberPlayers);
                break;
            }
        }
        while (true) {
            int maxNumberOfBots =  maxNumberOfPlayers - numberPlayers;
            int minimumBots = 0;
            if (numberPlayers == 1 && minNumberOfPlayers != 1) {
                minimumBots = 1;
            } 
            if (maxNumberOfBots < 1) {
                break;
            } else {
                System.out.println("Please enter the number of bots ("+ minimumBots +"-" + maxNumberOfBots + "): ");
                numberOfBots = in.nextInt();
                if (numberOfBots < 0 || numberOfBots > maxNumberOfBots) {
                    System.out.println("Invalid number of bots. Please try again.");
                } else if (numberOfBots + numberPlayers < 2 && minNumberOfPlayers > 1) {
                    System.out.println("Need atleast 2 players. Please select atleast 1 bot.");
                } else {
                    gameState.setNumberOfBots(numberOfBots);
                    in.nextLine();
                    break;
                } 

            }
        }

        if(!checkAmountOfPlayers(gameState)) {
            throw new IllegalArgumentException("Invalid number of players. Please try again.");
        } 
    }

    public static void main(String[] args) {
        try {
            new PointGame(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
