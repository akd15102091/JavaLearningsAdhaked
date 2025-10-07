package GameBoard;

import Piece.PieceType;

public class Board {
    private int size;
    private PieceType[][] board;

    private static String wonState = "WON";
    private static String tiedState = "TIED";
    private static String inProgressState = "INPROGRESS";

    public Board(int size) {
        this.size = size;
        this.board = new PieceType[size][size] ;
    }

    public int getSize() {
        return size;
    }

    public PieceType[][] getBoard() {
        return board;
    }

    public boolean addPieceToBoard(int row, int column, PieceType type){
        if(this.board[row][column] != null){
            // space is not empty
            return false;
        }
        this.board[row][column] = type;
        return true;
    }

    public boolean isRowAndColumnIndexValid(int row, int column){
        if(row>=0 && row<this.size && column>=0 && column<this.size){
            return true;
        }
        return false;
    }    

    public String checkGameStatus(PieceType pieceType, int row, int column){
        boolean rowFlag = true, columnFlag = true;
        for(int i=0;i<this.size;i++){
            if(this.board[row][i] != pieceType){
                rowFlag = false;
            }
            if(this.board[i][column] != pieceType){
                columnFlag = false;
            }
        }

        // check diagonal too
        boolean diagonal1Flag = true, diagonal2Flag = true;
        for(int i=0;i<this.size;i++){
            if(this.board[i][i] != pieceType){
                diagonal1Flag = false;
            }
            if(this.board[i][this.size-1-i] != pieceType){
                diagonal2Flag = false;
            }
        }

        if(rowFlag || columnFlag || diagonal1Flag || diagonal2Flag){
            return Board.wonState;
        }

        // checkin tie condition
        boolean tieFlag = true;
        for(int i=0;i<this.size;i++){
            for(int j=0;j<this.size;j++){
                if(this.board[i][j] == null){
                    tieFlag = false;
                }
            }
        }
        if(tieFlag){
            return Board.tiedState;
        }

        return Board.inProgressState;
    }

    public void printBoard() {
        for(int i=0;i<this.size;i++){
            for(int j=0;j<this.size; j++){
                if(this.board[i][j] == null){
                    System.out.print("     ");
                }
                else{
                    System.out.print("  "+this.board[i][j]+"  ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

}
