package main.card;


/**
 * SaladCardFactory is a factory class that creates a SaladCard object.
 */
public class SaladCardFactory implements ICardFactory {

    // public SaladCardFactory() {
    // }
    
    /**
     * Creates a SaladCard object based on the vegetable type and criteria.
     * 
     * @param vegetableType The type of the vegetable
     * @param criteria The criteria of the card
     * @return Card The SaladCard object created
     */
    @Override
    public Card createCard(String vegetableType, String criteria) {
        return new SaladCard(vegetableType, criteria, true); 
    }
}
