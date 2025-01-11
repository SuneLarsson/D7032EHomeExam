package main.players.actions;

import java.util.ArrayList;

import main.card.Card;
import main.game.gameState.GameState;
import main.game.score.CriteriaFactory;
import main.game.score.ICriteria;
import main.game.score.PointSaladCriteria;
import main.piles.PileManager;
import main.piles.pile.Pile;
import main.players.BotPlayer;
import main.players.IPlayer;

/**
 * Class for the actions of a bot player for PointSalad game
 */

public class SaladBotActions implements IPlayerActions {

    private BotPlayer botPlayer;
    private GameState gameState;
    private PileManager pileManager;
    private ICriteria pointSaladCriteria;
    private ArrayList<String> cardTypes;

    /**
     * Constructor for SaladBotActions
     * @param thisPlayer the bot player
     * @param gameState the current game state
     */
    public SaladBotActions(IPlayer thisPlayer, GameState gameState) {
        this.gameState = gameState;
        this.botPlayer = (BotPlayer) thisPlayer;
        this.pileManager = gameState.getPileManager();
        this.pointSaladCriteria = CriteriaFactory.createCriteria(gameState);
        this.cardTypes = gameState.getSettings().getCardTypes();
    }

    

    @Override
    public boolean drawCardFromPile(int pileIndex){
        // Take a point card
        int highestPointCardIndex = 0;
        int highestPointCardScore = 0;
        for(int i = 0; i < pileManager.getPiles().size(); i++) {
            if(pileManager.getPile(i).getPileCard() != null) {
                ArrayList<Card> tempHand = new ArrayList<Card>();
                for(Card handCard : botPlayer.getHand()) {
                    tempHand.add(handCard);
                }
                tempHand.add(pileManager.getPile(i).getPileCard());
                pointSaladCriteria = new PointSaladCriteria();
                int score = pointSaladCriteria.calculateScore(botPlayer, gameState.getPlayers(), cardTypes, tempHand);
                if(score > highestPointCardScore) {
                    highestPointCardScore = score;
                    highestPointCardIndex = i;
                }
            }
        }
        if(pileManager.getPile(highestPointCardIndex).getPileCard() != null) {
            botPlayer.addCard(pileManager.getPile(highestPointCardIndex).buyPileCard());
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean takeFromMarket(String pileChoice) {
        int cardsPicked = 0;
        for(Pile pile : pileManager.getPiles()) {
            if(pile.getMarketCard(0) != null && cardsPicked < 2) {
                botPlayer.addCard(pile.buyMarketCard(0));
                cardsPicked++;
            }
            if(pile.getMarketCard(1) != null && cardsPicked < 2) {
                botPlayer.addCard(pile.buyMarketCard(1));
                cardsPicked++;
            }
        }
        return true;
    }

    
}
