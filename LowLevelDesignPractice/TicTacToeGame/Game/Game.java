package Game;

import GameBoard.Board;
import Piece.PieceType;
import Player.Player;
import java.util.*;

public class Game {
    Board playingBoard ;
    Deque<Player> players = new ArrayDeque<>();
    Scanner scanner;

    public Game(){
        scanner = new Scanner(System.in) ;
    }

    public boolean startGame(){
        // initialize game
        this.initializeGame() ;

        boolean winnerDecided = false;

        this.playingBoard.printBoard();
        while(!winnerDecided){
            Player currentPlayer = players.removeFirst();
            players.addLast(currentPlayer);
            System.out.println(currentPlayer.getName() +" turn");
            int row=0, column=0;

            while(true){
                System.out.println("Enter row number : ") ;
                row = scanner.nextInt(); 
                
                System.out.println("Enter column number : ") ;
                column = scanner.nextInt(); 

                if(this.playingBoard.isRowAndColumnIndexValid(row, column)){
                    boolean isPieceAdded = this.playingBoard.addPieceToBoard(row, column, currentPlayer.getPieceType()) ;
                    if(isPieceAdded){
                        break;
                    }
                    else{
                        System.out.println("Already piece is exist at place. Please try some other coordinate.");
                    }
                }
                else{
                    System.out.println("Please enter input from 0 to "+this.playingBoard.getSize()+".");
                }
                
            }
            this.playingBoard.printBoard();
            String winnerStatus = this.playingBoard.checkGameStatus(currentPlayer.getPieceType(), row, column) ;

            if(winnerStatus.equals("WON") || winnerStatus.equals("TIED")){
                winnerDecided = true;
                this.printGameResult(winnerStatus, currentPlayer) ;
                scanner.close();
                return true;
            }
                   
        }
        scanner.close();
        return false;
    }

    public void initializeGame(){
        inputBoardSize() ;
        initializePlayers() ;
    }

    public void initializePlayers(){
        System.out.println("Enter player1 name : ") ;
        String p1 = scanner.next(); 
        Player player1 = new Player(PieceType.X, p1);

        System.out.println("Enter player2 name : ") ;
        String p2 = scanner.next(); 
        Player player2 = new Player(PieceType.O, p2);

        players.addLast(player1);
        players.addLast(player2);
    }

    public void inputBoardSize(){
        System.out.println("Enter size of board") ;

        int size = scanner.nextInt();  // Read user input
        playingBoard = new Board(size) ;
    }


    public void printGameResult(String winnerStatus, Player player){
        switch (winnerStatus) {
            case "WON":
                System.out.println(player.getName()+" won the game...");
                break;

            case "TIED":
                System.out.println("Game is tied...");
                break;
        
            default:
                break;
        }
    }

}
