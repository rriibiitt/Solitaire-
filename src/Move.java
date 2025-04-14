import java.util.Stack;

public class Move {
    private final Card card;
    private final Pile fromPile;
    private final Pile toPile;

    public Move(Card card, Pile fromPile, Pile toPile) {
        this.card = card;
        this.fromPile = fromPile;
        this.toPile = toPile;
    }

    public Card getCard() { return card; }
    public Pile getFromPile() { return fromPile; }
    public Pile getToPile() { return toPile; }
    private Stack<Move> moveHistory = new Stack<>();

}

