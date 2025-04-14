import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

// Note: this entire class is basically a placeholder for GameScene.java, gonna update it later on, but just letting you know if you get confused
public class SolitaireGameScreen extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Solitaire Game");
        Button undoButton = new Button("Undo");
        Button restartButton = new Button("Restart");
        undoButton.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color: rgb(121, 183, 148); -fx-text-fill: black;");
        restartButton.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color:rgb(121, 183, 148); -fx-text-fill: black;");
        //startButton.setTranslateX(225);


        
        // Stack to store the history of moves
        Stack<Move> moveHistory = new Stack<>();

        // List to store tableau piles
        private List<TableauPile> tableauPiles = new ArrayList<>();

        

        undoButton.setOnAction(e -> {
            System.out.println("Undoing last move...");
            if (!moveHistory.isEmpty()) {
                Move previousState = moveHistory.pop();
                restoreGameState(previousState);
                System.out.println("Last move undone.");
            } else {
                System.out.println("No moves to undo.");
            }
        
            // Method to deal cards to tableau piles
            
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
        layout.getChildren().addAll(undoButton, restartButton );
        layout.setStyle("-fx-background-color:rgb(32, 123, 76);"); 
        layout.setAlignment(Pos.BOTTOM_RIGHT);

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

