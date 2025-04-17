import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class Card extends StackPane{
    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    private final Suit suit;
    private boolean isFaceUp;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = false;

        setOnMouseClicked(e -> handleClick());
    }
    
    private void handleClick() {
        if(!isFaceUp) {
            return;

        }
        System.out.println("Card clicked: " + this.toString());
    }

    
    public void flip() {
        isFaceUp = !isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
        updateVisual();
    }

    private void updateVisual() {
        if (isFaceUp){
            // Update the visual representation to show the card face
            getChildren().clear();
            getChildren().add(new javafx.scene.control.Label(this.toString()));
        } else {
            // Update the visual representation to show the card back
            getChildren().clear();
            getChildren().add(new javafx.scene.control.Label("Card Back"));
        }
        System.out.println("Visual updated for card: " + this.toString());
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public Suit getSuit() {
        return suit;
    }

    
    public Rank getRank() {
        return rank;
    }
    
    
    public boolean isRed() {
        return suit == Suit.HEARTS || suit == Suit.DIAMONDS;
    }

    public String getImageCode() {
        if (!isFaceUp) return "back";

        String rankCode = switch (rank) {
            case ACE -> "A";
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE -> "9";
            case TEN -> "10";
            case JACK -> "J";
            case QUEEN -> "Q";
            case KING -> "K";
        };

        String suitCode = switch (suit) {
            case HEARTS -> "H";
            case DIAMONDS -> "D";
            case CLUBS -> "C";
            case SPADES -> "S";
        };

        return rankCode + suitCode; // e.g., "7C", "QH"
    }

    public boolean canStack(Card other) {
        // Solitaire: alternating colors, descending value
        return this.isRed() != other.isRed()
            && this.rank.ordinal() == other.rank.ordinal() + 1;
    }
    public boolean canPlaceOnFoundation(Card topCard) {
        return this.suit == topCard.getSuit() &&
               this.rank.ordinal() == topCard.getRank().ordinal() + 1;
    }

    

    @Override
    public String toString() {
        return getImageCode() + (isFaceUp ? " (face up)" : " (face down)");
    }
    public Color getColor() {
        if (suit == Suit.HEARTS || suit == Suit.DIAMONDS) {
            return Color.RED;
        } else {
            return Color.BLACK;
        }
    }
    public Node getVisualRepresentation() {
        // Return a visual representation of the card (e.g., an ImageView or a Label)
        return new javafx.scene.control.Label(this.toString());
    }
}
// 