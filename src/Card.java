public class Card {
    private final Deck.Rank rank;
    private final Deck.Suit suit;
    private boolean faceUp;

    
    
    public Card(Deck.Rank rank, Deck.Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.faceUp = false;
    }

    public Deck.Rank getRank() {
        return rank;
    }

    public Deck.Suit getSuit() {
        return suit;
    }

    public boolean isFaceUp() {
        return faceUp;
    }
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public void flip() {
        faceUp = !faceUp;
    }
    public boolean isRed() {
        return suit == Deck.Suit.HEARTS || suit == Deck.Suit.DIAMONDS;
    }
    public boolean isBlack() {
        return suit == Deck.Suit.SPADES || suit == Deck.Suit.CLUBS;
    }
    public boolean isAce() {
        return rank == Deck.Rank.ACE;
    }
    public boolean isKing() {
        return rank == Deck.Rank.KING;
    }
    public boolean isQueen() {
        return rank == Deck.Rank.QUEEN;
    }
    public boolean isJack() {
        return rank == Deck.Rank.JACK;
    }
    public boolean canStack(Card other) {
        if (this.isRed() && other.isBlack()) {
            return this.rank.ordinal() == other.rank.ordinal() + 1;
        } else if (this.isBlack() && other.isRed()) {
            return this.rank.ordinal() == other.rank.ordinal() + 1;
        }
        return false;
    }
    public boolean canStackOnFoundation(Card other) {
        if (this.isRed() && other.isRed()) {
            return this.rank.ordinal() == other.rank.ordinal() + 1;
        } else if (this.isBlack() && other.isBlack()) {
            return this.rank.ordinal() == other.rank.ordinal() + 1;
        }
        return false;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    //card images
    public String getImagePath() {
        if (!faceUp) {
            return "images/card_back.png"; // Path to the card back image
        } else {
            return "images/" + rank.toString().toLowerCase() + " of " + suit.toString().toLowerCase() + ".png";
        }
    }
}
