package DesignSnakeLadder;

import java.util.Arrays;
import java.util.List;

public class GameDriverMain {
    public static void main(String[] args) {
        GameManager manager = new GameManager();

        // Common snakes/ladders config
        List<Snake> snakes = Arrays.asList(new Snake(17, 7), new Snake(54, 34));
        List<Ladder> ladders = Arrays.asList(new Ladder(3, 22), new Ladder(8, 26));
        Board board = new Board(50, snakes, ladders);
        Dice dice = new Dice(1, 6);

        // Game 1
        int game1 = manager.createGame(board, dice, Arrays.asList("Alice", "Bob"));
        manager.startGame(game1);

        // // Game 2 (running in parallel)
        // int game2 = manager.createGame(board, dice, Arrays.asList("Charlie", "David"));
        // manager.startGame(game2);

        System.out.println("Active Games: " + manager.listActiveGames());
    }
}
