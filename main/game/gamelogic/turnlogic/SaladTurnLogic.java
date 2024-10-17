package main.game.gamelogic.turnlogic;

import main.game.display.HandDisplay;
import main.game.display.IMarket;
import main.game.display.SaladMarket;
import main.game.piles.PileManager;
import main.game.players.BotPlayer;
import main.game.players.IHumanPlayer;
import main.game.players.IPlayer;
import main.game.players.actions.SaladBotActions;
import main.game.players.actions.SaladHumanActions;
import main.game.setupgame.GameState;

public class SaladTurnLogic implements ITurnLogic {

    private IPlayer thisPlayer;
    private GameState gameState;
    private PileManager pileManager;
    private HandDisplay handDisplay;
    private IMarket saladMarket;
    // private IPlayerActions playerActions;

    public SaladTurnLogic() {
    }




    @Override
    public void takeTurn(GameState gameState, IPlayer thisPlayer) {
        this.gameState = gameState;
        this.thisPlayer = thisPlayer;
        this.pileManager = gameState.getPileManager();
        this.handDisplay = new HandDisplay();
        this.saladMarket = new SaladMarket();
        if (thisPlayer instanceof IHumanPlayer) {
            SaladHumanActions playerActions = new SaladHumanActions(thisPlayer, gameState);
            takeHumanTurn(playerActions);
        } else {
            BotPlayer botPlayer = (BotPlayer) thisPlayer;
            SaladBotActions botActions = new SaladBotActions(botPlayer, gameState);
            takeBotTurn(botActions);
        }
      }

    private void takeHumanTurn(SaladHumanActions playerActions) {  
        boolean validChoice = false;
        IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
        startTurnPrint();
        while(!validChoice) {
            String vegetableString = "two vegetables";
            String syntaxString = "AC";
            if (gameState.availableMarketCards() == 1){
                vegetableString = "a vegetable";
                syntaxString = "A";
            }
            humanPlayer.sendMessage("\nWould you like to draw a card from a pile or take " + vegetableString + " from the market? (Syntax example: 0 or " + syntaxString + ")\n");
            String choice = humanPlayer.readMessage();
            if(choice.matches("\\d")) {
                int pileIndex = Integer.parseInt(choice);
                validChoice = playerActions.drawCardFromPile(pileIndex);
            } else {
                validChoice = playerActions.takeFromMarket(choice);
            }
        }
        if (hasCriteriaCardInHand()) {
            playerActions.flipCriteriaCard(gameState);
        }
        endTurnPrint();
    }

    private void takeBotTurn(SaladBotActions botActions) {
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
        // if (hasCriteriaCardInHand()) {
        //     playerActions.flipCriteriaCard();
        // }
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

    @Override
    public void startTurnPrint() {
        IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
        humanPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
        humanPlayer.sendMessage(handDisplay.displayHand(humanPlayer.getHand(), gameState));
        humanPlayer.sendMessage("\nThe piles are: ");
        humanPlayer.sendMessage(saladMarket.printMarket(pileManager));
    }

    @Override
    public void endTurnPrint() {
        IHumanPlayer humanPlayer = (IHumanPlayer) thisPlayer;
        humanPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
    }
    
}
