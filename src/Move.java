public class Move {
    private final Pile fromPile;
    private final Pile toPile;
    private final Card card;

    public Move(Pile from, Pile to, Card card) {
        this.fromPile = from;
        this.toPile = to;
        this.card = card;
    }

    public Pile getFromPile() { return fromPile; }
    public Pile getToPile() { return toPile; }
    public Card getCard() { return card; }
}
