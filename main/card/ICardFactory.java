package main.card;


/**
 * ICardFactory is an interface that represents a card factory in the game.
 */
public interface ICardFactory {
    /**
     * Creates a card object based on the card type and criteria.
     * 
     * @param cardType The type of the card
     * @param criteria The criteria of the card
     * @return Card The card object created
     */
    Card createCard(String cardType, String criteria);
}
