import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class InstructionsScene extends Application{
    private Button startButton;

    @Override
    public void start(Stage instructionStage) {
        instructionStage.setTitle("Solitaire Instructions");
        instructionStage.setScene(new Scene(createGameLayout(), 800, 500));
        instructionStage.show();
        }
    
        private VBox createGameLayout() {
            VBox layout = new VBox();
            layout.setPadding(new Insets(10));
            layout.setSpacing(10);
            layout.setStyle("-fx-background-color:rgb(32, 123, 76);"); 
            layout.setAlignment(Pos.CENTER);
    
          
            Button startButton2 = new Button("Start Game");
            startButton2.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color: rgb(121, 183, 148); -fx-text-fill: black;");
            layout.setAlignment(Pos.CENTER);

            Label titleLabel = new Label("Instructions for Solitaire Game:");
            titleLabel.setFont(new Font("Book Antiqua", 30));
            layout.getChildren().addAll(titleLabel);

            Label instructionsLabel = new Label("1. The goal of Solitaire is to move all cards to the foundation piles. \n" +
                    "2. You can move cards between tableau piles and to the foundation. \n" +
                    "3. Only Kings can be placed in empty tableau spaces. \n" +
                    "4. Use the stock pile to draw cards when you run out of moves. \n" +
                    "5. Follow the rules for moving cards between tableau piles and to the foundation.");
            instructionsLabel.setFont(new Font("Book Antiqua", 20));
            titleLabel.setTranslateY(-60);
            instructionsLabel.setTranslateY(-40);
            layout.getChildren().addAll(instructionsLabel);

            startButton2.setOnAction(e -> {
                try {
                    GameScene gameScene = new GameScene();
                    Stage primaryStage = (Stage) startButton2.getScene().getWindow(); // Get the current stage
                    gameScene.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("Starting game");
            });
            layout.getChildren().addAll(startButton2);
            
          
            return layout;
    }


}
