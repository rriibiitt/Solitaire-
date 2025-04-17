import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Move {
    private final Card card;
    private final Pile fromPile;
    private final Pile toPile;
    private List<Stack<Card>> foundations = new ArrayList<>(); // Initialize foundations

    public Move(Card card, Pile fromPile, Pile toPile) {
        this.card = card;
        this.fromPile = fromPile;
        this.toPile = toPile;
    }


    public Card getCard() { return card; }
    public Pile getFromPile() { return fromPile; }
    public Pile getToPile() { return toPile; }
    private Stack<Move> moveHistory = new Stack<>();

    // Method to check if the game is won
    private boolean isGameWon() {
        // Add logic to determine if the game is won
        for (Stack<Card> foundation : foundations) {
            if (foundation.size() < 13) { 
                return false;
            }
        }
        return true;
    }

    private void moveCards(Stack<Card> stack) {
        List<Card> selected = new ArrayList<>(); 
        for (Card card : selected) {
            stack.push(card);
        }
        if (isGameWon()) {
            System.out.println("You win!");
        }
    }
}

