package main.game;

import java.util.Scanner;

import main.game.display.HandDisplay;
import main.game.display.SendMessage;
import main.game.gamelogic.PointSaladGame;
import main.game.network.Client;
import main.game.network.Server;
import main.game.piles.SetupPiles;
import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;
import main.game.score.CalculatePoints;
import main.game.setupgame.CreatePlayers;
import main.game.setupgame.GameState;
import main.game.setupgame.ISettings;
import main.game.setupgame.SaladSettings;

import java.util.ArrayList;

public class PointGame {
    private Scanner in;

    public PointGame(String[] args) {
        String gameMode = "";
        try {
            this.in = new Scanner(System.in);
        } catch (Exception s) {
            s.printStackTrace();
        }
        if (args.length == 0) {
            gameMode = gameMode();
            GameState gameState = new GameState(gameMode);
            gameState.setSettings(selectSettings(gameState));
            System.out.println("Game mode: " + gameMode);
            selectPlayers(gameState);
            
            try {
                Server server = new Server(gameState);
                new CreatePlayers(gameState, server);
                // Server server = new Server(gameState);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SetupPiles(gameState);
            //set Starting player
            // gameState.setCurrentPlayer((int) (Math.random() * (gameState.getPlayers().size())));
            gameState.setCurrentPlayer(gameState.getSettings().startingPlayerRule(gameState.getNumPlayers()));
            PointSaladGame game = new PointSaladGame(gameState);
            game.gameLoop(gameState);
            endGame(gameState);

            


        } else {
            //check if args[0] is a String (ip address) or an integer (number of players)
            // fixa så man direkt kan skriva in game mode och antal spelare och bots
			if(args[0].matches("\\d+")) {
                // gameMode = String.valueOf(args[2]);
                // numberPlayers = Integer.parseInt(args[0]);
				// numberOfBots = Integer.parseInt(args[1]);
			}
			else {
                try {
                    Client.connectToServer(args[0]);
				} catch (Exception e) {
                    e.printStackTrace();
				}
			}
		}
        

    }

    private ISettings selectSettings(GameState gameState) {
        if (gameState.getGameMode().equals("PointSalad")) {
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
            // try (Scanner in = new Scanner(System.in)) {
            //     mode = in.nextLine();
            // }
            if (mode.equals("1")) {
                return "PointSalad";
            } else if (mode.equals("E")) {
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please try again.");
            }

        }
    }

    private void endGame(GameState gameState) {
        SendMessage sendToAll = new SendMessage();
        HandDisplay handDisplay = new HandDisplay();
        ArrayList<IPlayer> players = gameState.getPlayers();
        CalculatePoints calculatePoints = new CalculatePoints();
		sendToAll.sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"), players);
        calculatePoints.scorePoints(gameState);
		for(IPlayer player : players) {

			sendToAll.sendToAllPlayers("Player " + player.getPlayerID() + "'s hand is: \n"+ handDisplay.displayHand(player.getHand(), gameState), players);
			sendToAll.sendToAllPlayers("\nPlayer " + player.getPlayerID() + "'s score is: " + player.getScore(), players);
		}

		int maxScore = 0;
		int playerID = 0;
		for(IPlayer player : players) {
			if(player.getScore() > maxScore) {
				maxScore = player.getScore();
				playerID = player.getPlayerID();
			}
		}
		for(IPlayer player : players) {
			if(player.getPlayerID() == playerID ) {
                String winnerMessage = "\\nCongratulations! You are the winner with a score of " + maxScore;
                if (player instanceof IHumanPlayer) {
                    IHumanPlayer humanPlayer = (IHumanPlayer) player;
                    humanPlayer.sendMessage(winnerMessage);   
                }
			} else {
                String looserMessage = "\nSorry, you lost. The winner is player " + playerID + " with a score of " + maxScore;
                if (player instanceof IHumanPlayer) {
                    IHumanPlayer humanPlayer = (IHumanPlayer) player;
                    humanPlayer.sendMessage(looserMessage);   
                }
			}
		}
    }

    private void selectPlayers(GameState gameState) {
        int numberPlayers = 0;
        int numberOfBots = 0;
        int maxNumberOfPlayers = gameState.getSettings().getMaxPlayers();
        int minNumberOfPlayers = gameState.getSettings().getMinPlayers();
        
        // try (Scanner in = new Scanner(System.in)) {
        
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
                    break;
                } 

            }
        }
        // }
    }

    public static void main(String[] args) {
        try {
            new PointGame (args);
            // game.play();
            // new PointGame (String[] args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
