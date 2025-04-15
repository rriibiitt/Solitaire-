/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGame extends JFrame {
    private Deck deck;
    private Pile tableauPile1;
    private Pile tableauPile2;
    private Pile tableauPile3;
    private Pile tableauPile4;
    private Pile tableauPile5;
    private Pile tableauPile6;
    private Pile tableauPile7;
    

    public NewGame() {
        setTitle("Solitaire");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create new deck and shuffle it
        deck = new Deck();
        deck.shuffle();

        // Create tableau piles
        tableauPile1 = new Pile(true);
        tableauPile2 = new Pile(true);
        tableauPile3 = new Pile(true);
        tableauPile4 = new Pile(true);
        tableauPile5 = new Pile(true);
        tableauPile6 = new Pile(true);
        tableauPile7 = new Pile(true);
        dealCards();

        // "New Game" button
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        add(newGameButton, BorderLayout.NORTH);
    }

    private void dealCards() {
        // Example: Deal a single card to the tableau pile
        if (!deck.isEmpty()) {
            tableauPile.addCard(deck.dealCard());
        }
    }

    private void startNewGame() {
        deck.resetDeck();
        tableauPile = new Pile(true); // Reset the tableau
        dealCards();
        System.out.println("New game started!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NewGame game = new NewGame();
            game.setVisible(true);
        });
    }
}*/