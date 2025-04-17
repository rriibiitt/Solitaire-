public class Move {
    private final Card card;
    private final Pile fromPile;
    private final Pile toPile;
    private Card flippedCard; // Optional card that was flipped during the move

    // Original constructor (no flipped card)
    public Move(Card card, Pile fromPile, Pile toPile) {
        this(card, fromPile, toPile, null);
    }

    // Overloaded constructor (with flipped card)
    public Move(Card card, Pile fromPile, Pile toPile, Card flippedCard) {
        this.card = card;
        this.fromPile = fromPile;
        this.toPile = toPile;
        this.flippedCard = flippedCard;
    }

    public Card getCard() {
        return card;
    }

    public Pile getFromPile() {
        return fromPile;
    }

    public Pile getToPile() {
        return toPile;
    }

    public Card getFlippedCard() {
        return flippedCard;
    }

    public void setFlippedCard(Card flippedCard) {
        this.flippedCard = flippedCard;
    }
}
