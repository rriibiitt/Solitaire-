import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.Card;

public class Pile {
    private List<Card> cards;
    private boolean allowsFaceDown;

    public Pile(boolean allowsFaceDown) {
        this.cards = new ArrayList<>();
        this.allowsFaceDown = allowsFaceDown;
    }

    public void addCard(Card card) {
        if(!allowsFaceDown) {
            card.flip();
        }
        cards.add(card);
    }

    public void addCards(List<Card> newCards) {
        for(Card card : newCards) {
            if(!allowsFaceDown) {
                card.flip();
            }
            cards.add(cards);
        }
    }

    public Card removeTopCArd() {
        if(!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    public List<Card> removeCardsFromIndex(int index) {
        if(index >= 0 && index < cards.size()) {
            List<Card> removedCards = new ArrayList<>(cards.subList(index, cards.size()));
            cards.subList(index, cards.size()).clear();
            return removedCards; 
        }
        return new ArrayList<>();
    }

    public Card peekTopCard() {
        return isEmpty() ? null : cards.get(cards.size()-1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public void flipTopCard() {
        if (!cards.isEmpty()) {
            cards.get(cards.size() - 1).flip();
        }
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards); // Returns a copy to prevent modification
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Pile: ");
        for (Card card : cards) {
            sb.append(card.toString()).append(", ");
        }
        return sb.toString();
    }
}

/*
 * Alternate code
 * import java.util.*;

public class Pile {
    private Stack<Card> cards = new Stack<>();

    public void add(Card card) {
        cards.push(card);
    }

    public Card peek() {
        return cards.isEmpty() ? null : cards.peek();
    }

    public Card remove() {
        return cards.isEmpty() ? null : cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void printPile() {
        for (Card card : cards) {
            System.out.print(card + " ");
        }
        System.out.println();
    }
}

// for moving cards
public int size() {
    return cards.size();
}

public Stack<Card> getStack() {
    return cards;
}


 */