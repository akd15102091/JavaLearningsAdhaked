package DesignSnakeLadder;

public class Player implements Runnable{
    private String name;
    private Game game;
    private int position;

    public Player(String name, Game game){
        this.name = name;
        this.game = game;
        this.position = 0;
    }

    public String getName(){ return this.name; }
    public int getPosition(){ return this.position; }
    public Game getGame(){ return this.game; }

    public void setPosition(int pos){
        this.position = pos;
    }

    @Override
    public void run() {
        while(!game.isFinished()) {
            game.takeTurn(this);
            try { Thread.sleep(100); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
