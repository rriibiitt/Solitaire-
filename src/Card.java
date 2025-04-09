public class Card {
    private String rank;
    private String suit;
    private int value;
    private boolean faceUp = false;

    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.faceUp = false;
    }
    public String rank() {
        return rank;
    }
    public String suit() {
        return suit;
    }
    public int value() {
        return value;
    }
    public boolean isFaceUp() {
        return faceUp;
    }
    public boolean matches(Card otherCard) { // 
        boolean hasSameSuit = this.suit.equals(otherCard.suit()); // Check if suits are the same
        boolean hasSameRank = this.rank.equals(otherCard.rank()); // Check if ranks are the same
        return this.suit.equals(otherCard.suit()) && 
               this.rank.equals(otherCard.rank()) &&
               this.value == otherCard.value(); // Check if values are the same
    }
    @Override
    public String toString() {
        return rank + " of " + suit + " (point value = " + value + ")" + Integer.toString(this.value) + " "; // Return a string representation of the card

    }
}