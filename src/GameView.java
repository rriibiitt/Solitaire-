import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private List<ImageView> draggedViews = new ArrayList<>();
    private double dragOffsetX;
    private double dragOffsetY;

    
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
            // Clear all game-related content
            root.getChildren().clear();
            tableauPiles.clear();
            foundationPiles.clear();
            viewToCard.clear();
        
            // Reset the deck and add the foundations and tableau piles
            Deck newDeck = new Deck();
            this.gameDeck = newDeck;
            addDeckAndFoundations();
            dealCards(newDeck);
        
            // Re-add the buttons to their correct position after resetting the game
            buttonBox.getChildren().setAll(undoButton, restartButton);
            buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
            buttonBox.setPadding(new Insets(10));
            rootLayout.setBottom(buttonBox); // Reposition the buttons to the bottom of the screen
        
            // Make sure the root layout is updated
            rootLayout.setCenter(backgroundPane);  // Ensure the background is preserved
        });
        
        

        // Set up the scene and stage
        Scene scene = new Scene(rootLayout, 800, 600);
        addDeckAndFoundations();
        primaryStage.setScene(scene);
        scene.setFill(Color.rgb(32, 123, 76));
        primaryStage.show();
    }
    private void restoreGameState(Move move) {
        Card card = move.getCard();
        Pile fromPile = move.getFromPile();
        Pile toPile = move.getToPile();
    
        // Move card from toPile back to fromPile
        toPile.getCards().remove(card);
        fromPile.getCards().add(card);
    
        // Find the ImageView for the card
        ImageView view = null;
        for (Map.Entry<ImageView, Card> entry : viewToCard.entrySet()) {
            if (entry.getValue() == card) {
                view = entry.getKey();
                break;
            }
        }
    
        // Restore the card's image and position
        if (view != null) {
            view.setImage(new Image(getClass().getResourceAsStream("/cards/" + card.getImageCode() + ".png")));
            view.toFront(); // Bring to front
    
            double newX = fromPile.getX();
            double newY = (fromPile.getType() == Pile.PileType.TABLEAU)
                    ? fromPile.getY() + (fromPile.getCards().size() - 1) * 30
                    : fromPile.getY();
    
            view.setX(newX);
            view.setY(newY);
        } else {
            System.out.println("Card view not found for: " + card.getImageCode());
        }
    
        // Flip top card on fromPile if needed
        Card flipped = null;
        if (fromPile.getType() == Pile.PileType.TABLEAU && !fromPile.getCards().isEmpty()) {
            Card topCard = fromPile.getCards().get(fromPile.getCards().size() - 1);
            if (!topCard.isFaceUp()) {
                topCard.flip();
                flipped = topCard;
                addCardImage(topCard, fromPile.getX(), fromPile.getY() + (fromPile.getCards().size() - 1) * 30);
            }
        }
    
        // Re-add move to history to support redo
        moveHistory.push(new Move(card, fromPile, toPile, flipped));
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
    cardView.setUserData(new double[] { cardView.getX(), cardView.getY() });

    if (!card.isFaceUp()) return; 

    // Collect all cards in the stack starting from the clicked card
    Pile fromPile = findPileContaining(card);
    draggedViews.clear(); // Clear previous dragged views

    boolean startAdding = false;
    for (Card c : fromPile.getCards()) {
        if (c == card) startAdding = true; // Start adding cards to the draggedViews list
        if (startAdding) {
            // Add the corresponding ImageViews to the stack
            for (Map.Entry<ImageView, Card> entry : viewToCard.entrySet()) {
                if (entry.getValue() == c) {
                    draggedViews.add(entry.getKey());
                    break;
                }
            }
        }
    }

    // Bring all cards in the stack to the front
    for (ImageView draggedView : draggedViews) {
        draggedView.toFront();
    }

    // Store drag offset
    dragOffsetX = event.getSceneX() - cardView.getX();
    dragOffsetY = event.getSceneY() - cardView.getY();
});

            
            cardView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && card.isFaceUp()) {
                    attemptAutoMoveToFoundation(card, cardView);
                }
            });            
            
            cardView.setOnMouseDragged(event -> {
                // Move the card with the mouse cursor
                if (!card.isFaceUp()) return;
    
                double mouseX = event.getSceneX() - dragOffsetX;
                double mouseY = event.getSceneY() - dragOffsetY;
                
                for (int i = 0; i < draggedViews.size(); i++) {
                    ImageView view = draggedViews.get(i);
                    view.setX(mouseX);
                    view.setY(mouseY + i * 30); // Keep stacking spacing between cards
                }
            
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
    private void attemptAutoMoveToFoundation(Card card, ImageView cardView) {
        Pile fromPile = findPileContaining(card);
    
        for (Pile foundation : foundationPiles) {
            if (foundation.canAccept(card)) {
                // Move card from tableau to foundation
                fromPile.removeCard(card);
    
                // Flip the next card in the old pile if it's a tableau and not face-up
                if (fromPile.getType() == Pile.PileType.TABLEAU && !fromPile.getCards().isEmpty()) {
                    Card topCard = fromPile.getCards().get(fromPile.getCards().size() - 1);
                    if (!topCard.isFaceUp()) {
                        topCard.flip();
                        addCardImage(topCard, fromPile.getX(), fromPile.getY() + (fromPile.getCards().size() - 1) * 30);
                    }
                }
    
                // Add the card to the foundation pile
                foundation.addCard(card);
    
                // Position the card on the foundation
                double newX = foundation.getX();
                double newY = foundation.getY();
    
                cardView.setX(newX);
                cardView.setY(newY);
                cardView.toFront(); // Keep on top
    
                moveHistory.push(new Move(card, fromPile, foundation)); // Save move for undo
    
                System.out.println("Auto-moved " + card + " to foundation.");
    
                // ✅ Check win condition
                if (checkWinCondition()) {
                    showWinMessage();
                }
                break;
            }
        }
    }
    
    
    private void handleCardDrop(Card card, ImageView cardView) {
        double[] oldPos = (double[]) cardView.getUserData();
        cardView.setUserData(oldPos);
        boolean validMove = false;
        cardView.toFront();
    
        Pile fromPile = findPileContaining(card);
        Pile toPile = null;
    
        // List all piles to check for valid drop locations
        List<Pile> allPiles = new ArrayList<>();
        allPiles.addAll(tableauPiles);
        allPiles.addAll(foundationPiles);
        allPiles.add(wastePile); // Include waste pile in the check
    
        for (Pile pile : allPiles) {
            double dx = cardView.getX() - pile.getX();
            double dy = cardView.getY() - pile.getY();
    
            // Check if the card is near the pile and whether the move is valid
            if (Math.abs(dx) < CARD_WIDTH && Math.abs(dy) < CARD_HEIGHT) {
                if (pile.canAccept(card)) {
                    validMove = true;
                    toPile = pile;
                    break;
                }
            }
        }
    
        if (validMove && toPile != null) {
            // Move card or stack of cards to the valid pile
            List<Card> cardsToMove = new ArrayList<>();
            List<ImageView> viewsToMove = new ArrayList<>();
    
            // Identify if we're dealing with a stack of cards
            boolean startAdding = false;
            for (Card c : fromPile.getCards()) {
                if (c == card) {
                    startAdding = true;
                }
                if (startAdding) {
                    cardsToMove.add(c);
                    // Add the corresponding ImageViews to the stack
                    for (Map.Entry<ImageView, Card> entry : viewToCard.entrySet()) {
                        if (entry.getValue() == c) {
                            viewsToMove.add(entry.getKey());
                            break;
                        }
                    }
                }
            }
    
            // Now move the stack of cards (not just a single card)
            for (int i = 0; i < viewsToMove.size(); i++) {
                ImageView view = viewsToMove.get(i);
                Card movedCard = cardsToMove.get(i);
    
                // Remove the card from the old pile and add it to the new pile
                fromPile.removeCard(movedCard);
                toPile.addCard(movedCard);
    
                double newX = toPile.getX();
                double newY = (toPile.getType() == Pile.PileType.TABLEAU)
                        ? toPile.getY() + (toPile.getCards().size() - 1) * 30
                        : toPile.getY();
    
                // Update the position of the card's ImageView
                view.setX(newX);
                view.setY(newY);
            }
    
            // If the fromPile is a tableau, flip the top card if necessary
            if (fromPile.getType() == Pile.PileType.TABLEAU && !fromPile.getCards().isEmpty()) {
                Card topCard = fromPile.getCards().get(fromPile.getCards().size() - 1);
                if (!topCard.isFaceUp()) {
                    topCard.flip();
                    addCardImage(topCard, fromPile.getX(), fromPile.getY() + (fromPile.getCards().size() - 1) * 30);
                    ImageView topCardView = null;
                    for (Map.Entry<ImageView, Card> entry : viewToCard.entrySet()) {
                        if (entry.getValue() == topCard) {
                            topCardView = entry.getKey();
                            break;
                        }
                    }
                    if (topCardView != null) {
                        topCardView.setImage(new Image(getClass().getResourceAsStream("/cards/" + topCard.getImageCode() + ".png")));
                        topCardView.toFront();
                    }
                }
            }
    
            moveHistory.push(new Move(card, fromPile, toPile)); // Save the move for undo
            System.out.println("Moved card(s) to pile of type: " + toPile.getType());
        } else {
            // If the move is invalid, return the card to its original position
            if (fromPile == wastePile) {
                cardView.setX(wastePile.getX());
                cardView.setY(wastePile.getY());
            } else {
                cardView.setX(oldPos[0]);
                cardView.setY(oldPos[1]);
            }
    
            System.out.println("Invalid move!");
        }
    }
    
    
    
    private Pile findPileContaining(Card card) {
        for (Pile pile : tableauPiles) {
            if (pile.getCards().contains(card)) return pile;
        }
        for (Pile pile : foundationPiles) {
            if (pile.getCards().contains(card)) return pile;
        }
        if (wastePile.getCards().contains(card)) return wastePile;
        return null;
    }
    
    
    
    
    private void addDeckAndFoundations() {
        // Load the back image of the card
        String backImagePath = "/cards/back.png"; // Make sure this file exists
        Image backImage = new Image(getClass().getResourceAsStream(backImagePath));
        ImageView backImageView = new ImageView(backImage);
        
        // Set up the deck rectangle (invisible hitbox)
        deckRect = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        deckRect.setX(20);
        deckRect.setY(20);
        deckRect.setFill(Color.TRANSPARENT); // Make it invisible
        
        // Set the image position and size
        backImageView.setX(deckRect.getX());
        backImageView.setY(deckRect.getY());
        backImageView.setFitWidth(CARD_WIDTH);
        backImageView.setFitHeight(CARD_HEIGHT);
    
        // Add both to root
        root.getChildren().add(backImageView);
        root.getChildren().add(deckRect);
    
        // Add click event to either one (your choice)
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
            pile.getRectangle().toBack();
 
        }
    }
    private boolean checkWinCondition() {
        for (Pile pile : foundationPiles) {
            if (pile.getCards().size() != 13) {
                return false;
            }
        }
        return true;
    }
    
    private void showWinMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText("You won!");
        alert.setContentText("You've completed the game. Great job!");
        alert.showAndWait();
    }
    
    
}

