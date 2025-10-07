package DesignSnakeLadder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {
    Map<Integer, GameRoom> games = new ConcurrentHashMap<>();
    private AtomicInteger gameIdGenerator = new AtomicInteger(1);

    public int createGame(Board board, Dice dice, List<String> playerNames) {
        int gameId = gameIdGenerator.getAndIncrement();
        Game game = new Game(board, dice, new ArrayList<>());
        List<Player> players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name, game));
        }
        game.setPlayers(players);
        GameRoom room = new GameRoom(gameId, game);
        games.put(gameId, room);
        return gameId;
    }

    public void startGame(int gameId) {
        GameRoom room = games.get(gameId);
        if (room == null) {
            System.out.println("Game not found!");
            return;
        }
        room.setStatus("RUNNING");
        room.getGame().startGame();
        System.out.println("Game " + gameId + " started!");
    }

    public List<Integer> listActiveGames() {
        return new ArrayList<>(games.keySet());
    }
}
