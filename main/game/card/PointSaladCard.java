package main.game.card;

public class PointSaladCard implements ICard {
    public enum Vegetable {
        PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
    }

    private Vegetable vegetable;
    private String criteria;
    private boolean criteriaSideUp = true;

    public PointSaladCard(Vegetable vegetable, String criteria) {
        this.vegetable = vegetable;
        this.criteria = criteria;
    }

    public String getCritera() {
        return criteria;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }
    
    public boolean isCriteriaSideUp() {
        return criteriaSideUp;
    }

    @Override
    public void flip() {
        criteriaSideUp = !criteriaSideUp;
    }

    @Override
    public String toString() {
        if(criteriaSideUp) {
            return criteria + " (" + vegetable + ")";
        } else {
            return vegetable.toString();
        }
    }
}