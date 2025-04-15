import java.util.*;  

public class Pile {
    private Stack<Card> cards;

    public Pile() {
        cards = new Stack<>();
    }

    public void addCard(Card card) {
        cards.push(card);
    }

    public Card topCard() {
        return cards.isEmpty() ? null : cards.peek();
    }

    public Card removeTopCard() {
        return cards.isEmpty() ? null : cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Stack<Card> getCards() {
        return cards;
    }
}