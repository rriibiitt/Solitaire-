import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class GameScene extends Application{
   
    private Button restartButton;
    private Button undoButton;

    @Override
    public void start(Stage gameStage) {
        gameStage.setTitle("Solitaire Game");
        gameStage.setScene(new Scene(createGameLayout(), 800, 500));
        restartButton = new Button("Restart");
        undoButton = new Button("Undo");
        HBox buttonBox;
        restartButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        undoButton.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        buttonBox = new HBox(10, restartButton, undoButton);
        buttonBox.setStyle("-fx-background-color: #2E8B57; -fx-padding: 10px;");
        gameStage.show();
    }
    
    

        public void setRestartButtonAction(Runnable action) {
            restartButton.setOnAction(e -> action.run());
        }
        public void setUndoButtonAction(Runnable action) {
            undoButton.setOnAction(e -> action.run());
        }

        private void restartButton() {
            System.out.println("Restarting game...");
            // Logic to reset the game state
        }
        
        private void undoButton() {
            System.out.println("Undoing last move...");
            if (!moveHistory.isEmpty()) {
                Move lastMove = moveHistory.pop();
                Card card = lastMove.getCard();
                Pile fromPile = lastMove.getFromPile();
                Pile toPile = lastMove.getToPile();
        
                
        
                System.out.println("Undo: Moved " + card + " back to " + fromPile);
            } else {
                System.out.println("No moves to undo.");
            }
        }
        private VBox createGameLayout() {
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            layout.getChildren().addAll();
            layout.setSpacing(10);
            layout.setStyle("-fx-background-color:rgb(32, 123, 76);"); 
            layout.setAlignment(Pos.CENTER);
    
          
            return layout;
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



  
