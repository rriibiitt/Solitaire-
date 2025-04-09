import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Stack;

import javax.smartcardio.Card;


public class buttons2 extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Buttons Example");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    Button restartButton = new Button("Restart");
    Button undoButton = new Button("Undo");
    HBox buttonBox;
    VBox root;

    public buttons2() {
        // Apply styling
        restartButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        undoButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        

        buttonBox = new HBox(10, restartButton, undoButton);
        buttonBox.setStyle("-fx-background-color: #2E8B57; -fx-padding: 10px;");
        root = new VBox(10, buttonBox);
        root.setStyle("-fx-background-color: #F0F8FF; -fx-padding: 20px;");
    }

    public void setRestartButtonAction(Runnable action) {
        restartButton.setOnAction(e -> action.run());
    }
    public void setUndoButtonAction(Runnable action) {
        undoButton.setOnAction(e -> action.run());
    }

    private void restartGame() {
        System.out.println("Restarting game...");
        // Logic to reset the game state
    }
    
    private void undoMove() {
        System.out.println("Undoing last move...");
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.pop();
            Card card = lastMove.getCard();
            Pile fromPile = lastMove.getFromPile();
            Pile toPile = lastMove.getToPile();
    
            // Move the card back
            toPile.removeCard(card);
            fromPile.addCard(card);
    
            System.out.println("Undo: Moved " + card + " back to " + fromPile);
        } else {
            System.out.println("No moves to undo.");
        }
    }
    

    class Move {
        private Card card;
        private Pile fromPile;
        private Pile toPile;

        public Move(Card card, Pile fromPile, Pile toPile) {
            this.card = card;
            this.fromPile = fromPile;
            this.toPile = toPile;
        }

        public Card getCard() {
            return card;
        }

        public Pile getFromPile() {
            return fromPile;
        }

        public Pile getToPile() {
            return toPile;
        }
    }
        Stack<Move> moveHistory = new Stack<>();

        public void addMove(Card card, Pile fromPile, Pile toPile) {
            Move move = new Move(card, fromPile, toPile);
            moveHistory.push(move);
    }
}
