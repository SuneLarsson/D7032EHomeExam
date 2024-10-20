package main.game.card;

/**
 * Card is an abstract class that represents a card in the game.
 */
public abstract class Card {
    private boolean pointSideUp;
    private String pointSide;
    private String resourceSide;

    /**
     * Constructor to initialize a Card object with specified point side, resource side, and its orientation.
     * 
     * @param pointSide The value or description of the point side of the card
     * @param resourceSide The value or description of the resource side of the card
     * @param pointSideUp Indicates whether the point side of the card is facing up
     */
    public Card(String pointSide, String resourceSide, boolean pointSideUp) {
        this.pointSide = pointSide;
        this.resourceSide = resourceSide;
        this.pointSideUp = pointSideUp;
    }

    /**
     * Gets the value or description of the point side of the card.
     * 
     * @return The value or description of the point side
     */
    public String getPointSide() {
        return pointSide;
    }

    /**
     * Gets the value or description of the resource side of the card.
     * 
     * @return The value or description of the resource side
     */
    public String getResourceSide() {
        return resourceSide;
    }

    /**
     * Checks if the point side of the card is facing up.
     * 
     * @return true if the point side is facing up, false otherwise
     */
    public boolean isPointSideUp() {
        return pointSideUp;
    }

    /**
     * Flips the card, changing its orientation.
     * If the point side was up, it will be down, and vice versa.
     */
    public void flip() {
        pointSideUp = !pointSideUp;
    }
}
