public interface Movable {
    void enableCardDragging(Card card, Pile currentPile);
    void snapCardToPile(Card card, Pile pile);
    void checkForReveal(Pile pile);
}
