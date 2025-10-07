package DesignChessGame;

public class ChessGame {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentTurn;

    public void startGame() {
        board = new Board();
        whitePlayer = new Player("White", Color.WHITE);
        blackPlayer = new Player("Black", Color.BLACK);
        board.initialize(whitePlayer, blackPlayer);
        currentTurn = whitePlayer;
    }

    public boolean makeMove(Position from, Position to) {
        Piece piece = board.getPieceAt(from);
        if (piece == null || piece.getColor() != currentTurn.getColor()) return false;

        if (piece.isValidMove(board, from, to)) {
            board.movePiece(from, to);
            switchTurn();
            return true;
        }
        return false;
    }

    private void switchTurn() {
        currentTurn = (currentTurn == whitePlayer) ? blackPlayer : whitePlayer;
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.startGame();
        System.out.println("Game started. White moves first.");

        // Example moves
        game.makeMove(new Position(6, 4), new Position(4, 4)); // White pawn e2 to e4
        game.makeMove(new Position(1, 4), new Position(3, 4)); // Black pawn e7 to e5
        System.out.println("Two sample moves played.");
    }
}
