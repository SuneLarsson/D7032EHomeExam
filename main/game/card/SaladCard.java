package main.game.card;

public class SaladCard extends Card {
    public enum Vegetable {
        PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
    }
    // private String vegetable;
    // private String criteria;
    // private boolean criteriaSideUp;

    public SaladCard(String vegetable, String criteria, boolean criteriaSideUp) {
        super(criteria, vegetable, criteriaSideUp);
        // this.vegetable = vegetable;
        // this.criteria = criteria;
        // this.criteriaSideUp = criteriaSideUp;
    }


    @Override
    public void flip() {
        super.flip();
    //     if (!isPointSideUp()) {
    //         super.flip();;
        // }
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
