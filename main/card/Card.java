package main.card;

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
     * @param pointSide The value of the point side of the card
     * @param resourceSide The value of the resource side of the card
     * @param pointSideUp Indicates whether the point side of the card is facing up
     */
    public Card(String pointSide, String resourceSide, boolean pointSideUp) {
        this.pointSide = pointSide;
        this.resourceSide = resourceSide;
        this.pointSideUp = pointSideUp;
    }

    /**
     * @return String get the the point side of the card
     */
    public String getPointSide() {
        return pointSide;
    }

    /**
    * @return String get the the resource side of the card
    */
 
    public String getResourceSide() {
        return resourceSide;
    }

    /**
    * @return true if the card is facing up where pointside is up, false otherwise
    */

    public boolean isPointSideUp() {
        return pointSideUp;
    }

    /**
    * Flips the card to the other side.
    */
    public void flip() {
        pointSideUp = !pointSideUp;
    }
}
