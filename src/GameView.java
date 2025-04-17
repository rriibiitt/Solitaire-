import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GameView extends Application {
    private List<Pile> tableauPiles = new ArrayList<>();
    private Map<ImageView, Card> viewToCard= new HashMap<>();
    private List<Pile> foundationPiles = new ArrayList<>();
        private Stack<Move> moveHistory = new Stack<>();

    private Group root;
    private Pile wastePile;
    private Rectangle deckRect;
    private Deck gameDeck; // so we can use it outside dealCards()
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane rootLayout = new BorderPane();
        root = new Group();
        Pane backgroundPane = new Pane(root);
        backgroundPane.setStyle("-fx-background-color: rgb(32,123,76);"); // set green background
        rootLayout.setCenter(backgroundPane);



        Deck deck = new Deck();
        dealCards(deck);

        primaryStage.setTitle("Solitaire Game");
        // Create and add undo and restart buttons
        Button undoButton = new Button("Undo");
        Button restartButton = new Button("Restart");

        HBox buttonBox = new HBox(10, undoButton, restartButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setPadding(new Insets(10));
        rootLayout.setBottom(buttonBox); // Align to the bottom center of the screen

        
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
            root.getChildren().clear();
            tableauPiles.clear();
            foundationPiles.clear();
            viewToCard.clear();
            Deck newDeck = new Deck();
            this.gameDeck = new Deck();
            addDeckAndFoundations();
            dealCards(newDeck);
            root.getChildren().addAll(undoButton, restartButton); // re-add buttons

        });
        
        
        // Add buttons to root

        // Set up the scene and stage
        Scene scene = new Scene(rootLayout, 800, 600);
        addDeckAndFoundations();
        primaryStage.setScene(scene);
        scene.setFill(Color.rgb(32, 123, 76));
        primaryStage.show();
    }
    private void restoreGameState(Move previousState) {
        Card card = previousState.getCard();
        Pile from = previousState.getFromPile();
        Pile to = previousState.getToPile();
    
        to.removeCard(card);
        from.addCard(card);
    
        // Move the ImageView back
        ImageView view = null;
        for (Map.Entry<ImageView, Card> entry : viewToCard.entrySet()) {
            if (entry.getValue() == card) {
                view = entry.getKey();
                break;
            }
        }
    
        if (view != null) {
            double newX = from.getX();
            double newY = (from.getType() == Pile.PileType.TABLEAU)
                    ? from.getY() + (from.getCards().size() - 1) * 30
                    : from.getY();
    
            view.setX(newX);
            view.setY(newY);
        }
    
        System.out.println("Undo: Moved " + card + " back to " + from.getType());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    // Define constants for card dimensions and padding
    // Define constants for card dimensions and padding
    private static final double CARD_WIDTH = 100;
    private static final double CARD_HEIGHT = 150;
    private static final double PADDING = 10;
    
    
    public void addCardImage(Card card, double x, double y) {
        try {
            String cardCode = card.isFaceUp() ? card.getImageCode() : "back";
            Image cardImage = new Image(getClass().getResourceAsStream("/cards/" + cardCode + ".png"));
            ImageView cardView = new ImageView(cardImage);
            cardView.setX(x);
            cardView.setY(y);
            cardView.setFitWidth(CARD_WIDTH);
            cardView.setFitHeight(CARD_HEIGHT);
    
            root.getChildren().add(cardView);
            viewToCard.put(cardView, card);
    
            // Drag events
            cardView.setOnMousePressed(event -> {
                cardView.toFront();
                cardView.setUserData(new double[]{cardView.getX(), cardView.getY()});
            });
    
            cardView.setOnMouseDragged(event -> {
                // Move the card with the mouse cursor
                cardView.setX(event.getSceneX() - CARD_WIDTH / 2);
                cardView.setY(event.getSceneY() - CARD_HEIGHT / 2);
            
                // Reset any previous highlights
                for (Pile pile : tableauPiles) {
                    pile.getRectangle().setStroke(null);  // Reset previous highlights by removing borders
                }
            
                boolean validMove = false;
            
                // Iterate over each pile and highlight valid ones
                List<Pile> allPiles = new ArrayList<>();
                allPiles.addAll(tableauPiles);
                allPiles.addAll(foundationPiles);
                for (Pile pile : allPiles) {
                    double dx = cardView.getX() - pile.getX();
                    double dy = cardView.getY() - pile.getY();
                
                    if (Math.abs(dx) < CARD_WIDTH && Math.abs(dy) < CARD_HEIGHT) {
                        if (pile.canAccept(card)) {
                            pile.getRectangle().setStroke(Color.LIMEGREEN);
                            validMove = true;
                        }
                    }
                }
            
                // If no valid pile is found, do not highlight any pile
                if (!validMove) {
                    // Optionally, add visual feedback for invalid piles (e.g., red border)
                    for (Pile pile : tableauPiles) {
                        pile.getRectangle().setStroke(Color.RED); // You can also use other colors for invalid piles
                    }
                }
            });
    
            cardView.setOnMouseReleased(event -> {
                handleCardDrop(card, cardView);
            });
    
        } catch (Exception e) {
            System.out.println("Could not load card image: " + card.getImageCode());
            e.printStackTrace();
        }
    }
    private void handleCardDrop(Card card, ImageView cardView) {
        double[] oldPos = (double[]) cardView.getUserData();
        boolean validMove = false;
    
        List<Pile> allPiles = new ArrayList<>();
        allPiles.addAll(tableauPiles);
        allPiles.addAll(foundationPiles);
        allPiles.add(wastePile);
    
        for (Pile pile : allPiles) {
            double dx = cardView.getX() - pile.getX();
            double dy = cardView.getY() - pile.getY();
    
            if (Math.abs(dx) < CARD_WIDTH && Math.abs(dy) < CARD_HEIGHT) {
                if (pile.canAccept(card)) {
                    // Remove from current pile
                    for (Pile p : tableauPiles) {
                        if (p.getCards().contains(card)) {
                            p.removeCard(card);
                            break;
                        }
                    }

    
                    // Add to new pile
                    pile.addCard(card);
                    System.out.println("Moved to pile of type: " + pile.getType());
    
                    // Set new position
                    double newX = pile.getX();
                    double newY = (pile.getType() == Pile.PileType.TABLEAU)
                            ? pile.getY() + (pile.getCards().size() - 1) * 30
                            : pile.getY();
    
                    cardView.setX(newX);
                    cardView.setY(newY);
                    validMove = true;
                    break;
                }
            }
        }
    
        if (!validMove) {
            cardView.setX(oldPos[0]);
            cardView.setY(oldPos[1]);
            System.out.println("Invalid move!");
        }
    }
    
    
    
    private void addDeckAndFoundations() {
        deckRect = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        deckRect.setFill(Color.DARKBLUE);
        deckRect.setX(20);
        deckRect.setY(20);
        root.getChildren().add(deckRect);
    
        // Add click event to draw from deck
        deckRect.setOnMouseClicked(e -> drawFromDeck());
    
        // Waste pile placeholder
        double wasteX = deckRect.getX() + CARD_WIDTH + PADDING;
        double wasteY = deckRect.getY();
        wastePile = new Pile(Pile.PileType.WASTE, wasteX, wasteY);
        root.getChildren().add(wastePile.getRectangle());
    
        // Foundation piles
        for (int i = 0; i < 4; i++) {
            double x = 350 + i * (CARD_WIDTH + PADDING);
            double y = 20;
            Pile foundationPile = new Pile(Pile.PileType.FOUNDATION, x, y);
            foundationPiles.add(foundationPile);
            root.getChildren().add(foundationPile.getRectangle());
            foundationPile.getRectangle().setStroke(Color.GOLD);
            foundationPile.getRectangle().setStrokeWidth(2);
            foundationPile.getRectangle().setFill(Color.LIGHTGRAY);
        }
    }
    private void drawFromDeck() {
        if (gameDeck.isEmpty()) {
            if (wastePile.isEmpty()) {
                System.out.println("No cards to recycle.");
                return;
            }
    
            // ♻️ Recycle waste pile
            List<Card> wasteCards = new ArrayList<>(wastePile.getCards());
            for (Card card : wasteCards) {
                card.flip(); // Flip back to face-down
                gameDeck.addCard(card); // Return to deck
            }
            wastePile.clear(); // Clear the waste pile
            removeWasteImages();
            System.out.println("Recycled waste pile into deck.");
            return;
        }
    
        // 🃏 Draw a card from the deck
        Card card = gameDeck.draw();
        card.flip(); // Face-up
        wastePile.addCard(card);
        addCardImage(card, wastePile.getX(), wastePile.getY());
    }
    private void removeWasteImages() {
        List<Node> toRemove = new ArrayList<>();
        for (Map.Entry<ImageView, Card> entry : viewToCard.entrySet()) {
            if (wastePile.getCards().contains(entry.getValue())) {
                toRemove.add(entry.getKey());
            }
        }
        root.getChildren().removeAll(toRemove);
        toRemove.forEach(node -> viewToCard.remove((ImageView) node));
    }
    
    
    private void dealCards(Deck deck) {
        double startX = 20;
        this.gameDeck= deck;
        double y = 175; // Starting Y-position for the first pile
        
        for (int pileIndex = 0; pileIndex < 7; pileIndex++) {
            double x = startX + pileIndex * (CARD_WIDTH + PADDING);
            Pile pile = new Pile(Pile.PileType.TABLEAU, x, y);
            
            for (int j = 0; j <= pileIndex; j++) {
                Card card = deck.draw();
                if (j == pileIndex) card.flip(); // Flip the top card of each pile
                pile.addCard(card);
                
                // Correct Y-position for each card in the pile
                double offsetY = y + j * 30; // Stack cards vertically with an offset
                addCardImage(card, x, offsetY); // Add card image at the correct position
            }
            
            // Print the size of the current pile
            System.out.println("Pile " + pileIndex + " size: " + pile.getCards().size());
        
            tableauPiles.add(pile); // Add pile to the tableau
            root.getChildren().add(pile.getRectangle());

        }
    }
    
    
    
    
}

