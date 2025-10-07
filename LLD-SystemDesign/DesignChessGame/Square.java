package DesignChessGame;

@SuppressWarnings("unused")
public class Square {
    private Position position;
    private Piece piece;

    public Square(Position position) {
        this.position = position;
    }

    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}
