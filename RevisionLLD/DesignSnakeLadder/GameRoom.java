package DesignSnakeLadder;

public class GameRoom {
    private int id;
    private Game game;
    private String status;

    public GameRoom(int id, Game game) {
        this.id = id;
        this.game = game;
        this.status = "WAITING";
    }

    public int getId() { return id; }
    public Game getGame() { return game; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
