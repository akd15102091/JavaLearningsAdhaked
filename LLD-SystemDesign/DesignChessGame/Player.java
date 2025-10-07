package DesignChessGame;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class Player {
    private String name;
    private Color color;
    private Set<Piece> pieces = new HashSet<>();

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void addPieces(Board board, Color color) {
        int base = (color == Color.WHITE) ? 7 : 0;
        int pawnRow = (color == Color.WHITE) ? 6 : 1;

        board.grid[base][0].setPiece(new Rook(color));
        board.grid[base][1].setPiece(new Knight(color));
        board.grid[base][2].setPiece(new Bishop(color));
        board.grid[base][3].setPiece(new Queen(color));
        board.grid[base][4].setPiece(new King(color));
        board.grid[base][5].setPiece(new Bishop(color));
        board.grid[base][6].setPiece(new Knight(color));
        board.grid[base][7].setPiece(new Rook(color));

        for (int i = 0; i < 8; i++)
            board.grid[pawnRow][i].setPiece(new Pawn(color));
    }

    public Color getColor() { return color; }
}
