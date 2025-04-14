import java.util.Stack;

public class Foundation {
    private Stack<Card> cards = new Stack<>();

    public boolean canAccept(Card card) {
        if (cards.isEmpty()) {
            return card.getRank() == Deck.Rank.ACE;
        }
        Card topCard = cards.peek();
        return topCard.getSuit() == card.getSuit() &&
               card.getRank().ordinal() == topCard.getRank().ordinal() + 1;
    }

    public void addCard(Card card) {
        cards.push(card);
    }

    public Card removeTopCard() {
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}