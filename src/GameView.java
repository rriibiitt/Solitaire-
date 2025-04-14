import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.util.List;

public class GameView extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Solitaire Game");
        Scene scene = new Scene(createContent(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private static final int CARD_WIDTH = 80;
    private final GameController controller;
    private final Pane root = new Pane();

    public GameView(GameController controller) {
        this.controller = controller;
    }

    public Pane createContent() {
        root.setPrefSize(800, 600);
        return root;
    }

    public void initializePiles(List<Pile> piles) {
        root.getChildren().clear();
        double x = 50;
        for (Pile pile : piles) {
            StackPane stack = new StackPane();
            stack.setLayoutX(x);
            stack.setLayoutY(100);
            int offset = 0;

            for (Card card : pile.getCards()) {
                Rectangle rect = new Rectangle(80, 120);
                rect.setArcWidth(10);
                rect.setArcHeight(10);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(2);
                rect.setFill(card.isFaceUp() ? Color.WHITE : Color.GRAY);
                rect.setTranslateY(offset);
                stack.getChildren().add(rect);
                offset += 20;
            }

            root.getChildren().add(stack);
            x += 100;
        }
    }
}
