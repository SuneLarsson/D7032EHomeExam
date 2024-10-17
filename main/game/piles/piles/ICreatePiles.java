package main.game.piles.piles;

import main.game.card.Card;
import main.game.setupgame.GameState;
import java.util.Map;
import java.util.ArrayList;

public interface ICreatePiles {
    void createPiles(GameState gameState, Map<String, ArrayList<Card>> decks);



}