import java.util.ArrayList;
import java.util.List;

public class Pile {
    protected List<Card> cards;

    public Pile(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }
    public Pile(double x, double y) {
        this.cards = new ArrayList<>();
    }

    public Card topCard() {
        return cards.isEmpty() ? null : cards.get(cards.size() - 1);
    }
    public Card peekTopCard() {
        return cards.isEmpty() ? null : cards.get(cards.size() - 1);
    }
    public boolean canAccept(Card card) {
        if (isEmpty()) {
            return true; // Accept any card if pile is empty
        }
        Card top = topCard();
        return top.canStack(card); // Check if the card can be placed on top of the current top card
    }
    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card removeTopCard() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }
    public boolean addCards(List<Card> newCards) {
        return cards.addAll(newCards);
    }
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    public void clear() {
        cards.clear();
    }
    
    
}
