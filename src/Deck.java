import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;

public class Deck {
    private final Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        cards.addAll(
            Arrays.stream(Card.Suit.values())
                .flatMap(suit -> Arrays.stream(Card.Rank.values())
                    .map(rank -> new Card(suit, rank)))
                .toList()
        );
    }
    

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.isEmpty() ? null : cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public void reset() {
        cards.clear();
        initializeDeck();
        shuffle();
    }
    public void addCard(Card card) {
        cards.push(card);
    }

    public void addCards(List<Card> cardsToAdd) {
        for (Card card : cardsToAdd) {
            addCard(card);
        }
    }

}
