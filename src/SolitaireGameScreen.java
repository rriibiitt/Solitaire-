import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import java.util.Stack;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

// Define the Value enum
enum Value {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
}

// Note: this entire class is basically a placeholder for GameScene.java, gonna update it later on, but just letting you know if you get confused
public class SolitaireGameScreen extends Application {

    // Constants for card dimensions
    private static final double CARD_WIDTH = 100.0;
    private static final double CARD_HEIGHT = 150.0;
    private static final double PADDING = 25;
    private static final double ROUNDING_FACTOR = 10;

    // Method to draw a card at a specific position
    private void drawCard(Card card, double x, double y) {
        System.out.println("Drawing card " + card + " at position (" + x + ", " + y + ")");
        // Add your implementation for rendering the card here
    }

    // Method to draw an empty placeholder at a specific position
    private void drawEmpty(double x, double y) {
        System.out.println("Drawing empty placeholder at position (" + x + ", " + y + ")");
        // Add your implementation for rendering an empty placeholder here
        System.out.println("Rendering an empty placeholder visually.");
        Rectangle placeholder = new Rectangle(x, y, CARD_WIDTH, CARD_HEIGHT);
        placeholder.setFill(Color.LIGHTGRAY);
        // Add the placeholder to your scene or layout
        // Assuming you have a Pane or Group to add this placeholder to
        // Example: Add the placeholder to a Group named 'root'
        Group root = new Group();
        root.getChildren().add(placeholder);
    }

    // Method to draw the back of a card at a specific position
    private void drawCardBack(double x, double y) {
        System.out.println("Drawing card back at position (" + x + ", " + y + ")");
        Rectangle cardBack = new Rectangle(x, y, CARD_WIDTH, CARD_HEIGHT);
        cardBack.setFill(Color.DARKBLUE); // Example color for the back of the card
        // Add the card back to your scene or layout
        // Assuming you have a Pane or Group to add this card back to
        // Example: Add the card back to a Group named 'root'
        Group root = new Group();
        root.getChildren().add(cardBack);
    }
    // List to store tableau piles
    private List<TableauPile> tableauPiles = new ArrayList<>();

    // List to store board piles
    private List<Stack<Card>> board = new ArrayList<>();

    // Stack to represent the hand
    private Stack<Card> hand = new Stack<>();

    // Stack to represent the waste pile
    private Stack<Card> waste = new Stack<>();

    // List to store foundation piles
    private List<Stack<Card>> foundations = new ArrayList<>();

    // Stack to store move history for undo functionality
    private Stack<Move> moveHistory = new Stack<>();

   
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Solitaire Game");
        Button undoButton = new Button("Undo");
        Button restartButton = new Button("Restart");
        undoButton.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color: rgb(121, 183, 148); -fx-text-fill: black;");
        restartButton.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color:rgb(121, 183, 148); -fx-text-fill: black;");
        //startButton.setTranslateX(225);


        //research how to add rectangles to the scene
        //add 


        Rectangle rectangle = new Rectangle(100, 150);
        rectangle.setFill(Color.LIGHTGRAY); 
        rectangle.setX(20);
        rectangle.setY(20);
        rectangle.setWidth(100);
        rectangle.setHeight(150);

        undoButton.setOnAction(e -> {
            System.out.println("Undoing last move...");
            if (!moveHistory.isEmpty()) {
                Move previousState = moveHistory.pop();
                restoreGameState(previousState);
                System.out.println("Last move undone.");
            } else {
                System.out.println("No moves to undo.");
            }
        
            
        });


        restartButton.setOnAction(e -> {
            System.out.println("Restarting game...");
            // Logic to reset the game state
            moveHistory.clear(); // Clear the move history
            // Assuming you have a deck and tableau piles, reset them
            // Reset the deck
            Deck deck = new Deck();
            deck.resetDeck();
            // Clear and reset tableau piles
            for (TableauPile pile : tableauPiles) {
                pile.clear();
            }
            
            // Deal cards again to tableau piles
            dealCards(); // Call the method to deal cards
            
            System.out.println("Game restarted.");
        });
    


        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(undoButton, restartButton,rectangle );
        layout.setStyle("-fx-background-color:rgb(32, 123, 76);"); 
        //layout.setAlignment(Pos.BOTTOM_RIGHT);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private void restoreGameState(Move previousState) {
        if (previousState != null) {
            // Restore the card to its previous position
            previousState.getFromPile().addCard(previousState.getCard());
            previousState.getToPile().removeCard(previousState.getCard());
            System.out.println("Card " + previousState.getCard() + " moved back to " + previousState.getCard());
        }
        System.out.println("Restoring game state to: " + previousState);
    }

    // Method to deal cards to tableau piles
    private void dealCards() {
        System.out.println("Dealing cards to tableau piles...");
        Deck deck = new Deck();
        deck.shuffle(); // Shuffle the deck before dealing

        // Assuming there are 7 tableau piles and the first pile gets 1 card, the second gets 2, and so on
        for (int i = 0; i < 7; i++) {
            TableauPile pile = new TableauPile();
            for (int j = 0; j <= i; j++) {
            if (!deck.isEmpty()) {
                Card card = deck.drawCard();
                if (j == i) {
                card.setFaceUp(true); // The top card of each pile is face up
                }
                pile.addCard(card);
            }
            }
            tableauPiles.add(pile); // Add the pile to the list of tableau piles
        }
        // Example: Iterate through tableauPiles and assign cards from the deck
    }
}