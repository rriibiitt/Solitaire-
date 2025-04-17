import javafx.animation.RotateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.util.Duration;

public class FlipCard extends StackPane {
    private Node front;
    private Node back;

    public FlipCard(Node front, Node back) {
        this.front = front;
        this.back = back;
        getChildren().addAll(back, front);
        showFront();
    }

    public void showFront() {
        front.setVisible(true);
        back.setVisible(false);
    }

    public void showBack() {
        front.setVisible(false);
        back.setVisible(true);
    }

    public void flip() {
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), this);
        rotate.setByAngle(180);
        rotate.setCycleCount(1);
        rotate.setAutoReverse(false);
        rotate.setOnFinished(e -> {
            if (front.isVisible()) {
                showBack();
            } else {
                showFront();
            }
        });
        rotate.play();
    }
}
