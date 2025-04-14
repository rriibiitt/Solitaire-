// Removed incorrect import for javax.smartcardio.Card

public class SolitaireGame {
    private Pile[] tableauPiles; // Declare tableauPiles
    private Foundation[] foundations; // Declare foundations
    public SolitaireGame(Pile[] tableauPiles, Foundation[] foundations) {
        this.tableauPiles = tableauPiles; // Initialize tableauPiles
        this.foundations = foundations; // Initialize foundations
    }
    public boolean canAutoWin() {
        for (Pile tableauPile : tableauPiles) {
            for (Card card : tableauPile.getCards()) {
                if (card.isFaceUp()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void autoWin() {
        boolean moved = false;
        for (Pile tableauPile : tableauPiles) {
            if (!tableauPile.isEmpty()) {
                Card topCard = tableauPile.topCard();
                for (Foundation foundation : foundations) {
                    if (foundation.canAccept(topCard)) {
                        foundation.addCard(tableauPile.removeTopCard());
                        moved = true;
                        break;
                    }
                }
            }
        }
    }
}

/*
 * game logic
 * public class Solitaire {
    private Deck deck;
    private Pile[] tableau = new Pile[7];
    private Pile[] foundation = new Pile[4];
    private Pile stock = new Pile();
    private Pile waste = new Pile();

    public Pile[] getFoundation() { return foundation; }
    public Pile[] getTableau() { return tableau; }
    public Pile getStock() { return stock; }
    public Pile getWaste() { return waste; }
    public void drawFromStock() { //logic form before}
    public boolean hasWon() { //logic from before }

    public Solitaire() {
        deck = new Deck();

        // Initialize tableau and deal cards
        for (int i = 0; i < 7; i++) {
            tableau[i] = new Pile();
            for (int j = 0; j <= i; j++) {
                Card card = deck.draw();
                tableau[i].add(card);
                if (j == i) card.flip();  // Flip top card
            }
        }

        // Initialize foundations
        for (int i = 0; i < 4; i++) {
            foundation[i] = new Pile();
        }

        // Remaining cards go to stock
        while (!deck.isEmpty()) {
            stock.add(deck.draw());
        }
    }

    public void drawFromStock() {
        if (stock.isEmpty()) {
            while (!waste.isEmpty()) {
                Card card = waste.remove();
                card.flip();
                stock.add(card);
            }
        } else {
            Card card = stock.remove();
            card.flip();
            waste.add(card);
        }
    }

    public void printGameState() {
        System.out.println("Foundations:");
        for (int i = 0; i < 4; i++) {
            System.out.print("F" + (i+1) + ": ");
            foundation[i].printPile();
        }

        System.out.print("Waste: ");
        waste.printPile();

        System.out.println("Tableau:");
        for (int i = 0; i < 7; i++) {
            System.out.print("T" + (i+1) + ": ");
            tableau[i].printPile();
        }

        System.out.println("Stock: " + (stock.isEmpty() ? "Empty" : "[X]"));
    }

    public static void main(String[] args) {
        Solitaire game = new Solitaire();
        Scanner scanner = new Scanner(System.in);

        while (true) {
    game.printGameState();
    if (game.hasWon()) {
        System.out.println("Congratulations! You won the game!");
        break;
    }

    System.out.println("Commands: draw, move FROM TO, exit");
    String command = scanner.nextLine();

    if (command.equalsIgnoreCase("draw")) {
        game.drawFromStock();
    } else if (command.startsWith("move")) {
        String[] parts = command.split(" ");
        if (parts.length == 3) {
            game.move(parts[1], parts[2]);
        } else {
            System.out.println("Invalid move syntax. Use: move T1 F1");
        }
    } else if (command.equalsIgnoreCase("exit")) {
        break;
    } else {
        System.out.println("Unknown command.");
    }
}

    }
}
// for moving cards
private boolean move(String from, String to) {
    Pile source = getPile(from);
    Pile target = getPile(to);

    if (source == null || target == null) {
        System.out.println("Invalid pile names.");
        return false;
    }

    Card movingCard = source.peek();
    if (movingCard == null || !movingCard.isFaceUp()) {
        System.out.println("No face-up card to move.");
        return false;
    }

    // Rules for moving to foundation
    if (to.startsWith("F")) {
        if (canMoveToFoundation(movingCard, target)) {
            target.add(source.remove());
            return true;
        }
    }

    // Rules for moving to tableau
    if (to.startsWith("T")) {
        if (canMoveToTableau(movingCard, target)) {
            target.add(source.remove());
            return true;
        }
    }

    System.out.println("Invalid move.");
    return false;
}

// helpers
private Pile getPile(String name) {
    if (name.equalsIgnoreCase("waste")) return waste;
    if (name.equalsIgnoreCase("stock")) return stock;

    if (name.startsWith("T")) {
        int i = Integer.parseInt(name.substring(1)) - 1;
        if (i >= 0 && i < tableau.length) return tableau[i];
    }

    if (name.startsWith("F")) {
        int i = Integer.parseInt(name.substring(1)) - 1;
        if (i >= 0 && i < foundation.length) return foundation[i];
    }

    return null;
}

private boolean canMoveToFoundation(Card card, Pile foundation) {
    if (foundation.isEmpty()) {
        return card.getRank() == Card.Rank.ACE;
    }
    Card top = foundation.peek();
    return top.getSuit() == card.getSuit()
        && card.getRank().ordinal() == top.getRank().ordinal() + 1;
}

private boolean canMoveToTableau(Card card, Pile tableau) {
    if (tableau.isEmpty()) {
        return card.getRank() == Card.Rank.KING;
    }
    Card top = tableau.peek();
    boolean oppositeColor = (isRed(card) != isRed(top));
    boolean oneLess = card.getRank().ordinal() + 1 == top.getRank().ordinal();
    return oppositeColor && oneLess;
}

private boolean isRed(Card card) {
    return card.getSuit() == Card.Suit.HEARTS || card.getSuit() == Card.Suit.DIAMONDS;
}

//win condition
private boolean hasWon() {
    for (Pile f : foundation) {
        if (f.size() != 13) return false;
    }
    return true;
}


 */