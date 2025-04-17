import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pile {
    private final List<Card> cards;
    private final PileType type;
    private final Rectangle rectangle;

    private static final double CARD_WIDTH = 100.0;
    private static final double CARD_HEIGHT = 150.0;

    public enum PileType {
        TABLEAU,
        FOUNDATION,
        STOCK,
        WASTE
    }

    public Pile(PileType type, double x, double y) {
        this.type = type;
        this.cards = new ArrayList<>();
        this.rectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        this.rectangle.setX(x);
        this.rectangle.setY(y);
        this.rectangle.setFill(null);
        this.rectangle.setStroke(Color.GRAY);
        this.rectangle.setStrokeWidth(3);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean canAccept(Card card) {
        Card top = getTopCard();

        switch (type) {
            case FOUNDATION:
                if (top == null) {
                    return card.getRank() == Card.Rank.ACE;
                }
                return card.getSuit() == top.getSuit() &&
                       card.getRank().ordinal() == top.getRank().ordinal() + 1;

            case TABLEAU:
                if (top == null) {
                    return card.getRank() == Card.Rank.KING;
                }
                return card.getColor() != top.getColor() &&
                       card.getRank().ordinal() == top.getRank().ordinal() - 1;

            case STOCK:
            case WASTE:
                return false;

            default:
                return false;
        }
    }

    public Card getTopCard() {
        return cards.isEmpty() ? null : cards.get(cards.size() - 1);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public PileType getType() {
        return type;
    }

    public double getX() {
        return rectangle.getX();
    }

    public double getY() {
        return rectangle.getY();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public void clear() {
        cards.clear();
    }
}
