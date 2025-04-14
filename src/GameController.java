public class GameController {
    private GameView view;
    private Deck deck;

    public void setView(GameView view) {
        this.view = view;
    }

    public void startGame() {
        deck = new Deck();
        deck.shuffle();
        view.initializePiles(deck.deal());
    }

    public boolean tryMove(Move move) {
        if (GameRules.isValidMove(move)) {
            move.execute();
            return true;
        }
        return false;
    public List<Pile> getCurrentPiles() {
        return deck.getCards();
    }
}
