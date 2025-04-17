import java.util.*;  

public class SolitaireLogic {
    public Deck deck;
    public Pile[] tableau;
    public Pile stock;
    public Pile waste;
    public Pile[] foundations;

    public SolitaireLogic() {
        deck = new Deck();
        tableau = new Pile[7];
        for (int i = 0; i< 7; i++) {
            tableau[i] = new Pile();
        }
        stock = new Pile();
        waste = new Pile();
        foundations = new Pile[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Pile();
        }
        dealCards();
    }

    private void dealCards() {
        for (int i = 0; i < 7; i++) {
            for (int j =0; j <= i; j++) {
                Card card = deck.draw();
                if (j == i) {
                    card.flip();
                }
                tableau[i].addCard(card);
            }
        }
        while (!deck.isEmpty()) {
            stock.addCard(deck.draw());
        }
    }

    public void drawFromStock() {
        if(!stock.isEmpty()) {
            Card card = stock.removeTopCard();
            card.flip();
            waste.addCard(card);
        } else {
            // reset stock from waste
            while (!waste.isEmpty()) {
                Card card = waste.removeTopCard();
                card.flip();
                stock.addCard(card);
            }
        }
    }

    public boolean moveToFoundation(Card card) {
        for (Pile foundation : foundations) {
            Card top = foundation.topCard();
            if (top == null) {
                if (card.getValue() == 1) { // if card is ace
                    foundation.addCard(card);
                    return true;
                }
            } else if (top.getSuit() == card.getSuit() && top.getValue() + 1 == card.getValue()) {
                foundation.addCard(card);
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() { // checks if all foundations are complete
        for (Pile foundation : foundations) {
            Card top = foundation.topCard();
            if (top == null || top.getValue() != 13) {
                return false;
            }
        }
        return true;
    }
}