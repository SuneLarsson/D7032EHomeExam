package main.game.card;

public class SaladCard extends Card {

    public SaladCard(String vegetable, String criteria, boolean criteriaSideUp) {
        super(criteria, vegetable, criteriaSideUp);
    }

    @Override
    public void flip() {
        super.flip();
    }

    @Override
    public String toString() {
        if(isPointSideUp()) {
            return getPointSide() + " (" + getResourceSide() + ")";
        } else {
            return getResourceSide().toString();
        }
    }
    
}
