package main.card;

/**
 * SaladCard is a class that represents a SaladCard object in the game.
 */
public class SaladCard extends Card {

    /**
     * Constructor to initialize a SaladCard object with specified vegetable and criteria.
     * 
     * @param vegetable The value of the vegetable side of the card
     * @param criteria The value of the criteria side of the card
     * @param criteriaSideUp Indicates whether the criteria side of the card is facing up
     */
    public SaladCard(String vegetable, String criteria, boolean criteriaSideUp) {
        super(criteria, vegetable, criteriaSideUp);
    }

    /**
     * Flips the card to the other side.
     */
    @Override
    public void flip() {
        super.flip();
    }

    /**
     * @return String representation of the SaladCard object
     */
    @Override
    public String toString() {
        if(isPointSideUp()) {
            return getPointSide() + " (" + getResourceSide() + ")";
        } else {
            return getResourceSide().toString();
        }
    }
    
}
