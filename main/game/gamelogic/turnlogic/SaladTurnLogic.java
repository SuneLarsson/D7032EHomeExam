package main.game.gamelogic.turnlogic;

import main.display.GameFieldFactory;
import main.display.HandDisplayFactory;
import main.display.IGameField;
import main.display.IHandDisplay;
import main.display.SendMessageToAll;
import main.game.gameState.GameState;
import main.piles.PileManager;
import main.players.BotPlayer;
import main.players.IHumanPlayer;
import main.players.IPlayer;
import main.players.actions.SaladBotActions;
import main.players.actions.SaladHumanActions;

/**
 * Class that handles the turn logic for the PointSalad game.
 */

public class SaladTurnLogic implements ITurnLogic {

    private IPlayer thisPlayer;
    private GameState gameState;
    private PileManager pileManager;
    private IHandDisplay handDisplay;
    private IGameField saladGameField;
    private SendMessageToAll sendMessage;

    // public SaladTurnLogic() {
    // }


    /**
     * Setups the takeTurn selection for the player. If the player is a human player, they will be prompted to make a choice. If the player is a bot, a random choice will be made. 
     * @param gameState The current game state.
     * @param thisPlayer The player whose turn it is.
     */
    @Override
    public void takeTurn(GameState gameState, IPlayer thisPlayer) {
        try {
            this.gameState = gameState;
            this.thisPlayer = thisPlayer;
            this.pileManager = gameState.getPileManager();
            this.handDisplay = HandDisplayFactory.createCardFactory(gameState);
            this.saladGameField = GameFieldFactory.getGameField(gameState);
            this.sendMessage =gameState.getSendMessageToAll();
            if (thisPlayer instanceof IHumanPlayer) {
                SaladHumanActions playerActions = new SaladHumanActions(thisPlayer, gameState);
                takeHumanTurn(playerActions);
            } else {
                BotPlayer botPlayer = (BotPlayer) thisPlayer;
                SaladBotActions botActions = new SaladBotActions(botPlayer, gameState);
                takeBotTurn(botActions);
            }
        } catch (Exception e) {
            System.out.println("Error in takeTurn: " + e);
            throw new RuntimeException(e);
        }

      }

    private void takeHumanTurn(SaladHumanActions playerActions) {  
        try {
            boolean validChoice = false;
            IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
            startTurnPrint();
            while(!validChoice) {
                String vegetableString = "two vegetables";
                String syntaxString = "AC";
                if (availableMarketCards() == 1){
                    vegetableString = "a vegetable";
                    syntaxString = "A";
                }
                humanPlayer.sendMessage("\nWould you like to draw a card from a pile or take " + vegetableString + " from the market? (Syntax example: 0 or " + syntaxString + ")\n");
                String choice = humanPlayer.readMessage(gameState);
                if(choice.matches("\\d")) {
                    int pileIndex = Integer.parseInt(choice);
                    validChoice = playerActions.drawCardFromPile(pileIndex);
                } else {
                    validChoice = playerActions.takeFromMarket(choice);
                }
            }
            if (hasCriteriaCardInHand()) {
                humanPlayer.sendMessage("\n"+handDisplay.displayHand(humanPlayer.getHand(), gameState)+"\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
                String choice = humanPlayer.readMessage(gameState);
                playerActions.flipCriteriaCard(choice);
            }
            endTurnPrint();
        } catch (Exception e) {
            System.out.println("Error in takeHumanTurn: " + e);
            throw new RuntimeException(e);
        }

    }

    private void takeBotTurn(SaladBotActions botActions) {
        try {
            boolean validChoice = false;
            while(!validChoice) {
                // Random choice: 
                int choice = (int) (Math.random() * 2);
                if(choice == 0) {
                    validChoice = botActions.drawCardFromPile(0);
                } 
                if (choice == 1 || !validChoice) {
                    validChoice = botActions.takeFromMarket("");
                }
            }
    
            endTurnPrint();
            // if (hasCriteriaCardInHand()) {
            //     playerActions.flipCriteriaCard();
            // }
        } catch (Exception e) {
            System.out.println("Error in takeBotTurn: " + e);
            throw new RuntimeException(e);
        }
    }

    // Rule 8: If the player has a criteria card in their hand, they can flip it to a veggie card
    private boolean hasCriteriaCardInHand() {
        for (int i = 0; i < thisPlayer.getHand().size(); i++) {
            if (thisPlayer.getHand().get(i).isPointSideUp()) {
                return true;
            }
        }
        return false;
    }

    private int availableMarketCards(){
        int marketCards = 0;
        for (int i = 0; i < pileManager.getPiles().size(); i++) {
            for (int j = 0; j < 2; j++) {
                if (pileManager.getPile(i).getMarketCard(j) != null) {
                    marketCards++;
                }
            }
        }
        return marketCards;
    }

    @Override
    public void startTurnPrint() {
        try {     
            IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
            humanPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
            humanPlayer.sendMessage(handDisplay.displayHand(humanPlayer.getHand(), gameState));
            humanPlayer.sendMessage("\nThe piles are: ");
            humanPlayer.sendMessage(saladGameField.printGameField(pileManager));
        } catch (Exception e) {
            System.out.println("Error in startTurnPrint: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endTurnPrint() {
        try {            
            if (thisPlayer instanceof BotPlayer) {
                sendPlayerHandToAllPlayers(thisPlayer, gameState);            
            }else{
                IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
                humanPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
                sendPlayerHandToAllPlayers(thisPlayer, gameState);
            }   
        } catch (Exception e) {
            System.out.println("Error in endTurnPrint: " + e);
            throw new RuntimeException(e);
        }
    }
    
	private void sendPlayerHandToAllPlayers(IPlayer player, GameState gameState) {
		String handMessage = "Player " + player.getPlayerID() + "'s hand is now: \n" + handDisplay.displayHand(player.getHand(), gameState) + "\n";
		sendMessage.sendToAllPlayers(handMessage, gameState.getPlayers());
	}

}
