package main.game;

import java.util.Scanner;

import main.game.display.HandDisplay;
import main.game.display.SendMessage;
import main.game.gamelogic.PointSaladGame;
import main.game.network.Client;
import main.game.network.Server;
import main.game.piles.SetPiles;
import main.game.piles.ISetPiles;
import main.game.players.IPlayer;
import main.game.players.LocalPlayer;
import main.game.players.OnlinePlayer;
import main.game.score.PointSaladCriteria;
import main.game.setupgame.GameState;
import java.util.ArrayList;

public class PointGame {

    public PointGame(String[] args) {
        int numberPlayers = 0;
        int numberOfBots = 0;
        String gameMode = "";
        ArrayList<Integer> playersAndBots = new ArrayList<>();
        if (args.length == 0) {
            gameMode = gameMode();
            System.out.println("Game mode: " + gameMode);
            playersAndBots = selectPlayers(gameMode);
            numberPlayers = playersAndBots.get(0);
            numberOfBots = playersAndBots.get(1);
            GameState gameState = new GameState(numberPlayers, numberOfBots, gameMode);
            if (gameMode.equals("PointSalad")) {
                //PointSalad setPiles
                ISetPiles setPiles = new SetPiles(gameState);
                setPiles.createPiles();
            }
            try {
                Server.startServer(gameState);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //set Starting player
            gameState.setCurrentPlayer((int) (Math.random() * (gameState.getPlayers().size())));
            PointSaladGame game = new PointSaladGame(gameState);
            game.gameLoop(gameState);
            endGame(gameState);

            


        } else {
            //check if args[0] is a String (ip address) or an integer (number of players)
            // fixa så man direkt kan skriva in game mode och antal spelare och bots
			if(args[0].matches("\\d+")) {
                numberPlayers = Integer.parseInt(args[0]);
				numberOfBots = Integer.parseInt(args[1]);
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

    private String gameMode() {
        while (true) {
            String mode = "";
            System.out.println("Game modes implemented:\n 1. PointSalad \n E. Exit\n");
            System.out.println("Please enter the game mode: ");
            Scanner in = new Scanner(System.in);
            mode = in.nextLine();
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
        int currentPlayer = gameState.getCurrentPlayer();
        SendMessage sendToAll = new SendMessage();
        HandDisplay handDisplay = new HandDisplay();
        ArrayList<IPlayer> players = gameState.getPlayers();
        PointSaladCriteria pointSaladCriteria;
		sendToAll.sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"), players);
		for(IPlayer player : players) {

			sendToAll.sendToAllPlayers("Player " + player.getPlayerID() + "'s hand is: \n"+ handDisplay.displayHand(player.getHand(), SetPiles.getCardType()), players);
            pointSaladCriteria = new PointSaladCriteria();
			player.setScore(pointSaladCriteria.calculateScore(player, players, SetPiles.getCardType(), null)); 
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
                if (player instanceof LocalPlayer) {
                    LocalPlayer localPlayer = (LocalPlayer) player;
                    localPlayer.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
                    
                } else if (player instanceof OnlinePlayer) {
                    OnlinePlayer onlinePlayer = (OnlinePlayer) player;
                    onlinePlayer.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
                }

			} else {
                if (player instanceof LocalPlayer) {
                    LocalPlayer localPlayer = (LocalPlayer) player;
                    localPlayer.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
                    
                } else if (player instanceof OnlinePlayer) {
                    OnlinePlayer onlinePlayer = (OnlinePlayer) player;
                    onlinePlayer.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
                }
			}
		}
    }

    private ArrayList<Integer> selectPlayers(String gameMode) {
        ArrayList<Integer> playersAndBots = new ArrayList<>();
        int numberPlayers = 0;
        int numberOfBots = 0;

        //fixa så att max antal spelare och bots är beroende av game mode inte hårdkodat
        
        if (gameMode.equals("PointSalad")) {
            while (true) {
                System.out.println("Please enter the number of players (1-6): ");
                Scanner in = new Scanner(System.in);
                numberPlayers = in.nextInt();
                if (numberPlayers < 1 || numberPlayers > 6) {
                    System.out.println("Invalid number of players. Please try again.");
                } else {
                    break;
                }
            }
            while (true) {
                int maxNumberOfBots =  6 - numberPlayers;
                int minimumBots = 0;
                if (numberPlayers == 1) {
                    minimumBots = 1;
                } 
                if (maxNumberOfBots == -1) {
                    break;
                } else {
                    System.out.println("Please enter the number of bots ("+ minimumBots +"-" + maxNumberOfBots + "): ");
                    Scanner in = new Scanner(System.in);
                    numberOfBots = in.nextInt();
                    if (numberOfBots < 0 || numberOfBots > maxNumberOfBots) {
                        System.out.println("Invalid number of bots. Please try again.");
                    } else if (numberOfBots + numberPlayers < 2) {
                        System.out.println("Need atleast 2 players. Please select atleast 1 bot.");
                    } else {
                        break;
                    } 

                }
            }
            
            // PointSaladGame game = new PointSaladGame();
            // game.play();
        } else {
            System.out.println("Invalid game mode. Please try again.");
        }
       
        playersAndBots.add(numberPlayers);
        playersAndBots.add(numberOfBots);
        return playersAndBots;
        // int numberPlayers = gameState.getNumPlayers();
        // int numberOfBots = gameState.getNumberOfBots();
        // int playerID = 0;
        // gameState.addPlayer(new LocalPlayer(playerID));
        // playerID++;

        // for (int i = 1; i < numberPlayers; i++) {
        //     gameState.addPlayer(new OnlinePlayer(playerID));
        //     playerID++;
        // }
        // for (int i = 0; i < numberOfBots; i++) {
        //     gameState.addPlayer(new BotPlayer(playerID));
        //     playerID++;
        // }
    }

    public static void main(String[] args) {
        try {
            PointGame game = new PointGame (args);
            // game.play();
            // new PointGame (String[] args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
