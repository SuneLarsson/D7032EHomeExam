package main.game.card;

public class SaladCardFactory implements ICardFactory {

    public SaladCardFactory() {
    }
    
    @Override
    public Card createCard(String vegetableType, String criteria) {
        return new SaladCard(vegetableType, criteria, true);  // Assuming 'true' is always passed
    }
}
