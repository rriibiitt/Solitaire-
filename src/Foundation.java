import java.util.Stack;
import javafx.scene.layout.VBox;

public class Foundation {
    private final Stack<Card> cards = new Stack<>();
    private VBox visualRepresentation;

    public boolean canAccept(Card card) {
        if (card == null) return false;

        if (cards.isEmpty()) {
            return card.getRank() == Card.Rank.ACE;
        }

        Card topCard = cards.peek();
        return card.getSuit() == topCard.getSuit() &&
               card.getRank().ordinal() == topCard.getRank().ordinal() + 1;
    }

    public void addCard(Card card) {
        if (card != null) {
            cards.push(card);
            updateVisual();
        }
    }

    public Card removeTopCard() {
        if (cards.isEmpty()) return null;
        Card removed = cards.pop();
        updateVisual();
        return removed;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void setVisualRepresentation(VBox visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
        updateVisual();
    }

    private void updateVisual() {
        if (visualRepresentation != null) {
            visualRepresentation.getChildren().clear();
            for (Card card : cards) {
                visualRepresentation.getChildren().add(card.getVisualRepresentation());
            }
        }
    }
}
