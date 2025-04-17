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