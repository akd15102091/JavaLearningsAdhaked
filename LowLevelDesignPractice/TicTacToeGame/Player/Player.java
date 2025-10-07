package Player;

import Piece.PieceType;

public class Player {
    private PieceType pieceType;
    private String name;

    public Player(PieceType pieceType, String name) {
        this.pieceType = pieceType;
        this.name = name;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public String getName() {
        return name;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
}
