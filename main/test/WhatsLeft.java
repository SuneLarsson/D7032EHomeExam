import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WhatsLeft {
	public ArrayList<Player> players = new ArrayList<>();
	public ArrayList<Pile> piles = new ArrayList<>();
    public ServerSocket aSocket;





	
	

	public String displayHand(ArrayList<Card> hand) {
		String handString = "Criteria:\t";
		for (int i = 0; i < hand.size(); i++) {
			if(hand.get(i).criteriaSideUp && hand.get(i).vegetable != null) {
				handString += "["+i+"] "+hand.get(i).criteria + " ("+hand.get(i).vegetable.toString()+")"+"\t";
			}
		}
		handString += "\nVegetables:\t";
		//Sum up the number of each vegetable and show the total number of each vegetable
		for (Card.Vegetable vegetable : Card.Vegetable.values()) {
			int count = countVegetables(hand, vegetable);
			if(count > 0) {
				handString += vegetable + ": " + count + "\t";
			}
		}
		return handString;
	}

	private void sendToAllPlayers(String message) {
		for(Player player : players) {
			player.sendMessage(message);
		}
	}

	public void client(String ipAddress) throws Exception {
        //Connect to server
        Socket aSocket = new Socket(ipAddress, 2048);
        ObjectOutputStream outToServer = new ObjectOutputStream(aSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(aSocket.getInputStream());
        String nextMessage = "";
        while(!nextMessage.contains("winner")){
            nextMessage = (String) inFromServer.readObject();
            System.out.println(nextMessage);
            if(nextMessage.contains("Take") || nextMessage.contains("into")) {
                Scanner in = new Scanner(System.in);
                outToServer.writeObject(in.nextLine());
            } 
        }
    }

    public void server(int numberPlayers, int numberOfBots) throws Exception {
        players.add(new Player(0, false, null, null, null)); //add this instance as a player
        //Open for connections if there are online players
        for(int i=0; i<numberOfBots; i++) {
            players.add(new Player(i+1, true, null, null, null)); //add a bot    
        }
        if(numberPlayers>1)
            aSocket = new ServerSocket(2048);
        for(int i=numberOfBots+1; i<numberPlayers+numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(new Player(i, false, connectionSocket, inFromClient, outToClient)); //add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }    
    }

	private String printMarket() {
		String pileString = "Point Cards:\t";
		for (int p=0; p<piles.size(); p++) {
			if(piles.get(p).getPointCard()==null) {
				pileString += "["+p+"]"+String.format("%-43s", "Empty") + "\t";
			}
			else
				pileString += "["+p+"]"+String.format("%-43s", piles.get(p).getPointCard()) + "\t";
		}
		pileString += "\nVeggie Cards:\t";
		char veggieCardIndex = 'A';
		for (Pile pile : piles) {
			pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(0)) + "\t";
			veggieCardIndex++;
		}
		pileString += "\n\t\t";
		for (Pile pile : piles) {
			pileString += "["+veggieCardIndex+"]"+String.format("%-43s", pile.getVeggieCard(1)) + "\t";
			veggieCardIndex++;
		}
		return pileString;
	}


	public WhatsLeft(String[] args) {
		int numberPlayers = 0;
		int numberOfBots = 0;

		if(args.length == 0) {
			System.out.println("Please enter the number of players (1-6): ");
			Scanner in = new Scanner(System.in);
			numberPlayers = in.nextInt();
			System.out.println("Please enter the number of bots (0-5): ");
			numberOfBots = in.nextInt();
		}
		else {
			//check if args[0] is a String (ip address) or an integer (number of players)
			if(args[0].matches("\\d+")) {
				numberPlayers = Integer.parseInt(args[0]);
				numberOfBots = Integer.parseInt(args[1]);
			}
			else {
				try {
					client(args[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		setPiles(numberPlayers+numberOfBots);

		try {
			server(numberPlayers, numberOfBots);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set random starting player
		int currentPlayer = (int) (Math.random() * (players.size()));
		boolean keepPlaying = true;

		while(keepPlaying) {
			Player thisPlayer = players.get(currentPlayer);
			boolean stillAvailableCards = false;
			for(Pile p: piles) {
				if(!p.isEmpty()) {
					stillAvailableCards = true;
					break;
				}
			}
			if(!stillAvailableCards) {
				keepPlaying = false;
				break;
			}
			if(!thisPlayer.isBot) {
				thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
				thisPlayer.sendMessage(displayHand(thisPlayer.hand));
				thisPlayer.sendMessage("\nThe piles are: ");
			
				thisPlayer.sendMessage(printMarket());
				boolean validChoice = false;
				while(!validChoice) {
					thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
					String pileChoice = thisPlayer.readMessage();
					if(pileChoice.matches("\\d")) {
						int pileIndex = Integer.parseInt(pileChoice);
						if(piles.get(pileIndex).getPointCard() == null) {
							thisPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
							continue;
						} else {
							thisPlayer.hand.add(piles.get(pileIndex).buyPointCard());
							thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
							validChoice = true;
						}
					} else {
						int takenVeggies = 0;
						for(int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
							if(Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
								thisPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
								validChoice = false;
								break;
							}
							int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
							int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2:-1;
							int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
							if(piles.get(pileIndex).veggieCards[veggieIndex] == null) {
								thisPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
								validChoice = false;
								break;
							} else {
								if(takenVeggies == 2) {
									validChoice = true;
									break;
								} else {
									thisPlayer.hand.add(piles.get(pileIndex).buyVeggieCard(veggieIndex));
									takenVeggies++;
									//thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
									validChoice = true;
								}
							}
						}

					}
				}
				//Check if the player has any criteria cards in their hand
				boolean criteriaCardInHand = false;
				for(Card card : thisPlayer.hand) {
					if(card.criteriaSideUp) {
						criteriaCardInHand = true;
						break;
					}
				}
				if(criteriaCardInHand) {
					//Give the player an option to turn a criteria card into a veggie card
					thisPlayer.sendMessage("\n"+displayHand(thisPlayer.hand)+"\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
					String choice = thisPlayer.readMessage();
					if(choice.matches("\\d")) {
						int cardIndex = Integer.parseInt(choice);
						thisPlayer.hand.get(cardIndex).criteriaSideUp = false;
					}
				}
				thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
				sendToAllPlayers("Player " + thisPlayer.playerID + "'s hand is now: \n"+displayHand(thisPlayer.hand)+"\n");	

			} else {
				// Bot logic
				// The Bot will randomly decide to take either one point card or two veggie cards 
				// For point card the Bot will always take the point card with the highest score
				// If there are two point cards with the same score, the bot will take the first one
				// For Veggie cards the Bot will pick the first one or two available veggies
				boolean emptyPiles = false;
				// Random choice: 
				int choice = (int) (Math.random() * 2);
				if(choice == 0) {
					// Take a point card
					int highestPointCardIndex = 0;
					int highestPointCardScore = 0;
					for(int i = 0; i < piles.size(); i++) {
						if(piles.get(i).getPointCard() != null) {
							ArrayList<Card> tempHand = new ArrayList<Card>();
							for(Card handCard : thisPlayer.hand) {
								tempHand.add(handCard);
							}
							tempHand.add(piles.get(i).getPointCard());
							int score = calculateScore(tempHand, thisPlayer);
							if(score > highestPointCardScore) {
								highestPointCardScore = score;
								highestPointCardIndex = i;
							}
						}
					}
					if(piles.get(highestPointCardIndex).getPointCard() != null) {
						thisPlayer.hand.add(piles.get(highestPointCardIndex).buyPointCard());
					} else {
						choice = 1; //buy veggies instead
						emptyPiles = true;
					}
				} else if (choice == 1) {
					// TODO: Check what Veggies are available and run calculateScore to see which veggies are best to pick
					int cardsPicked = 0;
					for(Pile pile : piles) {
						if(pile.veggieCards[0] != null && cardsPicked < 2) {
							thisPlayer.hand.add(pile.buyVeggieCard(0));
							cardsPicked++;
						}
						if(pile.veggieCards[1] != null && cardsPicked < 2) {
							thisPlayer.hand.add(pile.buyVeggieCard(1));
							cardsPicked++;
						}
					}
					if(cardsPicked == 0 && !emptyPiles) {
						// Take a point card instead of veggies if there are no veggies left
						int highestPointCardIndex = 0;
						int highestPointCardScore = 0;
						for(int i = 0; i < piles.size(); i++) {
							if(piles.get(i).getPointCard() != null && piles.get(i).getPointCard().criteriaSideUp) {
								ArrayList<Card> tempHand = new ArrayList<Card>();
								for(Card handCard : thisPlayer.hand) {
									tempHand.add(handCard);
								}
								tempHand.add(piles.get(i).getPointCard());
								int score = calculateScore(tempHand, thisPlayer);
								if(score > highestPointCardScore) {
									highestPointCardScore = score;
									highestPointCardIndex = i;
								}
							}
						}
						if(piles.get(highestPointCardIndex).getPointCard() != null) {
							thisPlayer.hand.add(piles.get(highestPointCardIndex).buyPointCard());
						}
					}
				}
				sendToAllPlayers("Bot " + thisPlayer.playerID + "'s hand is now: \n"+displayHand(thisPlayer.hand)+"\n");
			}
			
			if(currentPlayer == players.size()-1) {
				currentPlayer = 0;
			} else {
				currentPlayer++;
			}
		}
		sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"));
		for(Player player : players) {
			sendToAllPlayers("Player " + player.playerID + "'s hand is: \n"+displayHand(player.hand));
			player.score = calculateScore(player.hand, player);
			sendToAllPlayers("\nPlayer " + player.playerID + "'s score is: " + player.score);
		}

		int maxScore = 0;
		int playerID = 0;
		for(Player player : players) {
			if(player.score > maxScore) {
				maxScore = player.score;
				playerID = player.playerID;
			}
		}
		for(Player player : players) {
			if(player.playerID == playerID) {
				player.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
			} else {
				player.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
			}
		}
	}

	public static void main(String[] args) {
		PointSalad game = new PointSalad(args);

	}
}