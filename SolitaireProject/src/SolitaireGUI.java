import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class SolitaireGUI extends JFrame {
    private SolitaireLogic game;
    private JButton drawButton;

    public SolitaireGUI() {
        game = new SolitaireLogic();
        setTitle("Solitaire");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        drawButton = new JButton("Draw");
        drawButton.setBounds(50, 50, 100, 30);
        drawButton.addActionListener(e -> {
            game.drawFromStock();
            repaint();
        });
        add(drawButton);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawPile(g, game.stock, 50, 100);
        drawPile(g, game.waste, 150, 100);

        for (int i = 0; i < 7; i++) {
            drawPile(g, game.tableau[i], 50 + i * 100, 200);
        }

        if (game.checkWin()) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("You Win!", 300, 500);
        }
    }

    private void drawPile(Graphics g, Pile pile, int x, int y) {
        int offsetY = 0;
        for (Card card : pile.getCards()) {
            g.drawRect(x, y + offsetY, 60, 90);
            if (card.isFaceUp()) {
                g.drawString(card.toString(), x + 5, y + offsetY + 20);
            } else {
                g.drawString("X", x + 25, y + offsetY + 20);
            }
            offsetY += 20;
        }
    }

    // test
    JButton moveToFoundationButton = new JButton("To Foundation");
    moveToFoundationButton.setBounds(160, 50, 150, 30);
    moveToFoundationButton.addActionListener(e -> {
        Card top = game.waste.topCard();
        if (top != null && game.moveToFoundation(top)) {
            game.waste.removeTopCard();
        }
        repaint();
    });
    add(moveToFoundationButton);

    // for movement
    private Pile selectedPile = null;
    private Card selectedCard = null;

    addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            // Check waste pile
            if (x >= 150 && x <= 210 && y >= 100 && y <= 190) {
                if (game.waste.topCard() != null) {
                    selectedCard = game.waste.topCard();
                    selectedPile = game.waste;
                    repaint();
                }
                return;
            }

            for (int i = 0; i < game.foundations.length; i++) {
                int fx = 400 + i * 100;
                if (x >= fx && x <= fx + 60 && y >= 100 && y <= 190) {
                    if (game.foundations[i].topCard() != null) {
                        selectedCard = game.foundations[i].topCard();
                        selectedPile = game.foundations[i];
                        repaint();
                    }
                    return;
                }
            }
            
    
            // Check which tableau pile was clicked
            for (int i = 0; i < game.tableau.length; i++) {
                int pileX = 50 + i * 100;
                int pileY = 250;
                if (x >= pileX && x <= pileX + 60) {
                    Pile pile = game.tableau[i];
                    int clickedIndex = (y - pileY) / 20;
                    if (clickedIndex >= 0 && clickedIndex < pile.getCards().size()) {
                        Card clickedCard = pile.getCards().get(clickedIndex);
                        if (!clickedCard.isFaceUp()) return;
    
                        if (selectedCard == null) {
                            // Select a face-up card
                            selectedCard = clickedCard;
                            selectedPile = pile;
                        } else {
                            // Try to move selected card(s)
                            if (moveCardToTableau(selectedPile, selectedCard, pile)) {
                                selectedCard = null;
                                selectedPile = null;
                            }
                        }
                        repaint();
                        return;
                    }
                }
            }
    
            // Deselect if clicked elsewhere
            selectedCard = null;
            selectedPile = null;
            repaint();
        }
    });

    private boolean moveCardToTableau(Pile fromPile, Card card, Pile toPile) {
        Stack<Card> movingStack = new Stack<>();
        boolean startAdding = false;

        for (Card c : fromPile.getCards()) {
            if (c == card) startAdding = true;
            if (startAdding) movingStack.add(c);
        }

        if (movingStack.isEmpty()) return false;

        Card topTarget = toPile.topCard();
        Card firstCard = movingStack.firstElement();

        // Move to empty tableau pile only if it's a King
        if (toPile.isEmpty()) {
            if (firstCard.getValue() == 13) {
                for (Card c : movingStack) {
                    toPile.addCard(c);
                }
                fromPile.getCards().removeAll(movingStack);
                flipLastCard(fromPile);
                return true;
            }
            return false;
        }

        // Otherwise, check alternating color and descending value
        if (topTarget.isFaceUp() &&
            topTarget.isRed() != firstCard.isRed() &&
            topTarget.getValue() == firstCard.getValue() + 1) {
            for (Card c : movingStack) {
                toPile.addCard(c);
            }
            fromPile.getCards().removeAll(movingStack);
            flipLastCard(fromPile);
            return true;
        }

        return false;
    }
    private void flipLastCard(Pile pile) {
        if (!pile.isEmpty() && !pile.topCard().isFaceUp()) {
            pile.topCard().flip();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SolitaireGUI gui = new SolitaireGUI();
            gui.setVisible(true);
        });
    }
}
