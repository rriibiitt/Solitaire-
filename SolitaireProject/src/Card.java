import java.util.*;  

public class Card {
    public enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
    private Suit suit;
    private int value; // 1 = Ace, 11 = Jack, etc.
    private boolean faceUp;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        this.faceUp = false;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value; 
    } 

    public boolean isRed() {
        return suit == Suit.HEARTS || suit == Suit.DIAMONDS;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void flip() {
        faceUp = !faceUp;
    }

    @Override
    public String toString() {
        String[] names = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        return (faceUp ? names[value - 1] + " of " + suit : "Card Face Down");
    }
}

/*
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
} */