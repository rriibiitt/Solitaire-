import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Solitaire");
        Label titleLabel = new Label("Welcome to Solitaire!");
        titleLabel.setFont(new Font("Book Antiqua", 30));
        Button startButton = new Button("Start Game");
        Button instructionsButton = new Button("Instructions");
        Button exitButton = new Button("Exit");
        startButton.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color: rgb(121, 183, 148); -fx-text-fill: black;");
        instructionsButton.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color:rgb(121, 183, 148); -fx-text-fill: black;");
        exitButton.setStyle("-fx-font-size: 17px; -fx-padding: 5px; -fx-background-color: rgb(121, 183, 148); -fx-text-fill: black;");
        //startButton.setTranslateX(225);
        titleLabel.setTranslateY(-60);


        
        startButton.setOnAction(e -> {
            try {
                GameView gameScene = new GameView();
                gameScene.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Starting game");
        });


        instructionsButton.setOnAction(e -> {
            try {
                InstructionsScene instructionScene = new InstructionsScene();
                instructionScene.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Showing instructions");
        });
        exitButton.setOnAction(e -> {
            System.out.println("Exiting game");
            primaryStage.close();
        });
    
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel, startButton, instructionsButton, exitButton);
        layout.setStyle("-fx-background-color:rgb(32, 123, 76);"); 
        layout.setAlignment(Pos.CENTER);
        titleLabel.setStyle("-fx-text-fill:rgb(24, 24, 24);"); 

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        

        
        
    }
}

