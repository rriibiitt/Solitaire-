import java.util.List;

public class TableauPile extends Pile {
    public TableauPile(double x, double y) {
        super(x, y);
    }
    public TableauPile() {
        super(0, 0); // Provide default values for x and y
    }

    public boolean canPlaceCard(Card card) {
        if (isEmpty()) {
            return card.getRank() == Deck.Rank.KING;
        }

        Card top = peekTopCard();
        boolean oppositeColor = (top.getSuit() == Deck.Suit.HEARTS || top.getSuit() == Deck.Suit.DIAMONDS)
                              != (card.getSuit() == Deck.Suit.HEARTS || card.getSuit() == Deck.Suit.DIAMONDS);

        return oppositeColor && card.getRank().ordinal() == top.getRank().ordinal() - 1;
    }
    public boolean canMoveToFoundation(Card card) {
        if (isEmpty()) {
            return card.getRank() == Deck.Rank.ACE;
        }
        Card top = peekTopCard();
        return top.getSuit() == card.getSuit()
            && card.getRank().ordinal() == top.getRank().ordinal() + 1;
    }
    public TableauPile getTableauPile() {
        return this;
    }
    public void addCard(Card card) {
        if (canPlaceCard(card)) {
            super.addCard(card);
        } else {
            throw new IllegalArgumentException("Cannot place this card on the tableau pile.");
        }
    }
    public void clear() {
        super.clear(); // Assuming the parent class 'Pile' has a 'clear()' method to clear cards
    }
}
