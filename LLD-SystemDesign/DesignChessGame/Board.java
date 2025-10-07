package DesignChessGame;

public class Board {
    Square[][] grid = new Square[8][8];

    public Board() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                grid[i][j] = new Square(new Position(i, j));
    }

    public void initialize(Player white, Player black) {
        white.addPieces(this, Color.WHITE);
        black.addPieces(this, Color.BLACK);
    }

    public Piece getPieceAt(Position pos) {
        return grid[pos.getX()][pos.getY()].getPiece();
    }

    public void movePiece(Position from, Position to) {
        Piece piece = getPieceAt(from);
        grid[to.getX()][to.getY()].setPiece(piece);
        grid[from.getX()][from.getY()].setPiece(null);
    }

    public boolean isWithinBounds(Position pos) {
        return pos.getX() >= 0 && pos.getX() < 8 && pos.getY() >= 0 && pos.getY() < 8;
    }

    public boolean isPathClear(Position from, Position to) {
        return true; // Simplified
    }
}
