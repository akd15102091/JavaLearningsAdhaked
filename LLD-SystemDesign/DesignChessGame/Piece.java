package DesignChessGame;

// Abstract Piece.java
abstract class Piece {
    protected Color color;
    public Piece(Color color) { this.color = color; }
    public Color getColor() { return color; }
    public abstract boolean isValidMove(Board board, Position from, Position to);
}

// King.java
class King extends Piece {
    public King(Color color) { super(color); }
    public boolean isValidMove(Board board, Position from, Position to) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());
        return dx <= 1 && dy <= 1;
    }
}

// Queen.java
class Queen extends Piece {
    public Queen(Color color) { super(color); }
    public boolean isValidMove(Board board, Position from, Position to) {
        return true; // Simplified
    }
}

// Rook.java
class Rook extends Piece {
    public Rook(Color color) { super(color); }
    public boolean isValidMove(Board board, Position from, Position to) {
        return from.getX() == to.getX() || from.getY() == to.getY();
    }
}

// Bishop.java
class Bishop extends Piece {
    public Bishop(Color color) { super(color); }
    public boolean isValidMove(Board board, Position from, Position to) {
        return Math.abs(from.getX() - to.getX()) == Math.abs(from.getY() - to.getY());
    }
}

// Knight.java
class Knight extends Piece {
    public Knight(Color color) { super(color); }
    public boolean isValidMove(Board board, Position from, Position to) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}

// Pawn.java
class Pawn extends Piece {
    public Pawn(Color color) { super(color); }
    public boolean isValidMove(Board board, Position from, Position to) {
        int direction = (color == Color.WHITE) ? -1 : 1;
        return (to.getX() - from.getX()) == direction && from.getY() == to.getY();
    }
}
