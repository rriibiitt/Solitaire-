import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class GameScene extends Application{
    @Override
    public void start(Stage gameStage) {
        gameStage.setTitle("Solitaire Game");
            gameStage.setScene(new Scene(createGameLayout(), 800, 500));
            gameStage.show();
        }
    
        private VBox createGameLayout() {
            VBox layout = new VBox();
            layout.setPadding(new Insets(10));
            layout.setSpacing(10);
            layout.setStyle("-fx-background-color:rgb(32, 123, 76);"); 
            layout.setAlignment(Pos.CENTER);
    
          
            return layout;
    }

}
