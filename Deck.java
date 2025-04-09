import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }

    private initializeDeck() {
        for(Card.Suit suit : Card.Suit.values()) {
            for(Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
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

    public int size() {
        return cards.size();
    }

    public String toString() {
        Stringbuilder sb = new StringBuilder();
        for(Card card : cards) {
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }
}

/*
 * Alternate code
 * import java.util.*;

public class Deck {
    private Stack<Card> cards = new Stack<>();

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.push(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card draw() {
        return cards.isEmpty() ? null : cards.pop();
    }
}

 */