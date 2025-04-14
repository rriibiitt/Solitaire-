import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

enum Suit {
    HEARTS, DIAMONDS, CLUBS, SPADES
}

enum Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

public class Deck {
    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }
    
    enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }
    private final List<Card> cards;
    private final int NUM_CARDS = 52;
    private final int NUM_PILES = 7;
    private final int values[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null; // or throw an exception
        }
        return cards.remove(cards.size() - 1);
    }
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public List<Pile> dealCards() {
        List<Pile> piles = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < 7; i++) {
            List<Card> pileCards = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                Card card = cards.get(index++);
                pileCards.add(card);
            }
            pileCards.get(pileCards.size() - 1).flip(); // last card face up
            piles.add(new Pile(pileCards));
        }
        return piles;
    }
    public void resetDeck() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> getCards() {
        return cards;
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