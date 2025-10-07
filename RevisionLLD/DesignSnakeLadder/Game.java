package DesignSnakeLadder;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("unused")
public class Game {
    private Board board;
    private Dice dice;
    private List<Player> players;
    private volatile boolean finished;
    private int currentTurnIndex;
    
    private final Lock lock = new ReentrantLock();
    private final Condition turnCondition = lock.newCondition();

    public Game(Board board, Dice dice, List<Player> players) {
        this.board = board;
        this.dice = dice;
        this.players = players;
        this.finished = false;
        this.currentTurnIndex = 0;
    }

    public boolean isFinished() { 
        return finished; 
    }

    public void setPlayers(List<Player> players){
        this.players = players;
    }

    public void takeTurn(Player player){
        lock.lock();
        try{
            while (!finished && !players.get(currentTurnIndex).equals(player)) {
                turnCondition.await();
            }
            if(finished) return;

            int roll = dice.roll();
            int nextPos = player.getPosition() + roll;
            System.out.println(player.getName()+" rolled "+roll);

            if(nextPos > board.getSize()){
                System.out.println(player.getName()+" needs exact roll. Staying at "+player.getPosition());
            }
            else{
                nextPos = board.getNewPosition(nextPos);
                player.setPosition(nextPos);
                System.out.println(player.getName() + "moved to "+nextPos);

                if(nextPos == board.getSize()){
                    System.out.println("🎉 " + player.getName() + " WINS!");
                    finished = true;
                    turnCondition.signalAll();
                    return;
                }
            }

            // move to next player
            currentTurnIndex = (currentTurnIndex+1)% players.size();
            turnCondition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
    public void startGame() {
        for (Player p : players) {
            new Thread(p).start();
        }
    }
}
