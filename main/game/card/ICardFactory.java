package main.game.card;

public interface ICardFactory {
    Card createCard(String cardType, String criteria);
}
