package main.game.card;

public abstract class Card{
    private boolean pointSideUp;
    private String pointSide;
    private String resourceSide;

    public Card(String pointSide, String resourceSide, boolean pointSideUp) {
        this.pointSide = pointSide;
        this.resourceSide = resourceSide;
        this.pointSideUp = pointSideUp;
    }

    public String getPointSide() {
        return pointSide;
    }

    public String getResourceSide() {
        return resourceSide;
    }

    public boolean isPointSideUp() {
        return pointSideUp;
    }

    public void flip() {
        pointSideUp = !pointSideUp;
    }

}
