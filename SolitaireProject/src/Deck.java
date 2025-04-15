import java.util.*;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        for(Card.Suit suit : Card.Suit.values()) {
            for(int val = 1; val <= 13; val++) {
                cards.add(new Card(suit, val));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if(!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card draw() {
        return isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}